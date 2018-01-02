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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Curriculum_fragment extends Fragment{
    private View rootView;
    private TextView[][] Week = new TextView[8][5];
    private DatabaseReference get_databaseReference, push_databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private User user;
    private String ClassID;
    public Curriculum_fragment(String ClassID, User user){
        this.ClassID = ClassID;
        this.user = user;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        rootView = inflater.inflate(R.layout.curriculum_fragment, container, false);
        DeclareDayTextView(rootView);
        firebaseDatabase = FirebaseDatabase.getInstance();
        get_databaseReference = firebaseDatabase.getReference();
        push_databaseReference = firebaseDatabase.getReference();
        Permission();
        getCourse();
        return rootView;
    }
    private void Permission(){
        if(user.getPermission()==1){
            setOnClickListener_Permission_1();
        }
        else{
            //setOnClickListener_Permission_0();
        }
    }
    private void DeclareDayTextView(View view){

        //Monday
        Week[0][0] = (TextView) view.findViewById(R.id.mon_1_textview);
        Week[1][0] = (TextView) view.findViewById(R.id.mon_2_textview);
        Week[2][0] = (TextView) view.findViewById(R.id.mon_3_textview);
        Week[3][0] = (TextView) view.findViewById(R.id.mon_4_textview);
        Week[4][0] = (TextView) view.findViewById(R.id.mon_5_textview);
        Week[5][0] = (TextView) view.findViewById(R.id.mon_6_textview);
        Week[6][0] = (TextView) view.findViewById(R.id.mon_7_textview);
        Week[7][0] = (TextView) view.findViewById(R.id.mon_8_textview);
        //Tuesday
        Week[0][1] = (TextView) view.findViewById(R.id.tue_1_textview);
        Week[1][1] = (TextView) view.findViewById(R.id.tue_2_textview);
        Week[2][1] = (TextView) view.findViewById(R.id.tue_3_textview);
        Week[3][1] = (TextView) view.findViewById(R.id.tue_4_textview);
        Week[4][1] = (TextView) view.findViewById(R.id.tue_5_textview);
        Week[5][1] = (TextView) view.findViewById(R.id.tue_6_textview);
        Week[6][1] = (TextView) view.findViewById(R.id.tue_7_textview);
        Week[7][1] = (TextView) view.findViewById(R.id.tue_8_textview);
        //Wednesday
        Week[0][2] = (TextView) view.findViewById(R.id.wed_1_textview);
        Week[1][2] = (TextView) view.findViewById(R.id.wed_2_textview);
        Week[2][2] = (TextView) view.findViewById(R.id.wed_3_textview);
        Week[3][2] = (TextView) view.findViewById(R.id.wed_4_textview);
        Week[4][2] = (TextView) view.findViewById(R.id.wed_5_textview);
        Week[5][2] = (TextView) view.findViewById(R.id.wed_6_textview);
        Week[6][2] = (TextView) view.findViewById(R.id.wed_7_textview);
        Week[7][2] = (TextView) view.findViewById(R.id.wed_8_textview);
        //Thusday
        Week[0][3] = (TextView) view.findViewById(R.id.thu_1_textview);
        Week[1][3] = (TextView) view.findViewById(R.id.thu_2_textview);
        Week[2][3] = (TextView) view.findViewById(R.id.thu_3_textview);
        Week[3][3] = (TextView) view.findViewById(R.id.thu_4_textview);
        Week[4][3] = (TextView) view.findViewById(R.id.thu_5_textview);
        Week[5][3] = (TextView) view.findViewById(R.id.thu_6_textview);
        Week[6][3] = (TextView) view.findViewById(R.id.thu_7_textview);
        Week[7][3] = (TextView) view.findViewById(R.id.thu_8_textview);
        //Friday
        Week[0][4] = (TextView) view.findViewById(R.id.fri_1_textview);
        Week[1][4] = (TextView) view.findViewById(R.id.fri_2_textview);
        Week[2][4] = (TextView) view.findViewById(R.id.fri_3_textview);
        Week[3][4] = (TextView) view.findViewById(R.id.fri_4_textview);
        Week[4][4] = (TextView) view.findViewById(R.id.fri_5_textview);
        Week[5][4] = (TextView) view.findViewById(R.id.fri_6_textview);
        Week[6][4] = (TextView) view.findViewById(R.id.fri_7_textview);
        Week[7][4] = (TextView) view.findViewById(R.id.fri_8_textview);

    }
    private void setOnClickListener_Permission_1(){


        //Monday
        Week[0][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Monday 8:00 ~ 9:00", "Monday", "0");
            }
        });
        Week[1][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Monday 9:00 ~ 10:00", "Monday", "1");
            }
        });
        Week[2][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Monday 10:00 ~ 11:00", "Monday", "2");
            }
        });
        Week[3][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Monday 11:00 ~ 12:00", "Monday", "3");
            }
        });
        Week[4][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Monday 13:00 ~ 14:00", "Monday", "4");
            }
        });
        Week[5][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Monday 14:00 ~ 15:00", "Monday", "5");
            }
        });
        Week[6][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Monday 15:00 ~ 16:00", "Monday", "6");
            }
        });
        Week[7][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Monday 16:00 ~ 17:00", "Monday", "7");
            }
        });
        //Tuesday
        Week[0][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Tuesday 8:00 ~ 9:00", "Tuesday", "0");
            }
        });
        Week[1][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Tuesday 9:00 ~ 10:00", "Tuesday", "1");
            }
        });
        Week[2][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Tuesday 10:00 ~ 11:00", "Tuesday", "2");
            }
        });
        Week[3][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Tuesday 11:00 ~ 12:00", "Tuesday", "3");
            }
        });
        Week[4][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Tuesday 13:00 ~ 14:00", "Tuesday", "4");
            }
        });
        Week[5][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Tuesday 14:00 ~ 15:00", "Tuesday", "5");
            }
        });
        Week[6][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Tuesday 15:00 ~ 16:00", "Tuesday", "6");
            }
        });
        Week[7][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Tuesday 16:00 ~ 17:00", "Tuesday", "7");
            }
        });
        //Wednesday
        Week[0][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Wednesday 8:00 ~ 9:00", "Wednesday", "0");
            }
        });
        Week[1][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Wednesday 9:00 ~ 10:00", "Wednesday", "1");
            }
        });
        Week[2][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Wednesday 10:00 ~ 11:00", "Wednesday", "2");
            }
        });
        Week[3][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Wednesday 11:00 ~ 12:00", "Wednesday", "3");
            }
        });
        Week[4][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Wednesday 13:00 ~ 14:00", "Wednesday", "4");
            }
        });
        Week[5][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Wednesday 14:00 ~ 15:00", "Wednesday", "5");
            }
        });
        Week[6][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Wednesday 15:00 ~ 16:00", "Wednesday", "6");
            }
        });
        Week[7][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Wednesday 16:00 ~ 17:00", "Wednesday", "7");
            }
        });
        //Thursday
        Week[0][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Thursday 8:00 ~ 9:00", "Thursday", "0");
            }
        });
        Week[1][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Thursday 9:00 ~ 10:00", "Thursday", "1");
            }
        });
        Week[2][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Thursday 10:00 ~ 11:00", "Thursday", "2");
            }
        });
        Week[3][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Thursday 11:00 ~ 12:00", "Thursday", "3");
            }
        });
        Week[4][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Thursday 13:00 ~ 14:00", "Thursday", "4");
            }
        });
        Week[5][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Thursday 14:00 ~ 15:00", "Thursday", "5");
            }
        });
        Week[6][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Thursday 15:00 ~ 16:00", "Thursday", "6");
            }
        });
        Week[7][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Thursday 16:00 ~ 17:00", "Thursday", "7");
            }
        });
        //Friday
        Week[0][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Friday 8:00 ~ 9:00", "Friday", "0");
            }
        });
        Week[1][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Friday 9:00 ~ 10:00", "Friday", "1");
            }
        });
        Week[2][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Friday 10:00 ~ 11:00", "Friday", "2");
            }
        });
        Week[3][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Friday 11:00 ~ 12:00", "Friday", "3");
            }
        });
        Week[4][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Friday 13:00 ~ 14:00", "Friday", "4");
            }
        });
        Week[5][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Friday 14:00 ~ 15:00", "Friday", "5");
            }
        });
        Week[6][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Friday 15:00 ~ 16:00", "Friday", "6");
            }
        });
        Week[7][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog_Permission_1("Friday 16:00 ~ 17:00", "Friday", "7");
            }
        });
    }

    private void setAlertDialog_Permission_1(String title,final String day,final String postion){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View inputView = inflater.inflate(R.layout.curriculum_dialog, null);
        final EditText className_edittext = (EditText) inputView.findViewById(R.id.curriculum_dialog_edittext);
        //set two buttons
        builder.setView(inputView)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //if your edittext is empty!!
                    if(className_edittext.getText().toString().equals("")){
                        Toast.makeText(getActivity(), "請不要空白", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        uploadClassName(className_edittext.getText().toString(), day , postion);
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

    private void uploadClassName(String ClassName, String day, String position){
        push_databaseReference.child(ClassID).child("course").child(day).child(position).setValue(ClassName);
    }
    private void getCourse(){
        get_databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(int i=0; i<8; i++){
                    Week[i][0].setText(dataSnapshot.child(ClassID).child("course").child("Monday").child(String.valueOf(i)).getValue().toString());
                    Week[i][1].setText(dataSnapshot.child(ClassID).child("course").child("Tuesday").child(String.valueOf(i)).getValue().toString());
                    Week[i][2].setText(dataSnapshot.child(ClassID).child("course").child("Wednesday").child(String.valueOf(i)).getValue().toString());
                    Week[i][3].setText(dataSnapshot.child(ClassID).child("course").child("Thursday").child(String.valueOf(i)).getValue().toString());
                    Week[i][4].setText(dataSnapshot.child(ClassID).child("course").child("Friday").child(String.valueOf(i)).getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
