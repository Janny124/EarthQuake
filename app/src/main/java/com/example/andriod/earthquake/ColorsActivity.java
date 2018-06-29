package com.example.andriod.earthquake;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;

public class ColorsActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new
            AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);

                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {

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
        //create an array variable
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("red","wetiti",R.drawable.color_black,R.raw.color_black));
        words.add(new Word("mustard yellow", "chiwiiti",R.drawable.color_brown,R.raw.color_brown));
        words.add(new Word("dusty yellow","topiise",R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("green","chokokki",R.drawable.color_gray,R.raw.color_gray));
        words.add(new Word("brown","takaakki",R.drawable.color_green,R.raw.color_green));
        words.add(new Word("grey", "topoopi",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
        words.add(new Word("black","kululli",R.drawable.color_red,R.raw.color_red));
        words.add(new Word("white","kelelli",R.drawable.color_white,R.raw.color_white));

        WordAdapter adapter = new WordAdapter(this, words,R.color.category_color);
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


                    mediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getmAdioResourceId());
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
