package com.example.andriod.earthquake;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.RemoteException;
import android.provider.UserDictionary;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;


public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {

                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                       //The AUDIOFOCUS_GAIN case means we have gained focus and resume playback
                        mediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                       //The AUDIOFOCUS_CASE means we've lost focus and
                        //stop playback and clean up resources
                        releasedMediaPlayer();
                    }
                }
            };


    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releasedMediaPlayer();

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        //create an array of words

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("one", "lutti", R.drawable.number_one));
        words.add(new Word("Two", "otiiko", R.drawable.number_two));
        words.add(new Word("Three", "tolookosu", R.drawable.number_three));
        words.add(new Word("Four", "oyyisa", R.drawable.number_four));
        words.add(new Word("Five", "massokka", R.drawable.number_five));
        words.add(new Word("Six", "temmokka", R.drawable.number_six));
        words.add(new Word("Seven", "kenekaku", R.drawable.number_seven));
        words.add(new Word("Eight", "kawinta", R.drawable.number_eight));
        words.add(new Word("Nine", "wo'e", R.drawable.number_nine));
        words.add(new Word("Ten", "na'aacha", R.drawable.number_ten));
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Toast.makeText(NumbersActivity.this,"number list",Toast.LENGTH_SHORT);*/
                Word word = words.get(position);
                // release the mediaPlayer if it currently exists because we are about to play a
                // different sound file
                releasedMediaPlayer();
                //request audio focus for playback
                int result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        //use the music scream.
                        AudioManager.STREAM_MUSIC,
                        //request permanent focus
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    //we have a audio focus now

                    //create an setup the [@linkMediaPlayer] for the audio resource associated
                    //with the current word
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getmAdioResourceId());
                    //start the audio file
                    mediaPlayer.start();
                    //setup a listener on the mediaplayer ,so that we can stop and releasethe
                    //media player once the sounds has finished playing
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }


            }
        });

    }


    @Override
    protected void onStop() {
        super.onStop();
        //When the activity is stopped, release the media player resources because
        //we won't be playing again
        releasedMediaPlayer();
    }

    private void releasedMediaPlayer() {
        //if the media player is not null, then it may be currently playing the sound.
        if (mediaPlayer != null) {
            //Regardless of the current state of the media player, release it resource
            //because we no longer need it.
            mediaPlayer.release();
            //Set the media player black to null,because in our code,we've decided that
            //setting the media player to null is an easy way to tell that the media player
            //is not configured to play an audio file at that moment
            mediaPlayer = null;
            //Abandon audio focus when playblack complete
            audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);


        }
    }


}
