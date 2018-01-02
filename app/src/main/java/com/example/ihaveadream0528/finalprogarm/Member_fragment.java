package com.example.ihaveadream0528.finalprogarm;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ihaveadream0528 on 2017/4/25.
 */

public class Member_fragment extends Fragment {
    private View rootView;
    private User user;
    private String ClassID;
    private String UID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<User> show_user;
    private ListView show_user_listView;
    public Member_fragment(String ClassID, User user, String UID){
        this.ClassID = ClassID;
        this.user = user;
        this.UID = UID;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        rootView = inflater.inflate(R.layout.member_fragment, container, false);
        show_user_listView = (ListView) rootView.findViewById(R.id.member_listview);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        getUsers();
        return rootView;
    }
    private void getUsers(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                show_user = new ArrayList<User>();
                for(DataSnapshot i : dataSnapshot.child(ClassID).child("user").getChildren()){
                    if(i.child("name").getValue()!=null){
                        if(i.getKey().equals(UID)){

                        }
                        else{
                            User user = new User();
                            user.setName(i.child("name").getValue().toString());
                            user.setIntroduction(i.child("self").getValue().toString());
                            if(i.child("permission").toString().equals("1")){
                                user.setPermission(1);
                            }
                            else{
                                user.setPermission(0);
                            }
                            show_user.add(user);
                        }
                    }
                }
                show_user_listView.setAdapter(new User_adapter(getActivity(), show_user));
                show_user_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        setAlertDialog_USER_SHOW(position);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void setAlertDialog_USER_SHOW(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View inputView = inflater.inflate(R.layout.member_dialog, null);
        final TextView id_textview = (TextView) inputView.findViewById(R.id.member_dialog_id_textview);
        final TextView self_textview = (TextView) inputView.findViewById(R.id.member_dialog_self_textview);
        final TextView charater_textview = (TextView) inputView.findViewById(R.id.member_dialog_charater_textview);
        id_textview.setText("暱稱："+show_user.get(position).getName());
        self_textview.setText("自我介紹："+show_user.get(position).getIntroduction());
        if(show_user.get(position).getPermission()==1){
            charater_textview.setText("角色：管理者");
        }
        else{
            charater_textview.setText("角色：成員");
        }
        builder.setView(inputView)
                .setPositiveButton("了解", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.setTitle("成員資訊");
        builder.create();
        builder.show();
    }
}
