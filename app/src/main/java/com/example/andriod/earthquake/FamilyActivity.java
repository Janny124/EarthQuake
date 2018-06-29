package com.example.andriod.earthquake;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;

public class FamilyActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =new
            AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    } else if(focusChange == AudioManager.AUDIOFOCUS_GAIN) {

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
        setContentView(R.layout.word_list);
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        //create an array variable
       final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("father","epe",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("mother", "ata",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("son","angsi",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("older brother ","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("younger brother", "chalitti",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("older sister","tete",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_older_sister));
        words.add(new Word("grandma","ama",R.drawable.family_grandfather, R.raw.family_grandfather));
        words.add(new Word("grandpa","paapa",R.drawable.family_grandmother, R.raw.family_grandmother));
        WordAdapter adapter = new WordAdapter(this, words,R.color.category_family);
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



                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getmAdioResourceId());

                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }

        });
            /*LinearLayout rootView =(LinearLayout) findViewById(R.id.rootView);
                //
                //int index = 0;
                for (int index =0; index<words.size(); index++){
                    TextView wordsView = new  TextView(this);
                    wordsView.setText(words.get(index));
                    rootView.addView(wordsView);*/

        // index++;
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



