package com.p2ms.musicbuddy.ui.nowPlaying;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.p2ms.musicbuddy.R;
import com.p2ms.musicbuddy.model.Song;

import java.util.ArrayList;
import java.util.List;

public class NowPlayingFragment extends Fragment {

    private NowPlayingViewModel dashboardViewModel;
    JcPlayerView jcPlayer;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(NowPlayingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_now_playing, container, false);

        final ArrayList<JcAudio> jcAudios = new ArrayList<>();
        jcPlayer = root.findViewById(R.id.jcplayerID);
        db.collection("songDetails")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            List<Song> songList = new ArrayList<>();
                            //Map<String ,String> songMap = new HashMap<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Song song = new Song();
                                //Log.d("SearchFragment","task success: "+document.getId());
                                Log.d("SearchFragment", "task success: " + document.getData().get("songName"));
                                song.setSongName(document.getData().get("songName").toString());
                                song.setUrl(document.getData().get("url").toString());
                                songList.add(song);
                                jcAudios.add(JcAudio.createFromURL(song.getSongName(), song.getUrl()));
                                Log.d("NowPlayingFragment",String.valueOf(jcAudios.size()));
                            }
                            jcPlayer.playAudio(jcAudios.get(0));
                            jcPlayer.setVisibility(View.VISIBLE);
                            jcPlayer.initPlaylist(jcAudios, null);
                            if(jcPlayer.isPlaying()){
                                Log.d("NowPlayingFragment", "Song Playing");
                                Log.d("NowPlayingFragment","Inside if part: "+String.valueOf(jcAudios.size()));
                            }else {
                                Log.d("NowPlayingFragment", "Song Not Playing");
                                Log.d("NowPlayingFragment","Inside Else part: "+String.valueOf(jcAudios.size()));
                            }
                            Log.d("SearchFragment", "Getting songs from ArrayList" + jcAudios.get(1));

                        } else {
                            Log.w("SearchFragment", "Error getting documents.", task.getException());
                        }
                    }
                });

//async await
        Log.d("NowPlayingFragment","songs: ");
        return root;
    }
}