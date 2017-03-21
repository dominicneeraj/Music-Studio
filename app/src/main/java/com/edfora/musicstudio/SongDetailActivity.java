package com.edfora.musicstudio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neeraj on 3/19/2017.
 */

public class SongDetailActivity extends AppCompatActivity {
    private static Response response;
    static final String MAIN_URL = "http://starlord.hackerearth.com/edfora/cokestudio";
    MediaPlayer mediaplayer;
    private String TAG = SongDetailActivity.class.getSimpleName();
    private String song;
    private String url;
    private String artists;
    private String cover;
    private Integer position;
    private TextView songs;
    private TextView artist;
    private boolean flag;
    private ProgressDialog dialog;
    private ImageView next;
    private ImageView prev;
    private ImageView imageView;
    private List<Song> songList;
    private String song1;
    private String url1;
    private String artists1;
    private String cover1;
    private Song songi;
    private Integer n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_detail_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle extras = getIntent().getExtras();
        if (extras!= null) {

            song1 = extras.getString("song");

             url1 = extras.getString("url");
            Log.e(TAG, "True"+url);
            artists1= extras.getString("artists");
            cover1 = extras.getString("cover");

            position=extras.getInt("position");
        }
        songList = new ArrayList<>();

        new GetDataTask().execute();





        songs=(TextView) findViewById(R.id.musicTitle);

        artist=(TextView) findViewById(R.id.musicArtistName);

        mediaplayer= new MediaPlayer();
        next = (ImageView) findViewById(R.id.next);
        prev = (ImageView) findViewById(R.id.prev);



        imageView = (ImageView) findViewById(R.id.control);

    }


    class GetDataTask extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */
            dialog = new ProgressDialog(SongDetailActivity.this);
            dialog.setTitle("Please wait...");
            dialog.setMessage("Loading");
            dialog.show();
        }

        @Nullable
        @Override
        protected String doInBackground(Void... params) {

            try {
                OkHttpClient client = new OkHttpClient();




                Request request = new Request.Builder()
                        .url(MAIN_URL)
                        .build();
                response = client.newCall(request).execute();

                return response.body().string();
            } catch (@NonNull IOException e) {
                Log.e(TAG, "" + e.getLocalizedMessage());
            }


            return null;
        }

        @Override
        public void onPostExecute(String Response) {
            super.onPostExecute(Response);
            dialog.dismiss();
            JSONArray jsonarray = null;
            try {
                jsonarray = new JSONArray(Response);
                Log.e(TAG, String.valueOf(jsonarray));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < jsonarray.length(); i++) {
                Song song = new Song();
                JSONObject json = null;
                try {
                    json = jsonarray.getJSONObject(i);
                    song.setSong(json.getString("song"));
                    song.setUrl(json.getString("url"));
                    song.setArtists(json.getString("artists"));
                    song.setCover(json.getString("cover_image"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                songList.add(song);

            }
            songi = songList.get(position);
            song=songi.getSong();
            artists=songi.getArtists();
            cover=songi.getCover();
            url=songi.getUrl();
            songs.setText(song);

            artist.setText(artists);

            try {
                Glide.with(SongDetailActivity.this).load(cover).into((ImageView) findViewById(R.id.imageView));
            } catch (Exception e) {
                e.printStackTrace();
            }

            next.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    imageView.setBackgroundResource(R.drawable.ic_action_play);
                    mediaplayer.stop();
                     n=position++;
                    songi=songList.get(n);
                    song=songi.getSong();
                    artists=songi.getArtists();
                    cover=songi.getCover();
                    url=songi.getUrl();
                    songs.setText(song);

                    artist.setText(artists);

                    try {
                        Glide.with(SongDetailActivity.this).load(cover).into((ImageView) findViewById(R.id.imageView));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });




            prev.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    imageView.setBackgroundResource(R.drawable.ic_action_play);
                    mediaplayer.stop();
                    n=position--;
                    songi=songList.get(n);
                    song=songi.getSong();
                    artists=songi.getArtists();
                    cover=songi.getCover();
                    url=songi.getUrl();
                    songs.setText(song);

                    artist.setText(artists);

                    try {
                        Glide.with(SongDetailActivity.this).load(cover).into((ImageView) findViewById(R.id.imageView));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    flag=false;
                    try {

                        mediaplayer.setDataSource(url);
                        Log.e(TAG, "media "+url);
                        try {
                            mediaplayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if (!mediaplayer.isPlaying()) {
                        imageView.setBackgroundResource(R.drawable.ic_action_pause);
                        mediaplayer.start();


                    } else {
                        imageView.setBackgroundResource(R.drawable.ic_action_play);
                        mediaplayer.stop();

                    }

                }
            });
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();

        if (i == android.R.id.home) {
          mediaplayer.stop();
           mediaplayer.release();
            finish();
            return true;

        }

        else {
            return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaplayer.stop();
        mediaplayer.release();
    }
}

