package com.example.ihaveadream0528.finalprogarm;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ihaveadream0528 on 2017/12/24.
 */

public class Personal_fragment extends Fragment{
    private View rootView;
    private TextView username_textview, self_textview, userid_textview;
    private User user;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String UID, ClassID;
    public Personal_fragment(String ClassID, User user, String UID){
        this.user = user;
        this.ClassID = ClassID;
        this.UID = UID;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        rootView = inflater.inflate(R.layout.personal_fragment, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        username_textview = (TextView) rootView.findViewById(R.id.personal_username_textview);
        userid_textview = (TextView) rootView.findViewById(R.id.personal_userID_textview);
        self_textview = (TextView) rootView.findViewById(R.id.personal_userself_textview);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        setPersonalData();
        reNameTextView();
        return rootView;
    }
    private void setPersonalData(){
        username_textview.setText(user.getName().toString());
        userid_textview.setText(firebaseUser.getEmail().toString());
        self_textview.setText(user.getIntroduction().toString());
    }
    private void reNameTextView(){
        username_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_reName("更改暱稱");
            }
        });
        self_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_reName("更改自我介紹");
            }
        });
    }
    private void setAlertDialog_reName(String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View inputView = inflater.inflate(R.layout.personal_dialog, null);
        final EditText rename_editText = (EditText) inputView.findViewById(R.id.rename_dialog_edittext);
        if(title.equals("更改暱稱")){
            rename_editText.setText(username_textview.getText());
            rename_editText.selectAll();
            builder.setView(inputView)
                    .setPositiveButton("完成", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(rename_editText.getText().equals("")){
                                Toast.makeText(getActivity(), "請不要空白", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                databaseReference.child(ClassID).child("user").child(UID).child("name").setValue(rename_editText.getText().toString());
                            }
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
        }
        else{
            rename_editText.setText(self_textview.getText());
            rename_editText.selectAll();
            builder.setView(inputView)
                    .setPositiveButton("完成", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(rename_editText.getText().equals("")){
                                Toast.makeText(getActivity(), "請不要空白", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                databaseReference.child(ClassID).child("user").child(UID).child("self").setValue(rename_editText.getText().toString());
                            }
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
        }

        builder.setTitle(title);
        builder.create();
        builder.show();
    }
}
