package com.p2ms.musicbuddy.ui.nowPlaying;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NowPlayingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NowPlayingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}