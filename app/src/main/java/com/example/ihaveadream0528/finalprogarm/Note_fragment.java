package com.example.ihaveadream0528.finalprogarm;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;


public class Note_fragment extends Fragment {
    private View rootView;
    private EditText editText;
    private Calendar calendar = Calendar.getInstance();
    private ImageView imageView;
    private Bitmap bitmap;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        rootView = inflater.inflate(R.layout.note_fragment, container, false);
        editText = (EditText) rootView.findViewById(R.id.pickdate);
        imageView = (ImageView) rootView.findViewById(R.id.note_imageView);

        updateTextLabel();
        return rootView;
    }
    private void updateTextLabel(){
        editText.setText(calendar.getTime().toString());
    }
}
