package com.datasoft.mvvm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.datasoft.mvvm.adapter.RVAdapter;

import com.datasoft.mvvm.databinding.ActivityMainBinding;
import com.datasoft.mvvm.viewmodel.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //binding layout
    ActivityMainBinding binding;

    //creating view model obj to add
    private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        noteViewModel= new ViewModelProvider(this,(ViewModelProvider.Factory)ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(NoteViewModel.class);
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DataInserActivity.class);
                intent.putExtra("type","addMode");
                startActivityForResult(intent,1);
               /* startActivity(intent);*/

                //set result output
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
        RVAdapter adapter = new RVAdapter(MainActivity.this);//MainActivity.this
        binding.recyclerView.setAdapter(adapter);

        noteViewModel.getAllData().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
              adapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction==ItemTouchHelper.RIGHT){
                    noteViewModel.delete(adapter.getNote(viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(MainActivity.this,DataInserActivity.class);
                    intent.putExtra("type","update");
                    intent.putExtra("title",adapter.getNote(viewHolder.getAdapterPosition()).getName());
                    intent.putExtra("description",adapter.getNote(viewHolder.getAdapterPosition()).getDesc());
                    intent.putExtra("id",adapter.getNote(viewHolder.getAdapterPosition()).getId());
                    noteViewModel.update(adapter.getNote(viewHolder.getAdapterPosition()));
                    startActivityForResult(intent,2);
                }

            }
        }).attachToRecyclerView(binding.recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            String title = data.getStringExtra("title"); // getting data
            String description = data.getStringExtra("description");
            Note note= new Note(title,description);
            noteViewModel.insert(note);
            Toast.makeText(this, "note added", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode==2){
            String title = data.getStringExtra("title"); // getting data
            String description = data.getStringExtra("description");
            Note note= new Note(title,description);
            note.setId(data.getIntExtra("id",0));
            noteViewModel.update(note);
            Toast.makeText(this, "note update", Toast.LENGTH_SHORT).show();
        }
    }
}