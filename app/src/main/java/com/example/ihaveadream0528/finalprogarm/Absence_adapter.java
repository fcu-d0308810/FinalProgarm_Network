package com.example.ihaveadream0528.finalprogarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ihaveadream0528 on 2017/12/27.
 */

public class Absence_adapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Absence> values;
    public Absence_adapter(Context context, ArrayList<Absence> values) {
        super(context, R.layout.note_news_listview, values);
        this.context = context;
        this.values = values;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.absence_listview, parent, false);
        TextView name_textview = (TextView) rowView.findViewById(R.id.absence_listview_name_textview);
        TextView start_time_textview = (TextView) rowView.findViewById(R.id.absence_listview_starttime_textview);
        name_textview.setText(values.get(position).getName());
        start_time_textview.setText(values.get(position).getStart());
        return rowView;
    }
}
