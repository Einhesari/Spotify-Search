package com.example.spotifysearch.view.main.search;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifysearch.R;
import com.example.spotifysearch.adapter.SearchItemDataAdapter;
import com.example.spotifysearch.databinding.FragmentSearchBinding;
import com.example.spotifysearch.model.item.BaseItem;
import com.example.spotifysearch.viewmodel.ViewModelProviderFactory;
import com.example.spotifysearch.viewmodel.main.MainActivityViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class SearchFragment extends DaggerFragment {

    private FragmentSearchBinding fragmentSearchBinding;
    private MainActivityViewModel mainActivityViewModel;
    private SearchItemDataAdapter searchItemDataAdapter;
    private RecyclerView rv_items;
    private View view;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        view = fragmentSearchBinding.getRoot();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivityViewModel = new ViewModelProvider(getActivity(), providerFactory).get(MainActivityViewModel.class);

        rv_items = fragmentSearchBinding.rvItems;
        rv_items.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_items.setHasFixedSize(true);

        searchItemDataAdapter = new SearchItemDataAdapter();
        rv_items.setAdapter(searchItemDataAdapter);

        searchItemDataAdapter.getClickedItem().observe(getViewLifecycleOwner(), new Observer<BaseItem>() {
            @Override
            public void onChanged(BaseItem item) {

                Bundle navBundel = new Bundle();
                navBundel.putParcelable(getResources().getString(R.string.selected_item), item);


                switch (item.getType()) {
                    case "album":
                        Navigation.findNavController(getActivity(), R.id.search_nav_host_fragment).navigate(R.id.action_searchFragment_to_albumDetailFragment, navBundel);
                        break;

                    case "track":
                        Navigation.findNavController(getActivity(), R.id.search_nav_host_fragment).navigate(R.id.action_searchFragment_to_trackDetailFragment, navBundel);
                        break;

                    case "artist":
                        Navigation.findNavController(getActivity(), R.id.search_nav_host_fragment).navigate(R.id.action_searchFragment_to_artistDetailFragment, navBundel);
                        break;
                }
            }
        });

        mainActivityViewModel.searchStateLiveData().observe(getViewLifecycleOwner(), searchState -> {
            switch (searchState.status) {

                case LOADING:
                    fragmentSearchBinding.setIsloading(true);
                    break;

                case SEARCH_COMPLETE:
                    fragmentSearchBinding.setIsloading(false);
                    searchItemDataAdapter.submitList(searchState.data);
                    rv_items.smoothScrollToPosition(0);

                    break;

                case ERROR:
                    fragmentSearchBinding.setIsloading(false);
                    Toast.makeText(getContext(), searchState.message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

        fragmentSearchBinding = null;
        ((ViewGroup) view).removeAllViews();
        view = null;
    }
}
