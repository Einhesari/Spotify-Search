package com.example.spotifysearch.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifysearch.R;
import com.example.spotifysearch.databinding.AlbumRecyclerItemBinding;
import com.example.spotifysearch.databinding.ArtistRecyclerItemBinding;
import com.example.spotifysearch.databinding.TrackRecyclerItemBinding;
import com.example.spotifysearch.model.item.Album;
import com.example.spotifysearch.model.item.Artist;
import com.example.spotifysearch.model.item.BaseItem;
import com.example.spotifysearch.model.item.Track;


public class SearchItemDataAdapter extends ListAdapter<BaseItem, RecyclerView.ViewHolder> {

    private MutableLiveData<BaseItem> selectedItem = new MutableLiveData<>();


    public SearchItemDataAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<BaseItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<BaseItem>() {
        @Override
        public boolean areItemsTheSame(BaseItem oldItem, BaseItem newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull BaseItem oldItem, @NonNull BaseItem newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        AlbumRecyclerItemBinding albumRecyclerItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.album_recycler_item, parent, false);
        ArtistRecyclerItemBinding artistRecyclerItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.artist_recycler_item, parent, false);
        TrackRecyclerItemBinding trackRecyclerItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.track_recycler_item, parent, false);

        switch (viewType) {
            case 0:
                return new AlbumViewHolder(albumRecyclerItemBinding);
            case 1:
                return new ArtistViewHolder(artistRecyclerItemBinding);
            case 2:
                return new TrackViewHolder(trackRecyclerItemBinding);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        BaseItem item = getItem(position);
        switch (holder.getItemViewType()) {

            case 0:
                ((AlbumViewHolder) holder).albumRecyclerItemBinding.setItem((Album) item);
                break;

            case 1:
                ((ArtistViewHolder) holder).artistRecyclerItemBinding.setItem((Artist) item);
                break;

            case 2:
                ((TrackViewHolder) holder).trackRecyclerItemBinding.setItem((Track) item);
                break;

        }



        holder.itemView.setOnClickListener(view -> {
            selectedItem.postValue(item);
        });

    }

    public LiveData<BaseItem> getClickedItem() {
        return selectedItem;
    }

    @Override
    public int getItemViewType(int position) {
        switch (getItem(position).getType()) {
            case "album":
                return 0;
            case "artist":
                return 1;
            case "track":
                return 2;
            default:
                return 0;
        }
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder {


        private AlbumRecyclerItemBinding albumRecyclerItemBinding;

        public AlbumViewHolder(@NonNull AlbumRecyclerItemBinding albumRecyclerItemBinding) {
            super(albumRecyclerItemBinding.getRoot());
            this.albumRecyclerItemBinding = albumRecyclerItemBinding;
        }
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder {


        private ArtistRecyclerItemBinding artistRecyclerItemBinding;

        public ArtistViewHolder(@NonNull ArtistRecyclerItemBinding artistRecyclerItemBinding) {
            super(artistRecyclerItemBinding.getRoot());
            this.artistRecyclerItemBinding = artistRecyclerItemBinding;
        }
    }

    class TrackViewHolder extends RecyclerView.ViewHolder {


        private TrackRecyclerItemBinding trackRecyclerItemBinding;

        public TrackViewHolder(@NonNull TrackRecyclerItemBinding trackRecyclerItemBinding) {
            super(trackRecyclerItemBinding.getRoot());
            this.trackRecyclerItemBinding = trackRecyclerItemBinding;
        }
    }


}
