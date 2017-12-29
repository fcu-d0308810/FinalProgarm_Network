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
 * Created by ihaveadream0528 on 2017/12/27.
 */

public class Absence_fragment_1 extends Fragment {
    private View rootView;
    private String ClassID;
    private User user;
    private ArrayList<Absence> show_absence;
    private ArrayList<String> absence_key;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ListView show_listview;
    public Absence_fragment_1(String ClassID, User user){
        this.ClassID = ClassID;
        this.user = user;
        show_absence = new ArrayList<Absence>();
        absence_key = new ArrayList<String>();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        rootView = inflater.inflate(R.layout.absence_fragment_permission1, container, false);
        show_listview = (ListView) rootView.findViewById(R.id.absence_show_listview);
        setDatabase();
        showAbsence();
        return rootView;
    }
    private void setDatabase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    private void showAbsence(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot i : dataSnapshot.child(ClassID).child("absence").getChildren()){
                    if(i.child("name").getValue()!=null){
                        Absence absence = new Absence();
                        absence.setEnd(i.child("end").getValue().toString());
                        absence.setName(i.child("name").getValue().toString());
                        absence.setReason(i.child("reason").getValue().toString());
                        absence.setStart(i.child("start").getValue().toString());
                        show_absence.add(absence);
                    }
                }
                show_listview.setAdapter(new Absence_adapter(getActivity(), show_absence));
                show_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        setAlertDialog_NEWS_SHOW(position);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void setAlertDialog_NEWS_SHOW(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View inputView = inflater.inflate(R.layout.absence_show_dialog, null);
        final TextView content_textview = (TextView) inputView.findViewById(R.id.absence_dialog_reason_textview);
        final TextView start_textview = (TextView) inputView.findViewById(R.id.absence_dialog_start_textview);
        final TextView end_textview = (TextView) inputView.findViewById(R.id.absence_dialog_end_textview);
        content_textview.setText(show_absence.get(position).getReason());
        start_textview.setText(show_absence.get(position).getStart());
        end_textview.setText(show_absence.get(position).getEnd());
        builder.setView(inputView)
                .setPositiveButton("已讀", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseReference.child(ClassID).child("absence").child(absence_key.get(position)).removeValue();
                        Refresh();
                    }
                });
        builder.setTitle(show_absence.get(position).getName().toString());
        builder.create();
        builder.show();
    }
    private void Refresh(){
        show_absence = new ArrayList<Absence>();
        absence_key = new ArrayList<String>();
    }
}
