package com.example.andriod.earthquake;

/**
 * Created by ezikel on 5/14/2018.
 */

public class Word {
    private String mDefaultTranslation;

    private String mMiwokTranslation;
    private int mImageResourceId = NO_IMAGE_PROVIDER;
    private static final int NO_IMAGE_PROVIDER = -1;
    private int  mAudioResourceId;

    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;

    }

    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }
    //get default translation of words
    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }

    //get miwokTranslation of words
    public String getmMiwokTranslation() {
        return mMiwokTranslation;
    }


    public  int getmImageResourceId(){
        return mImageResourceId;
    }

    //
    public boolean hasImage(){
        return  mImageResourceId != NO_IMAGE_PROVIDER;}
        public int getmAdioResourceId(){
            return  mAudioResourceId;
        }
    }





