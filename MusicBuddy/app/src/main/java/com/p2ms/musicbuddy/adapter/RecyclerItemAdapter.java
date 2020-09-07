package com.p2ms.musicbuddy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.p2ms.musicbuddy.R;
import com.p2ms.musicbuddy.model.Song;

import java.util.List;

public class RecyclerItemAdapter extends RecyclerView.Adapter<RecyclerItemAdapter.SongViewHolder> {

    private Context context;
    private List<Song> songList;

    public RecyclerItemAdapter(Context context, List<Song> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View songItemView = inflater.inflate(R.layout.recycler_items,parent,false);
        SongViewHolder holder = new SongViewHolder((songItemView));
        return holder;
        
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songList.get(position);
        String title = song.getSongName();
        holder.songTitle.setText(title);

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        private TextView songTitle;
        private LinearLayout songLayout;
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            songTitle=itemView.findViewById(R.id.song_titleID);
            songLayout=itemView.findViewById(R.id.itemViewID);
        }
    }

}
