package com.example.audify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import java.nio.file.Path;

import java.io.File;
import java.util.ArrayList;

public class Songplay extends AppCompatActivity {
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        updateseek.interrupt();

    }

    TextView textView2;
    ArrayList<File> songs;
    int position;
    MediaPlayer mediaPlayer;
    String TextContent;
    SeekBar seekBar;
    Thread updateseek;
    ImageView previous;
    ImageView next;
    ImageView pause;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songplay);
        previous = findViewById(R.id.previous);
        pause = findViewById(R.id.pause);
        next = findViewById(R.id.next);
        textView2 = findViewById(R.id.textView2);
        seekBar = findViewById(R.id.seekBar);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        songs = (ArrayList) bundle.getParcelableArrayList("Songlist");
        position = intent.getIntExtra("pos",0);
        TextContent = songs.get(position).getName().replace(".mp3","");
        textView2.setText(TextContent);
        textView2.setSelected(true);
        Uri uri = Uri.parse(songs.get(position).toString());
        mediaPlayer = MediaPlayer.create(this,uri);
	    mediaPlayer.start();
        seekBar.setMax(mediaPlayer.getDuration());
        
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                   if(fromUser)
                   {
                       mediaPlayer.seekTo(progress);
                   }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                    mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        updateseek = new Thread()
        {
            @Override
            public void run() {
                int currentPosition =0;
                try {
                    while (currentPosition<mediaPlayer.getDuration())
                    {
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                        sleep(1000);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        updateseek.start();
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying())
                {
                    pause.setImageResource(R.drawable.play);
                    mediaPlayer.pause();
                }
                else
                {
                    pause.setImageResource(R.drawable.pause);
                    mediaPlayer.start();
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                if(position!=0)
                {
                    position = position-1;
                }
                else
                {
                    position=songs.size()-1;
                }
                Uri uri = Uri.parse(songs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                mediaPlayer.start();
                seekBar.setProgress(0);
                seekBar.setMax(mediaPlayer.getDuration());
                TextContent = songs.get(position).getName().replace(".mp3","");
                textView2.setText(TextContent);
                textView2.setSelected(true);
                pause.setImageResource(R.drawable.pause);


            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();

                if(position!=songs.size()-1)
                {
                    position = position+1;
                }
                else
                {
                    position=0;
                }
                Uri uri = Uri.parse(songs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                mediaPlayer.start();
                seekBar.setProgress(0);
                seekBar.setMax(mediaPlayer.getDuration());
                TextContent = songs.get(position).getName().replace(".mp3","");
                textView2.setText(TextContent);
                textView2.setSelected(true);
                pause.setImageResource(R.drawable.pause);

            }
        });

    }


}