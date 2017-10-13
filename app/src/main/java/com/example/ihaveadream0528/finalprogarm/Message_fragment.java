package com.example.ihaveadream0528.finalprogarm;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Message_fragment extends Fragment
{
    private View rootView;
    private ArrayList<Message> mMessage;
    private EditText input_text;
    private Button send_button;
    private FirebaseUser User;
    private ListView listView;
    private MyFirebaseMessagingService myFirebaseMessagingService;
    private DatabaseReference update;
    public Message_fragment(){

    }
    public Message_fragment(FirebaseUser user){
        this.User = user;
        mMessage = new ArrayList<Message>();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        //getMessage();
        rootView = inflater.inflate(R.layout.message_fragment, container, false);
        input_text = (EditText) rootView.findViewById(R.id.message_input_edittext);
        send_button = (Button) rootView.findViewById(R.id.message_send_button);
        listView = (ListView) rootView.findViewById(R.id.message_listView);


        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!input_text.getText().toString().equals("")){
                    Message message = new Message(input_text.getText().toString(),User.getEmail());
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                            .child("TEST0919").child("message").child("message"+ message.getMessageTime());
                    databaseReference.setValue(message);
                    input_text.setText("");
                }
            }
        });
        CheckUpdate();
        return rootView;
    }
    private void CheckUpdate(){
        update = FirebaseDatabase.getInstance().getReference().child("TEST0919").child("message");
        update.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //mMessage = new ArrayList<Message>();

                Message message = dataSnapshot.getValue(Message.class);
                Log.d("checkupdate:", dataSnapshot.toString());
                mMessage.add(message);
                listView.setAdapter(new Message_adapter(getActivity(), mMessage));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
    
}
