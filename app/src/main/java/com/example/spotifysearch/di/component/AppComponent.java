package com.example.spotifysearch.di.component;

import android.app.Application;

import com.example.spotifysearch.BaseApplication;
import com.example.spotifysearch.di.module.ActivityBuildersModule;
import com.example.spotifysearch.di.module.ApiModule;
import com.example.spotifysearch.di.module.AppModule;
import com.example.spotifysearch.di.module.SharedPrefsModule;
import com.example.spotifysearch.di.module.ViewModelFactoryModule;
import com.example.spotifysearch.di.module.ViewModelsModule;
import com.example.spotifysearch.di.scope.AppScope;


import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@AppScope
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                ViewModelFactoryModule.class,
                ViewModelsModule.class,
                ApiModule.class,
                AppModule.class,
                SharedPrefsModule.class,

        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {


    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }
}
