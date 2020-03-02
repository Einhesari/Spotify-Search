package com.example.spotifysearch.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MediatorLiveData;
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

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class SearchItemDataAdapter extends ListAdapter<BaseItem, RecyclerView.ViewHolder> {

    MutableLiveData<BaseItem> itemLiveData = new MutableLiveData<>();
    MutableLiveData<ImageView> imageViewLiveData = new MutableLiveData<>();
    MediatorLiveData clickedLiveData = new MediatorLiveData();
    PublishSubject<BaseItem> baseItemObservable = PublishSubject.create();
    PublishSubject<ImageView> imageViewObservable = PublishSubject.create();


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
        ImageView sharedImage = null;
        switch (holder.getItemViewType()) {

            case 0:
                ((AlbumViewHolder) holder).albumRecyclerItemBinding.setItem((Album) item);
                sharedImage = ((AlbumViewHolder) holder).albumRecyclerItemBinding.img;
                break;

            case 1:
                ((ArtistViewHolder) holder).artistRecyclerItemBinding.setItem((Artist) item);
                sharedImage = ((ArtistViewHolder) holder).artistRecyclerItemBinding.img;
                break;

            case 2:
                ((TrackViewHolder) holder).trackRecyclerItemBinding.setItem((Track) item);
                sharedImage = ((TrackViewHolder) holder).trackRecyclerItemBinding.img;
                break;

        }

        ImageView finalSharedImage = sharedImage;

        holder.itemView.setOnClickListener(view -> {
//            clickedLiveData.addSource(itemLiveData, o -> {
//                clickedLiveData.setValue(o);
//                clickedLiveData.removeSource(itemLiveData);
//            });
//            clickedLiveData.addSource(imageViewLiveData, o -> {
//                clickedLiveData.setValue(o);
//                clickedLiveData.removeSource(imageViewLiveData);
//            });
//            itemLiveData.postValue(item);
//            imageViewLiveData.postValue(finalSharedImage);
            baseItemObservable.onNext(item);
            imageViewObservable.onNext(finalSharedImage);
        });

    }

    public Observable getItemOnclickSubject() {
        val zipped = Observables.zip(currentSubject, maxSubject) { current, max -> "current : $current, max : $max " }
        zipped.subscribe(
                { Log.d("custom", it) },
                { Log.d("custom", "BONGO!") },
                { Log.d("custom", "KONGO!") }
        )    }

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
