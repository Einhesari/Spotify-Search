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
import com.example.spotifysearch.databinding.FragmentAlbumDetailBinding;
import com.example.spotifysearch.model.item.Album;


public class AlbumDetailFragment extends Fragment {

    private FragmentAlbumDetailBinding fragmentAlbumDetailBinding;
    private Album album;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAlbumDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_album_detail, container, false);
        view = fragmentAlbumDetailBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            album = getArguments().getParcelable(getResources().getString(R.string.selected_item));
            fragmentAlbumDetailBinding.setItem(album);
            fragmentAlbumDetailBinding.setOnclick(this);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentAlbumDetailBinding = null;
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
        String shareMessage = String.format("\nCheck out %1$s  by %2$s in Spotify! \r\n", album.getName(), album.getArtists().get(0).getName());
        shareMessage = shareMessage + album.getExternalUrl().getSpotifyUrl();
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(shareIntent);
    }
}
