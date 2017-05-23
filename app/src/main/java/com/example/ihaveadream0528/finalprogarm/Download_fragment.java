package com.example.ihaveadream0528.finalprogarm;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class Download_fragment extends Fragment {
    private View rootView;
    private Button save_button;
    private ListView listView;
    private ArrayList<RFile> file_list;
    private Download_adapter adapter;
    private DatabaseReference databaseReference_filename;
    public View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        rootView = inflater.inflate(R.layout.download_fragment, container, false);
        listView = (ListView) rootView.findViewById(R.id.download_listview);

        databaseReference_filename = FirebaseDatabase.getInstance().getReference();

        getAllFileName();
        listView.setAdapter(new Download_adapter(getActivity(), file_list));
        return rootView;
    }
    private void getAllFileName(){
        FirebaseDatabase.getInstance()
                .getReference()
                .child("storage")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
                        while(dataSnapshots.hasNext()){
                            DataSnapshot dataSnapshotChild = dataSnapshots.next();
                            RFile file = dataSnapshotChild.getValue(RFile.class);
                            file_list.add(file);
                        }
                        Toast.makeText(getActivity(), "get data finish", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
