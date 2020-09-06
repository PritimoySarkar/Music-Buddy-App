package com.p2ms.musicbuddy.ui.search;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.p2ms.musicbuddy.R;

import java.io.File;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private Button search;
    private EditText search_txt;

    private File localFile;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        search_txt=root.findViewById(R.id.search_songID);
        search=root.findViewById(R.id.search_buttonID);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSong(search_txt.getText().toString());
            }
        });

        return root;
    }

    private void getSong(String text) {
        //localFile= File.createTempFile(text,"mp3");
    }
}