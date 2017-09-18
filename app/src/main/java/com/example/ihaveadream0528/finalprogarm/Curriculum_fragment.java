package com.example.ihaveadream0528.finalprogarm;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Curriculum_fragment extends Fragment{
    private View rootView;
    private TextView[][] Week = new TextView[8][5];
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        rootView = inflater.inflate(R.layout.curriculum_fragment, container, false);
        DeclareDayTextView(rootView);
        setOnClickListener();
        return rootView;
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
    private void setOnClickListener(){


        //Monday
        Week[0][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Monday 8:00 ~ 9:00");
            }
        });
        Week[1][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Monday 9:00 ~ 10:00");
            }
        });
        Week[2][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Monday 10:00 ~ 11:00");
            }
        });
        Week[3][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Monday 11:00 ~ 12:00");
            }
        });
        Week[4][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Monday 13:00 ~ 14:00");
            }
        });
        Week[5][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Monday 14:00 ~ 15:00");
            }
        });
        Week[6][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Monday 15:00 ~ 16:00");
            }
        });
        Week[7][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Monday 16:00 ~ 17:00");
            }
        });
        //Tuesday
        Week[0][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Tuesday 8:00 ~ 9:00");
            }
        });
        Week[1][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Tuesday 9:00 ~ 10:00");
            }
        });
        Week[2][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Tuesday 10:00 ~ 11:00");
            }
        });
        Week[3][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Tuesday 11:00 ~ 12:00");
            }
        });
        Week[4][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Tuesday 13:00 ~ 14:00");
            }
        });
        Week[5][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Tuesday 14:00 ~ 15:00");
            }
        });
        Week[6][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Tuesday 15:00 ~ 16:00");
            }
        });
        Week[7][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Tuesday 16:00 ~ 17:00");
            }
        });
        //Wednesday
        Week[0][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Wednesday 8:00 ~ 9:00");
            }
        });
        Week[1][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Wednesday 9:00 ~ 10:00");
            }
        });
        Week[2][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Wednesday 10:00 ~ 11:00");
            }
        });
        Week[3][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Wednesday 11:00 ~ 12:00");
            }
        });
        Week[4][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Wednesday 13:00 ~ 14:00");
            }
        });
        Week[5][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Wednesday 14:00 ~ 15:00");
            }
        });
        Week[6][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Wednesday 15:00 ~ 16:00");
            }
        });
        Week[7][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Wednesday 16:00 ~ 17:00");
            }
        });
        //Thursday
        Week[0][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Thursday 8:00 ~ 9:00");
            }
        });
        Week[1][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Thursday 9:00 ~ 10:00");
            }
        });
        Week[2][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Thursday 10:00 ~ 11:00");
            }
        });
        Week[3][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Thursday 11:00 ~ 12:00");
            }
        });
        Week[4][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Thursday 13:00 ~ 14:00");
            }
        });
        Week[5][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Thursday 14:00 ~ 15:00");
            }
        });
        Week[6][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Thursday 15:00 ~ 16:00");
            }
        });
        Week[7][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Thursday 16:00 ~ 17:00");
            }
        });
        //Friday
        Week[0][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Friday 8:00 ~ 9:00");
            }
        });
        Week[1][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Friday 9:00 ~ 10:00");
            }
        });
        Week[2][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Friday 10:00 ~ 11:00");
            }
        });
        Week[3][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Friday 11:00 ~ 12:00");
            }
        });
        Week[4][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Friday 13:00 ~ 14:00");
            }
        });
        Week[5][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Friday 14:00 ~ 15:00");
            }
        });
        Week[6][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Friday 15:00 ~ 16:00");
            }
        });
        Week[7][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertDialog("Friday 16:00 ~ 17:00");
            }
        });
    }
    private void setAlertDialog(String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //set two buttons
        builder.setView(inflater.inflate(R.layout.curriculum_dialog, null))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

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
}
