package com.p2ms.musicbuddy.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.p2ms.musicbuddy.R;
import com.p2ms.musicbuddy.model.Song;

import java.util.List;

public class RecyclerItemAdapter extends RecyclerView.Adapter<RecyclerItemAdapter.SongViewHolder> {
    public interface BtnPlayedListener {
        public void btnPlayed(Song song);
    }

    private Context context;
    private List<Song> songList;
    private BtnPlayedListener btnPlayedListener;

    public RecyclerItemAdapter(Context context, List<Song> songList, BtnPlayedListener btnPlayedListener) {
        this.context = context;
        this.songList = songList;
        this.btnPlayedListener = btnPlayedListener;
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
    public void onBindViewHolder(@NonNull final SongViewHolder holder, final int position) {
        final Song song = songList.get(position);
        String title = song.getSongName();
        holder.songTitle.setText(title);
        //Log.d("RecyclerItemAdapter","position is: "+position);
        holder.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("RecyclerItemAdapter","Bhag bsdk!");
                Log.d("RecuclerItemAdapter", String.valueOf(position));
                btnPlayedListener.btnPlayed(song);
            }
        });

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        private TextView songTitle;
        private LinearLayout songLayout;
        private Button btnPlay;
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            songTitle=itemView.findViewById(R.id.song_titleID);
            songLayout=itemView.findViewById(R.id.itemViewID);
            btnPlay = itemView.findViewById(R.id.btnPlay);
        }
    }

}
