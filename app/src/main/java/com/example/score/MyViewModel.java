package com.example.score;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

public class MyViewModel extends AndroidViewModel {
    final private String ATEAMSCORE = "ats", BTEAMSCORE = "bts", ABACK = "abk", BBACK = "bbk";
    final private String SHP_NAME = "MyViewModel";
    private SavedStateHandle handle;

    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        this.handle = handle;
        if (!handle.contains(ATEAMSCORE) || !handle.contains(BTEAMSCORE)) {
            load();
        }
    }

    private void load() {
        SharedPreferences preferences = getApplication().getSharedPreferences(SHP_NAME, Context.MODE_PRIVATE);
        int aScore = preferences.getInt(ATEAMSCORE, 0);
        int bScore = preferences.getInt(BTEAMSCORE, 0);
        handle.set(ATEAMSCORE, aScore);
        handle.set(BTEAMSCORE, bScore);
    }

    void save() {
        SharedPreferences preferences = getApplication().getSharedPreferences(SHP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(ATEAMSCORE, getaTeamScore().getValue());
        editor.putInt(BTEAMSCORE, getbTeamScore().getValue());
        editor.apply();
    }


    public MutableLiveData<Integer> getaTeamScore() {
        if (!handle.contains(ATEAMSCORE)) {
            handle.set(ATEAMSCORE, 0);
        }
        return handle.getLiveData(ATEAMSCORE);
    }

    public MutableLiveData<Integer> getbTeamScore() {
        if (!handle.contains(BTEAMSCORE)) {
            handle.set(BTEAMSCORE, 0);
        }
        return handle.getLiveData(BTEAMSCORE);
    }


    public void aTeamAdd(int a) {
        handle.set(ABACK, getaTeamScore().getValue());
        handle.set(BBACK, getbTeamScore().getValue());
        getaTeamScore().setValue(getaTeamScore().getValue() + a);
    }

    public void bTeamAdd(int b) {
        handle.set(ABACK, getaTeamScore().getValue());
        handle.set(BBACK, getbTeamScore().getValue());
        getbTeamScore().setValue(getbTeamScore().getValue() + b);
    }

    public void reset() {
        handle.set(ABACK, getaTeamScore().getValue());
        handle.set(BBACK, getbTeamScore().getValue());
        handle.set(ATEAMSCORE, 0);
        handle.set(BTEAMSCORE, 0);
    }

    public void undo() {
        handle.set(ATEAMSCORE, handle.getLiveData(ABACK).getValue());
        handle.set(BTEAMSCORE, handle.getLiveData(BBACK).getValue());
    }

}
