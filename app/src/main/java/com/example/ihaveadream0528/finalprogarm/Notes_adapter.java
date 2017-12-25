package com.example.ihaveadream0528.finalprogarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ihaveadream0528 on 2017/12/25.
 */

public class Notes_adapter extends ArrayAdapter{
    private Context context;
    private ArrayList<Notes> values;
    public Notes_adapter(Context context, ArrayList<Notes> values) {
        super(context, R.layout.note_note_listview, values);
        this.context = context;
        this.values = values;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.note_note_listview, parent, false);
        TextView title_textview = (TextView) rowView.findViewById(R.id.note_title_textview);
        title_textview.setText(values.get(position).getTitle());
        return rowView;
    }
}
