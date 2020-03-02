package com.example.spotifysearch.viewmodel.main.search;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;

import com.example.spotifysearch.model.item.BaseItem;
import com.example.spotifysearch.model.item.SearchResult;
import com.example.spotifysearch.network.ApiService;
import com.example.spotifysearch.states.SearchResouces;
import com.example.spotifysearch.utils.Const;
import com.example.spotifysearch.utils.RetrofitBaseUrlModifier;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Retrofit;

public class SearchRepository {

    private ApiService apiService;
    private Retrofit retrofit;
    private Context context;
    public static final PublishSubject<String> textWathcerSubject = PublishSubject.create();
    private static final PublishSubject<String> searchQuerySubject = PublishSubject.create();
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<SearchResouces<ArrayList<BaseItem>>> searchStatus = new MutableLiveData<>();

    @Inject
    public SearchRepository(Context context, ApiService apiService, Retrofit retrofit) {
        this.context = context;
        this.retrofit = retrofit;
        this.apiService = apiService;
        RetrofitBaseUrlModifier.Change(retrofit, Const.BASE_URL_API);
        sendSearchQuery();

    }


    public static void setRxTextWatcher(EditText edt_search, PublishSubject<String> subject) {
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                subject.onNext(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        subject.debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textWatcherObserver());
    }

    private static Observer<String> textWatcherObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String query) {
                searchQuerySubject.onNext(query);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

    }

    private void sendSearchQuery() {


        disposable.add(
                searchQuerySubject
                        .debounce(300, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .switchMap(query -> {
                            searchStatus.postValue(SearchResouces.loading());
                            return apiService.search(query, "artist,album,track", 4)
                                    .doOnError(throwable -> searchStatus.postValue(SearchResouces.searchComplete(new ArrayList<>())))
                                    .onErrorResumeNext(Observable.empty())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread());
                        })
                        .subscribeWith(new DisposableObserver<SearchResult>() {
                            @Override
                            public void onNext(SearchResult result) {
                                ArrayList<BaseItem> recyclerViewItems = new ArrayList<>();
                                recyclerViewItems.addAll(result.getFoundedAlbums().getAlbums());
                                recyclerViewItems.addAll(result.getFoundedArtists().getArtists());
                                recyclerViewItems.addAll(result.getFoundedTracks().getTracks());
                                searchStatus.postValue(SearchResouces.searchComplete(recyclerViewItems));
                            }

                            @Override
                            public void onError(Throwable e) {
                                searchStatus.postValue(SearchResouces.error(e.getMessage(), new ArrayList<>()));

                            }

                            @Override
                            public void onComplete() {
                                searchStatus.postValue(SearchResouces.searchComplete(new ArrayList<>()));

                            }
                        }));

    }

    public MutableLiveData<SearchResouces<ArrayList<BaseItem>>> getState() {
        return searchStatus;
    }

}
