package com.example.ihaveadream0528.finalprogarm;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ImageAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<RFile> rFiles;
    public ImageAdapter(Context context, ArrayList<RFile> rFiles) {
        this.mContext = context;
        this.rFiles = rFiles;
        Log.d("load ", rFiles.get(0).getUrl());
    }
    @Override
    public int getCount() {
        return rFiles.size();
    }

    @Override
    public Object getItem(int i) {
        return rFiles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if(view == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(345, 345));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else{
            imageView = (ImageView) view;
        }
        //imageView.setImageBitmap();
        //imageView.setImageResource(R.drawable.ic_camera);
        Picasso.with(mContext).load(rFiles.get(i).getUrl()).fit().centerCrop().error(R.drawable.ic_warning).into(imageView);
        //
        return imageView;
    }
}
