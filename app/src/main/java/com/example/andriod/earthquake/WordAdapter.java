package com.example.andriod.earthquake;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ezikel on 5/14/2018.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    private  int mColorResourceId;
    public  WordAdapter(Activity context, ArrayList<Word> words, int colorResourceId){
        super(context,0,words );
        mColorResourceId = colorResourceId;
    }

    public View getView(int position,  View convertView, ViewGroup parent) {
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.activity_list_item, parent,false );

            }

            Word currentWord = getItem(position);
            TextView miworkTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
            miworkTextView.setText((currentWord.getmMiwokTranslation()));

            TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
            defaultTextView.setText(currentWord.getmDefaultTranslation());

            ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
                imageView.setImageResource(currentWord.getmImageResourceId());
            if (currentWord.hasImage()){
                imageView.setImageResource(currentWord.getmImageResourceId());
                imageView.setVisibility(View.VISIBLE);
            }
            else{
                imageView.setVisibility(View.GONE);
            }
            View textContainer = listItemView.findViewById(R.id.text_container);

            int color = ContextCompat.getColor(getContext(),mColorResourceId);
            textContainer.setBackgroundColor(color);
            return listItemView;

    }
}
