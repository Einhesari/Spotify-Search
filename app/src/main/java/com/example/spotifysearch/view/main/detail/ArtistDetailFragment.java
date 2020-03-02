package com.example.spotifysearch.view.main.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.spotifysearch.R;
import com.example.spotifysearch.databinding.FragmentArtistDetailBinding;
import com.example.spotifysearch.model.item.Artist;


public class ArtistDetailFragment extends Fragment {

    private FragmentArtistDetailBinding fragmentArtistDetailBinding;
    private View view;
    private Artist artist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentArtistDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_artist_detail, container, false);
        view = fragmentArtistDetailBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            artist = getArguments().getParcelable("selected_artist");
            fragmentArtistDetailBinding.setItem(artist);
            fragmentArtistDetailBinding.setOnclick(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentArtistDetailBinding = null;
        ((ViewGroup) view).removeAllViews();
        view = null;

    }

    public void backButtonOnClick(View view) {
        getActivity().onBackPressed();
    }

    public void shareButtonOnClick(View view) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share Album");
        String shareMessage = String.format("\nCheck out %1$s in Spotify! \r\n", artist.getName());
        shareMessage = shareMessage + artist.getExternalUrl().getSpotifyUrl();
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(shareIntent);
    }
}
