package com.example.ihaveadream0528.finalprogarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ihaveadream0528 on 2017/12/29.
 */

public class User_adapter extends ArrayAdapter {
    private Context context;
    private ArrayList<User> values;
    public User_adapter(Context context, ArrayList<User> values) {
        super(context, R.layout.member_listview, values);
        this.context = context;
        this.values = values;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.member_listview, parent, false);
        TextView name_textview = (TextView) rowView.findViewById(R.id.member_listview_name_textview);
        TextView charater_textview = (TextView) rowView.findViewById(R.id.member_listview_charater_textview);
        name_textview.setText(values.get(position).getName().toString());
        if(values.get(position).getPermission()==1){
            charater_textview.setText("管理員");
        }
        else{
            charater_textview.setText("成員");
        }
        return rowView;
    }
}
