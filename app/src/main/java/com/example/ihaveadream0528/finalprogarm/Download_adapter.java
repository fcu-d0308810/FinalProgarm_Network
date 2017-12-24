package com.example.ihaveadream0528.finalprogarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class Download_adapter extends ArrayAdapter<RFile> {
    private Context context;
    private ArrayList<RFile> values;
    public Download_adapter(Context context,ArrayList<RFile> values) {
        super(context, R.layout.download_listview, values);
        this.context = context;
        this.values = values;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.download_listview, parent, false);
        TextView filename = (TextView) rowView.findViewById(R.id.download_filename);
        TextView user = (TextView) rowView.findViewById(R.id.download_user);
        TextView time = (TextView) rowView.findViewById(R.id.download_time);
        filename.setText(values.get(position).getFilename());
        user.setText(values.get(position).getUser());
        return rowView;
    }
}
