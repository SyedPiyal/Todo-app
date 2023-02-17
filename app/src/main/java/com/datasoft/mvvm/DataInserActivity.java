package com.datasoft.mvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.datasoft.mvvm.databinding.ActivityDataInserBinding;

public class DataInserActivity extends AppCompatActivity {

    ActivityDataInserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDataInserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String type=getIntent().getStringExtra("type");
        if(type.equals("update")){
            setTitle("update");
            binding.editTextTextPersonName.setText(getIntent().getStringExtra("title"));
            binding.editTextTextPersonName2.setText(getIntent().getStringExtra("description"));
            int id = getIntent().getIntExtra("id",0);
            binding.button.setText("update note");
            binding.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("title",binding.editTextTextPersonName.getText().toString());
                    intent.putExtra("description",binding.editTextTextPersonName2.getText().toString());
                    intent.putExtra("id",id);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }else{
            setTitle("Add Note");
            binding.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //data binding with intent
                    Intent intent = new Intent();
                    intent.putExtra("title",binding.editTextTextPersonName.getText().toString());
                    intent.putExtra("description",binding.editTextTextPersonName2.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();

                    //set result input
                }
            });
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    startActivity(new Intent(DataInserActivity.this, MainActivity.class));
    }
}