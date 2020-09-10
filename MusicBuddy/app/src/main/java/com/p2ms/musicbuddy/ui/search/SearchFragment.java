package com.p2ms.musicbuddy.ui.search;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.p2ms.musicbuddy.R;
import com.p2ms.musicbuddy.adapter.RecyclerItemAdapter;
import com.p2ms.musicbuddy.model.Song;
import com.p2ms.musicbuddy.ui.nowPlaying.NowPlayingFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements RecyclerItemAdapter.BtnPlayedListener {

    private SearchViewModel searchViewModel;
    private Button search;
    private EditText search_txt;

    private RecyclerView rv;
    private LinearLayoutManager linearLayoutManager;

    private RecyclerItemAdapter adapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage;
    StorageReference storageRef;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_search, container, false);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fetching Song Details");
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        StorageReference songsRef = storageRef.child("Songs");
        Log.d("SearchFragment","Storage reference is: "+storageRef.toString());
        Log.d("SearchFragment","Songs reference is: "+songsRef.toString());
        Log.d("SearchFragment","Songs reference name: "+songsRef.getName());
        Log.d("SearchFragment","Songs reference name: "+storageRef.child("Songs").listAll());


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
                                //Log.d("SearchFragment","task success: "+document.getData().get("url"));
                                song.setSongName(document.getData().get("songName").toString());
                                song.setUrl(document.getData().get("url").toString());
                                songList.add(song);

                            }
                            for(int i=0;i<songList.size();i++) {
                                Log.d("Search", songList.get(i).getSongName());
                                Log.d("Search", songList.get(i).getUrl());
                            }

                            rv=root.findViewById(R.id.recyclerViewID);
                            linearLayoutManager= new LinearLayoutManager(getContext());
                            rv.setLayoutManager(linearLayoutManager);

                            adapter = new RecyclerItemAdapter(getContext(),songList, SearchFragment.this);
                            rv.setAdapter(adapter);
                            progressDialog.dismiss();

                        } else {
                            Log.w("SearchFragment", "Error getting documents.", task.getException());
                        }
                    }
                });
        return root;

    }


    @Override
    public void btnPlayed(Song song) {
        NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
        FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
        fragmentManager.replace(R.id.SearchFragmentLayout,nowPlayingFragment);
        fragmentManager.addToBackStack(null);
        fragmentManager.commit();
    }
}