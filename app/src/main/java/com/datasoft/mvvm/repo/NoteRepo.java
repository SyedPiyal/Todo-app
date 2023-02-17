package com.datasoft.mvvm.repo;

import android.app.Application;
import android.os.AsyncTask;


import androidx.lifecycle.LiveData;
import androidx.room.Delete;

import com.datasoft.mvvm.DAO;
import com.datasoft.mvvm.Note;
import com.datasoft.mvvm.RoomDB;
import java.util.List;


public class NoteRepo {
    private DAO dao;
    private LiveData<List<Note>>  notelist;

    public  NoteRepo(Application application){
        RoomDB roomDB = RoomDB.getInstance(application);
        dao = roomDB.dao();
        notelist = dao.getAllData();
    }
    public void insertData(Note note){new InsertTask(dao).execute(note);}
    public void updateData(Note note){new UpdateTask(dao).execute(note);}
    public void deleteData(Note note){new DeleteTask(dao).execute(note);}


    public LiveData<List<Note>> getAllData(){
        return notelist;
    }
    private static class InsertTask extends AsyncTask<Note,Void,Void> {

        private DAO dao;

        public InsertTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateTask extends AsyncTask<Note,Void,Void> {

        private DAO dao;

        public UpdateTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteTask extends AsyncTask<Note,Void,Void> {

        private DAO dao;

        public DeleteTask(DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.delete(notes[0]);
            return null;
        }
    }



}
