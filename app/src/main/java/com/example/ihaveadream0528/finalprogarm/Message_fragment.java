package com.example.ihaveadream0528.finalprogarm;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Message_fragment extends Fragment implements ValueEventListener
{
    View rootView;
    private ArrayList<Message> mMessage;
    private EditText input_text;
    private Button send_button;
    private FirebaseUser User;
    private String mRecipient;
    private ListView listView;
    private DatabaseReference databaseReference_data;
    private DatabaseReference databaseReference_user;
    private DatabaseReference databaseReference_time;
    private Message_adapter adapter;
    //private MessagesListener messagesListener;
    public Message_fragment(){

    }
    public Message_fragment(FirebaseUser user){
        this.User = user;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        rootView = inflater.inflate(R.layout.message_fragment, container, false);
        input_text = (EditText) rootView.findViewById(R.id.message_input_edittext);
        send_button = (Button) rootView.findViewById(R.id.message_send_button);
        listView = (ListView) rootView.findViewById(R.id.message_listView);
        mMessage = new ArrayList<Message>();
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!input_text.getText().toString().equals("")){
                    Message message = new Message(input_text.getText().toString(),User.getEmail());
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                            .child("message").child("message"+ message.getMessageTime());
                    databaseReference.setValue(message);
                    mMessage.add(message);
                    input_text.setText("");
                }
            }
        });
        listView.setAdapter(new Message_adapter(getActivity(), mMessage));
        return rootView;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
            Message message = noteSnapshot.getValue(Message.class);
            mMessage.add(message);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
