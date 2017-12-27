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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Note_fragment extends Fragment {
    private View rootView;
    private TextView add_news_textview, add_note_textview;
    private ListView news_listview, note_listview;
    private User user;
    private String ClassID, UID;
    private ArrayList<News> show_news;
    private ArrayList<Notes> show_notes;
    private ArrayList<String> news_key;
    private ArrayList<String> notes_key;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference add_databaseReference, fix_databaseReference, remove_databaseReference, show_databaseReference;
    public Note_fragment(String ClassID, String UID, User user){
        this.ClassID = ClassID;
        this.user = user;
        this.UID = UID;
        show_news = new ArrayList<News>();
        show_notes = new ArrayList<Notes>();
        news_key = new ArrayList<String>();
        notes_key = new ArrayList<String>();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        rootView = inflater.inflate(R.layout.note_fragment, container, false);
        news_listview = (ListView) rootView.findViewById(R.id.note_news_listview);
        note_listview = (ListView) rootView.findViewById(R.id.note_note_listview);
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
            add_news_textview.setVisibility(View.GONE);
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
        //news_listview = (ListView) view.findViewById(R.id.note_news_listview);
        //news_listview.setAdapter(new News_adapter(getActivity(), show_news));
        //note_listview = (ListView) view.findViewById(R.id.note_note_listview);
        //note_listview.setAdapter(new Notes_adapter(getActivity(), show_notes));
        news_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                setAlertDialog_NEWS_SHOW(position);
            }
        });
        note_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                setAlertDialog_NOTES_SHOW(position);
            }
        });

    }
    private void getNotes(){
        show_databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot i : dataSnapshot.child(ClassID).child("user").child(UID).child("note").getChildren()){
                    if(i.child("title").getValue()!=null){
                        notes_key.add(i.getKey());
                        Notes temp_notes = new Notes();
                        temp_notes.setTitle(i.child("title").getValue().toString());
                        temp_notes.setText(i.child("text").getValue().toString());
                        show_notes.add(temp_notes);
                        note_listview.setAdapter(new Notes_adapter(getActivity(), show_notes));
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
                       news_key.add(i.getKey());
                       News temp_news = new News();
                       temp_news.setTitle(i.child("title").getValue().toString());
                       temp_news.setTime(i.child("time").getValue().toString());
                       temp_news.setText(i.child("text").getValue().toString());
                       show_news.add(temp_news);
                       news_listview.setAdapter(new News_adapter(getActivity(), show_news));
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
                .setPositiveButton("完成", new DialogInterface.OnClickListener() {
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
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        builder.setTitle(title);
        builder.create();
        builder.show();
    }
    private void setAlertDialog_NEWS_SHOW(final int position){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View inputView = inflater.inflate(R.layout.show_news_dialog, null);
        final TextView content_textview = (TextView) inputView.findViewById(R.id.news_dialog_text_textview);
        final TextView time_textview = (TextView) inputView.findViewById(R.id.news_dialog_time_textview);
        content_textview.setText(show_news.get(position).getText());
        time_textview.setText(show_news.get(position).getTime());
        //set two buttons
        if(user.getPermission()==1){
            builder.setView(inputView)
                    .setPositiveButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            remove_databaseReference.child(ClassID).child("news").child(news_key.get(position)).removeValue();
                            News_Refresh();
                        }
                    })
                    .setNeutralButton("編輯", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            setAlertDialog_FIX(position, "News");
                        }
                    });
        }
        else{
            builder.setView(inputView)
                    .setPositiveButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
        }

        builder.setTitle(show_news.get(position).getTitle());
        builder.create();
        builder.show();
    }
    private void setAlertDialog_NOTES_SHOW(final int position){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View inputView = inflater.inflate(R.layout.show_notes_dialog, null);
        final TextView content_textview = (TextView) inputView.findViewById(R.id.notes_dialog_text_textview);
        content_textview.setText(show_notes.get(position).getText());
        //set two buttons
        builder.setView(inputView)
                .setPositiveButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNeutralButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        remove_databaseReference.child(ClassID).child("user").child(UID).child("note").child(notes_key.get(position)).removeValue();
                        Notes_Refresh();
                    }
                })
                .setNegativeButton("編輯", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setAlertDialog_FIX(position, "Notes");
                    }
                });

        builder.setTitle(show_notes.get(position).getTitle());
        builder.create();
        builder.show();
    }
    private void setAlertDialog_FIX(final int position,final String token){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View inputView = inflater.inflate(R.layout.fix_news_dialog, null);
        final EditText content_edittext = (EditText) inputView.findViewById(R.id.fix_text_dialog_edittext);

        if(token.equals("News")){
            content_edittext.setText(show_news.get(position).getText());
            builder.setTitle(show_news.get(position).getTitle());
        }
        else{
            content_edittext.setText(show_notes.get(position).getText());
            builder.setTitle(show_notes.get(position).getTitle());
        }
        builder.setView(inputView)
                .setPositiveButton("完成", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(content_edittext.getText().toString().equals("")){
                           Toast.makeText(getActivity(), "請輸入內容", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(token.equals("News")){
                                show_news.get(position).setText(content_edittext.getText().toString());
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                String time = dateFormat.format(new Date().getTime());
                                show_news.get(position).setTime(time);
                                fix_databaseReference.child(ClassID).child("news").child(news_key.get(position)).setValue(show_news.get(position));
                                News_Refresh();
                            }
                            else{
                                show_notes.get(position).setText(content_edittext.getText().toString());
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                String time = dateFormat.format(new Date().getTime());
                                fix_databaseReference.child(ClassID).child("user").child(UID).child("note").child(notes_key.get(position)).setValue(show_notes.get(position));
                                Notes_Refresh();
                            }
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(token.equals("News")){
                            setAlertDialog_NEWS_SHOW(position);
                        }
                        else{
                            setAlertDialog_NOTES_SHOW(position);
                        }
                    }
                });
        builder.create();
        builder.show();
    }
    private void CreateNote(Notes notes){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = dateFormat.format(new Date().getTime());
        add_databaseReference.child(ClassID).child("user").child(UID).child("note").child("notes"+time).setValue(notes);
        Notes_Refresh();
    }
    private void CreateNews(News news){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = dateFormat.format(new Date().getTime());
        add_databaseReference.child(ClassID).child("news").child("news"+time).setValue(news);
        News_Refresh();
    }
    private void News_Refresh(){
        show_news = new ArrayList<News>();
        news_key = new ArrayList<String>();
        show_notes = new ArrayList<Notes>();
        notes_key = new ArrayList<String>();
    }
    private void Notes_Refresh(){
        show_news = new ArrayList<News>();
        news_key = new ArrayList<String>();
        show_notes = new ArrayList<Notes>();
        notes_key = new ArrayList<String>();
    }
}
