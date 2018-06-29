package com.example.andriod.earthquake;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Context;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;


import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
        AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =new
                AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                         if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT  ||
                                 focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                              mediaPlayer.pause();
                              mediaPlayer.seekTo(0);
                         } else if(focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                                 mediaPlayer.start();
                         } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                             releasedMediaPlayer();
                         }
                }
        };




        private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                releasedMediaPlayer();

            }
        };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);
           audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("where are you going","minto wukus",R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name", "tinne oyaase 'ne", R.raw.phrase_what_is_your_name));
        words.add(new Word("How are you feeling","michekese?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeling good","kuchi achit", R.raw.phrase_im_coming));
        words.add(new Word("You are coming", "eenes 'a", R.raw.phrase_are_you_coming));
        words.add(new Word(" Yes,I'm coming","hee'eenem",R.raw.phrase_yes_im_coming));
        words.add(new Word("I'm coming","eenem", R.raw.phrase_im_coming));
        words.add(new Word("Let's go","yoowutis",R.raw.phrase_lets_go));
        words.add(new Word("come here","enni'nem",R.raw.phrase_come_here));
        WordAdapter adapter = new WordAdapter(this, words,R.color.category_phrases);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Toast.makeText(NumbersActivity.this,"number list",Toast.LENGTH_SHORT);*/
                Word word = words.get(position);
                releasedMediaPlayer();
                    int result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                         AudioManager.STREAM_MUSIC,
                         AudioManager.AUDIOFOCUS_GAIN) ;
                    if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                        mediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getmAdioResourceId());
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(mCompletionListener);
                    }
            }
        });
    }
    @Override
    protected  void onStop(){
        super.onStop();
        releasedMediaPlayer();
    }
    private void releasedMediaPlayer(){
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);

        }
    }
}
