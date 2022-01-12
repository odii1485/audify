package com.example.audify;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements Mainadapter.OnNoteListener {

    RecyclerView mrecycleview;
    String crs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mrecycleview = findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager mlayoutManager;
        RecyclerView.Adapter mAdapter;
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            ArrayList<File> mysongs = fetchSongs(Environment.getExternalStorageDirectory());

            mrecycleview.setHasFixedSize(true);
            mlayoutManager = new LinearLayoutManager(this);
            mAdapter = new Mainadapter(mysongs,this);
            mrecycleview.setLayoutManager(mlayoutManager);
            mrecycleview.setAdapter(mAdapter);


        }

    }

        public  ArrayList<File> fetchSongs(File file)
        {
            ArrayList arrayList = new ArrayList();
            File[] songs = file.listFiles();
            if(songs!=null)
            {
                for( File myFile: songs)
                {
                    if(!myFile.isHidden() && myFile.isDirectory())
                    {
                        arrayList.addAll(fetchSongs(myFile));
                    }
                    else
                    {
                        if(myFile.getName().endsWith(".mp3") && !myFile.getName().startsWith("."))
                        {
                            arrayList.add(myFile);
                        }
                    }
                }
            }
            return arrayList;
        }

    @Override
    public void onNoteClick(ArrayList<File> mysongs,int position) {
        Intent intent = new Intent(this,Songplay.class);
        intent.putExtra("Songlist",mysongs);
        intent.putExtra("pos",position);
        startActivity(intent);
    }
}