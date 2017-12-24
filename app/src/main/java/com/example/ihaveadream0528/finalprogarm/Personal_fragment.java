package com.example.ihaveadream0528.finalprogarm;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ihaveadream0528 on 2017/12/24.
 */

public class Personal_fragment extends Fragment{
    private View rootView;
    private TextView username_textview, self_textview, userid_textview;
    private User user;
    private FirebaseUser firebaseUser;
    public Personal_fragment(User user){
        this.user = user;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        rootView = inflater.inflate(R.layout.personal_fragment, container, false);
        username_textview = (TextView) rootView.findViewById(R.id.personal_username_textview);
        userid_textview = (TextView) rootView.findViewById(R.id.personal_userID_textview);
        self_textview = (TextView) rootView.findViewById(R.id.personal_userself_textview);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        setPersonalData();
        return rootView;
    }
    private void setPersonalData(){
        username_textview.setText(user.getName().toString());
        userid_textview.setText(firebaseUser.getEmail().toString());
        self_textview.setText(user.getIntroduction().toString());
    }
}
