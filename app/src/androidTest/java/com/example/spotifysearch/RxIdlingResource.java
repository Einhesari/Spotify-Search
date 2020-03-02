package com.example.spotifyapi;

import android.util.Log;


import androidx.test.espresso.IdlingResource;

import com.bumptech.glide.request.ResourceCallback;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;

public class RxIdlingResource implements IdlingResource, Function<Runnable, Runnable> {

    private static final String TAG = RxIdlingResource.class.getSimpleName();

    private static final ReentrantReadWriteLock IDLING_STATE_LOCK = new ReentrantReadWriteLock();

    private int taskCount = 0;

    private ResourceCallback transitionCallback;

    @Override
    public String getName() {
        return TAG;
    }

    @Override
    public boolean isIdleNow() {

        boolean result;

        IDLING_STATE_LOCK.readLock().lock();
        result = taskCount == 0;
        IDLING_STATE_LOCK.readLock().unlock();

        return result;
    }

    @Override
    public void registerIdleTransitionCallback(final ResourceCallback callback) {
        IDLING_STATE_LOCK.writeLock().lock();
        this.transitionCallback = callback;
        IDLING_STATE_LOCK.writeLock().unlock();
    }

    @Override
    public Runnable apply(final Runnable runnable) throws Exception {
        IDLING_STATE_LOCK.writeLock().lock();
        taskCount++;
        Log.d(TAG, "TaskCount increase " + taskCount);
        IDLING_STATE_LOCK.writeLock().unlock();
        return () -> {
            try {
                runnable.run();
            } finally {
                IDLING_STATE_LOCK.writeLock().lock();

                try {
                    taskCount--;
                    Log.d(TAG, "TaskCount decrease " + taskCount);

                    if (taskCount == 0 && transitionCallback != null) {
                        transitionCallback.onTransitionToIdle();
                        Log.d(TAG, "idle ");
                    }
                } finally {
                    IDLING_STATE_LOCK.writeLock().unlock();
                }
            }
        };
    }

    public void waitForIdle() {
        if (!isIdleNow()) {
            CountDownLatch latch = new CountDownLatch(1);
            registerIdleTransitionCallback(latch::countDown);
            try {
                latch.await();
            } catch (InterruptedException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }

    public void register() {
        RxJavaPlugins.setScheduleHandler(this);
    }
}