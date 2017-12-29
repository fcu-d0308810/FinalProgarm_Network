package com.example.ihaveadream0528.finalprogarm;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ihaveadream0528 on 2017/12/27.
 */

public class Absence_fragment_0 extends Fragment {
    private View rootView;
    private User user;
    private String ClassID;
    private EditText startDate_edittext, startTime_edittext, endDate_edittext, endTime_edittext;
    private Button sendAbsence_button;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    public Absence_fragment_0(String ClassID, User user){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        this.user = user;
        this.ClassID = ClassID;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        rootView = inflater.inflate(R.layout.absence_fragment_permission0, container, false);
        setEdittext(rootView);
        setButton(rootView);
        return rootView;
    }
    private void setEdittext(View view){
        startDate_edittext = (EditText) view.findViewById(R.id.absence0_startdate_edittext);
        startTime_edittext = (EditText) view.findViewById(R.id.absence0_starttime_edittext);
        endDate_edittext = (EditText) view.findViewById(R.id.absence0_enddate_edittext);
        endTime_edittext = (EditText) view.findViewById(R.id.absence0_endtime_edittext);
        startDate_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickDateDialog(startDate_edittext);
            }
        });
        startTime_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickTimeDialog(startTime_edittext);
            }
        });
        endDate_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickDateDialog(endDate_edittext);
            }
        });
        endTime_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickTimeDialog(endTime_edittext);
            }
        });
    }
    private void setButton(View view){
        sendAbsence_button = (Button) view.findViewById(R.id.absence0_sendAbsence_button);
        sendAbsence_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String start_date = startDate_edittext.getText().toString();
                String start_time = startTime_edittext.getText().toString();
                String end_date = endDate_edittext.getText().toString();
                String end_time = endTime_edittext.getText().toString();
                if(start_date.equals("") || start_time.equals("") || end_date.equals("") || end_time.equals("")){
                    Toast.makeText(getActivity(),"請勿空白",Toast.LENGTH_SHORT).show();
                }
                else{
                    setAlertDialog_KeyReason(start_date+" "+start_time, end_date+" "+end_time);
                }
            }
        });
    }
    private void PickDateDialog(final EditText editText){

        GregorianCalendar calendar = new GregorianCalendar();
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                editText.setText(String.valueOf(year) + "-" + String.valueOf(month+1) + "-" + String.valueOf(day));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    private void PickTimeDialog(final EditText editText){
        GregorianCalendar calendar = new GregorianCalendar();
        timePickerDialog = new TimePickerDialog(getActivity(), 3,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                editText.setText(ParseTime(hour) + ":" + ParseTime(minute));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

        timePickerDialog.show();
    }
    private String ParseTime(int time){
        return String.valueOf(time/10) + String.valueOf(time%10);
    }
    private void setAlertDialog_KeyReason(final String start, final String end) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View inputView = inflater.inflate(R.layout.absence_keyreason_dialog, null);
        final EditText reason_editText = (EditText) inputView.findViewById(R.id.absence_dialog_keyreason_edittext);
        builder.setView(inputView)
                .setPositiveButton("完成", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(reason_editText.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "請勿空白", Toast.LENGTH_SHORT).show();
                            setAlertDialog_KeyReason(start, end);
                        }
                        else{
                            setAlertDialog_ShowAndCheck(start, end, reason_editText.getText().toString());
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.setTitle("請假原因");
        builder.create();
        builder.show();
    }
    private void setAlertDialog_ShowAndCheck(final String start, final String end, final String reason){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View inputView = inflater.inflate(R.layout.absence_show_dialog, null);
        final TextView content_textview = (TextView) inputView.findViewById(R.id.absence_dialog_reason_textview);
        final TextView start_textview = (TextView) inputView.findViewById(R.id.absence_dialog_start_textview);
        final TextView end_textview = (TextView) inputView.findViewById(R.id.absence_dialog_end_textview);
        content_textview.setText(reason);
        start_textview.setText(start);
        end_textview.setText(end);
        builder.setView(inputView)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String time = dateFormat.format(new Date().getTime());
                        Absence absence = new Absence(start, end, user.getName(), reason);
                        databaseReference.child(ClassID).child("absence").child("absence"+time).setValue(absence);
                        Log.i("Absence", "Send Success");
                        startDate_edittext.setText("");
                        startTime_edittext.setText("");
                        endDate_edittext.setText("");
                        endTime_edittext.setText("");
                    }
                })
                .setNegativeButton("上一步", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setAlertDialog_KeyReason(start, end);
                    }
                })
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.setTitle(user.getName());
        builder.create();
        builder.show();
    }
}
