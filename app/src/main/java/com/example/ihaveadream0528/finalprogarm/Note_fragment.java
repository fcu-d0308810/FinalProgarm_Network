package com.example.ihaveadream0528.finalprogarm;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Note_fragment extends Fragment {
    private View rootView;
    private TextView add_news_textview, add_note_textview;
    private ListView news_listview, note_listview;
    private User user;
    private String ClassID, UID;
    private ArrayList<News> show_news;
    private ArrayList<Notes> show_notes;
    private int note_count, news_count;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference add_databaseReference, fix_databaseReference, remove_databaseReference, show_databaseReference;
    public Note_fragment(String ClassID, String UID, User user){
        this.ClassID = ClassID;
        this.user = user;
        this.UID = UID;
        show_news = new ArrayList<News>();
        show_notes = new ArrayList<Notes>();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        rootView = inflater.inflate(R.layout.note_fragment, container, false);
        setDatabase();
        getNotes();
        getNews();
        setListView(rootView);
        setPermissionButton(rootView);
        return rootView;
    }
    private void setDatabase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        add_databaseReference = firebaseDatabase.getReference();
        fix_databaseReference = firebaseDatabase.getReference();
        remove_databaseReference = firebaseDatabase.getReference();
        show_databaseReference = firebaseDatabase.getReference();
    }
    private void setPermissionButton(View view){
        add_news_textview = (TextView) view.findViewById(R.id.note_news_add_textview);
        add_note_textview = (TextView) view.findViewById(R.id.note_note_add_textview);
        add_note_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add a note
                setAlertDialog_ADD("Add Note");
            }
        });
        if(user.getPermission()!=1){
            add_note_textview.setVisibility(View.GONE);
        }
        else{
            add_news_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Add a new
                    setAlertDialog_ADD("Add News");
                }
            });
        }
    }
    private void setListView(View view){
        news_listview = (ListView) view.findViewById(R.id.note_news_listview);
        news_listview.setAdapter(new News_adapter(getActivity(), show_news));
        note_listview = (ListView) view.findViewById(R.id.note_note_listview);
        note_listview.setAdapter(new Notes_adapter(getActivity(), show_notes));
        news_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(user.getPermission()!=1){

                }
                else{

                }
            }
        });
        note_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }
    private void getNotes(){
        show_databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot i : dataSnapshot.child(ClassID).child("user").child(UID).child("note").getChildren()){

                    if(i.child("title").getValue()!=null){
                        Notes temp_notes = new Notes();
                        temp_notes.setTitle(i.child("title").getValue().toString());
                        temp_notes.setText(i.child("text").getValue().toString());
                        show_notes.add(temp_notes);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void getNews(){
        show_databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot i : dataSnapshot.child(ClassID).child("news").getChildren()){
                   if(i.child("title").getValue()!=null){
                       News temp_news = new News();
                       temp_news.setTitle(i.child("title").getValue().toString());
                       temp_news.setTime(i.child("time").getValue().toString());
                       temp_news.setText(i.child("text").getValue().toString());
                       show_news.add(temp_news);
                   }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void setAlertDialog_ADD(final String title){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View inputView = inflater.inflate(R.layout.note_add_dialog, null);
        final EditText data_title = (EditText) inputView.findViewById(R.id.title_dialog_edittext);
        final EditText data_content = (EditText) inputView.findViewById(R.id.content_dialog_edittext);
        //set two buttons
        builder.setView(inputView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //if your edittext is empty!!
                        if(data_title.getText().toString().equals("") || data_content.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "Do not empty.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(title.equals("Add Note")){
                                Notes notes = new Notes(data_title.getText().toString(),data_content.getText().toString());
                                CreateNote(notes);
                            }
                            else{
                                News news = new News(data_title.getText().toString(), data_content.getText().toString());
                                CreateNews(news);
                            }
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        builder.setTitle(title);
        builder.create();
        builder.show();
    }
    private void CreateNote(Notes notes){
        add_databaseReference.child(ClassID).child("user").child(UID).child("note").setValue(notes);
    }
    private void CreateNews(News news){
        add_databaseReference.child(ClassID).child("user").child("news").setValue(news);
    }
}
