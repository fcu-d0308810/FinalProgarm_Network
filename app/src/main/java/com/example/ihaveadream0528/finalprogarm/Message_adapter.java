package com.example.ihaveadream0528.finalprogarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class Message_adapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Message> values;
    public Message_adapter(Context context, ArrayList<Message> values) {
        super(context, R.layout.message, values);
        this.context = context;
        this.values = values;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.message, parent, false);
        TextView show_user = (TextView) rowView.findViewById(R.id.message_user);
        TextView show_message = (TextView) rowView.findViewById(R.id.message_text);
        TextView show_time = (TextView) rowView.findViewById(R.id.message_time);
        show_user.setText(values.get(position).getMessageUser());
        show_message.setText(values.get(position).getMessageText());
        show_time.setText(values.get(position).getMessageTime());
        return rowView;
    }

}
