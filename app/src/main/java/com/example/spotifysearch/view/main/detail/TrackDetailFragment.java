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
import com.example.spotifysearch.databinding.FragmentTrackDetailBinding;
import com.example.spotifysearch.model.item.Track;


public class TrackDetailFragment extends Fragment {


    private FragmentTrackDetailBinding fragmentTrackDetailBinding;
    private View view;
    private Track track;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentTrackDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_track_detail, container, false);
        view = fragmentTrackDetailBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            track = getArguments().getParcelable(getResources().getString(R.string.selected_item));
            fragmentTrackDetailBinding.setItem(track);
            fragmentTrackDetailBinding.setOnclick(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentTrackDetailBinding = null;
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
        String shareMessage = String.format("\nCheck out %1$s by %2$s in Spotify! \r\n", track.getName(), track.getArtists().get(0).getName());
        shareMessage = shareMessage + track.getExternalUrl().getSpotifyUrl();
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(shareIntent);
    }

}
