package com.datasoft.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.datasoft.mvvm.Note;
import com.datasoft.mvvm.repo.NoteRepo;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepo noteRepo;
    private LiveData<List<Note>> notelist;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepo= new NoteRepo(application);
        notelist= noteRepo.getAllData();
    }
    public void insert(Note note){
        noteRepo.insertData(note);
    }

    public void update(Note note){
        noteRepo.updateData(note);
    }

    public void delete(Note note){
        noteRepo.deleteData(note);
    }

    public LiveData<List<Note>> getAllData(){
        return notelist;
    }
}
