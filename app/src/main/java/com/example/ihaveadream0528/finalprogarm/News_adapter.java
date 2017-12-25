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

public class News_adapter extends ArrayAdapter {
    private Context context;
    private ArrayList<News> values;
    public News_adapter(Context context, ArrayList<News> values) {
        super(context, R.layout.note_news_listview, values);
        this.context = context;
        this.values = values;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.note_news_listview, parent, false);
        TextView title_textview = (TextView) rowView.findViewById(R.id.news_title_textview);
        TextView time_textview = (TextView) rowView.findViewById(R.id.news_time_textview);
        title_textview.setText(values.get(position).getTitle());
        time_textview.setText(values.get(position).getTime());
        return rowView;
    }
}
