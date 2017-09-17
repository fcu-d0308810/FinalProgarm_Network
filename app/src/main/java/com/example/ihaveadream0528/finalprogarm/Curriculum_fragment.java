package com.example.ihaveadream0528.finalprogarm;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ihaveadream0528 on 2017/9/7.
 */

public class Curriculum_fragment extends Fragment {
    private View rootView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        rootView = inflater.inflate(R.layout.curriculum_fragment, container, false);
        return rootView;
    }
}
