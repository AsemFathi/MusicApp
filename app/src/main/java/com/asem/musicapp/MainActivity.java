package com.asem.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_AUDIO;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView noMusicTV;

    ArrayList<AudioModel> songsList = new ArrayList<>();
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;
    private static final int PERMISSION_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.RecyclerViewSongs);
        noMusicTV = findViewById(R.id.NoSongsFound);

        requestPermissions();


    }
    private boolean checkPermissions (){
        int result = ActivityCompat.checkSelfPermission(MainActivity.this, READ_MEDIA_AUDIO);

        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        if (checkPermissions())
        {
            //Toast.makeText(this, "Permission granted.!!!!!! ", Toast.LENGTH_SHORT).show();
            loadSongs();
        }
        else
            requestPermission();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_MEDIA_AUDIO}, PERMISSION_REQUEST_CODE);
    }


    private void loadSongs()
{
    String[] projection={
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION
    };

    String selection = MediaStore.Audio.Media.IS_MUSIC +" != 0";

    String sortOrder = MediaStore.Audio.Media.TITLE + "ASC";

    Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection , selection , null, null);

    Log.i(TAG, "loadSongs: " + cursor.getCount());
    while (cursor.moveToNext())
    {


        // 1 >> Data(path) , 0 >> title , 2 >> Duration
        AudioModel songData = new AudioModel(cursor.getString(1) , cursor.getString(0), cursor.getString(2));
        Log.i(TAG, "loadSongs: " + songData);

            songsList.add(songData);
    }
    cursor.close();

    Collections.reverse(songsList);


    if (songsList.size() == 0)
    {
        noMusicTV.setVisibility(View.VISIBLE);
    }
    else
    {
        //recycler View

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SongsAdapter( getApplicationContext() , songsList));

    }
}
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean storageAccept = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccept) {
                        Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                        loadSongs();
                    } else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recyclerView != null){
            recyclerView.setAdapter(new SongsAdapter(getApplicationContext() , songsList));
        }
    }
}