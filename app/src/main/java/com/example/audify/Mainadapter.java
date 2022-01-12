package com.example.audify;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class Mainadapter extends RecyclerView.Adapter<Mainadapter.ViewHolder> {
    ArrayList<File> mysongs;
    private  OnNoteListener monNoteListener;
    public Mainadapter(ArrayList<File> mSong,OnNoteListener onNoteListener)
    {
        mysongs=mSong;
        this.monNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public Mainadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);

        return new ViewHolder(view,monNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Mainadapter.ViewHolder holder, int position) {
        holder.mfname.setText(mysongs.get(position).getName().replace(".mp3",""));
    }

    @Override
    public int getItemCount() {
        return mysongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mfname;
        OnNoteListener onNoteListener;
        public ViewHolder(@NonNull View itemView,OnNoteListener onNoteListener) {
            super(itemView);
            mfname = itemView.findViewById(R.id.textView3);
            this.onNoteListener =onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        onNoteListener.onNoteClick(mysongs,getAbsoluteAdapterPosition());
        }
    }
    public interface OnNoteListener
    {
        void onNoteClick(ArrayList<File> mysongs,int position);
    }
}
