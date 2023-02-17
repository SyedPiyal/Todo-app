package com.datasoft.mvvm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.datasoft.mvvm.Note;
import com.datasoft.mvvm.R;
import com.datasoft.mvvm.databinding.RowBinding;

public class RVAdapter extends ListAdapter<Note,RVAdapter.ViewHolder> {

    public RVAdapter(Context context){

        super(CALLBACK);
    }
    private  static  final DiffUtil.ItemCallback<Note> CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getName().equals(newItem.getName())
                    && oldItem.getDesc().equals(newItem.getDesc());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(view); //creating view
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Note note = getItem(position);
        holder.binding.textView.setText(note.getName());
        holder.binding.textView2.setText(note.getDesc());
    }
    public  Note getNote(int position){
        return  getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RowBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=RowBinding.bind(itemView);
        }
    }
}
