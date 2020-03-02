package com.example.spotifysearch.viewmodel.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotifysearch.model.item.BaseItem;
import com.example.spotifysearch.states.SearchResouces;
import com.example.spotifysearch.viewmodel.main.search.SearchRepository;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainActivityViewModel extends ViewModel {

    private SearchRepository repository;

    @Inject
    public MainActivityViewModel(SearchRepository repository) {
        this.repository = repository;
    }


    public LiveData<SearchResouces<ArrayList<BaseItem>>> searchStateLiveData() {
        return repository.getState();
    }

}
