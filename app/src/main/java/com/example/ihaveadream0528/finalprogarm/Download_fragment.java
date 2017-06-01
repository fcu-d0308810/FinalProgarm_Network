package com.example.ihaveadream0528.finalprogarm;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class Download_fragment extends Fragment {
    private View rootView;
    private ImageView imageView;
    private ListView listView;
    private ArrayList<RFile> file_list;
    private DatabaseReference databaseReference, mfilereference;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        rootView = inflater.inflate(R.layout.download_fragment, container, false);
        listView = (ListView) rootView.findViewById(R.id.download_listview);
        imageView = (ImageView) rootView.findViewById(R.id.download_imageview);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("storage").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                file_list = new ArrayList<RFile>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    RFile rfile = snapshot.getValue(RFile.class);

                    file_list.add(rfile);
                }
                listView.setAdapter(new Download_adapter(getActivity(), file_list));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        verifyStoragePermissions(getActivity());
                        final AlertDialog alertDialog = getAlertDialog(file_list.get(position).getFilename());
                        alertDialog.show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return rootView;
    }
    private void downloadFile(String filename) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://finalproject-50c25.appspot.com/images");
        StorageReference  islandRef = storageRef.child(filename);
        Log.d("islandRef:", islandRef.toString());
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Downloading");
        progressDialog.show();
        /*File rootPath = new File(Environment.getExternalStorageDirectory(), "PIC");
        if(!rootPath.exists()) {
            rootPath.mkdirs();
        }*/

        final File localFile = new File(Environment.getExternalStorageDirectory()+"/Download",filename);
        Log.d("File: ", localFile.toString());
        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();

                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                Log.e("firebase ",localFile.getAbsolutePath().toString());
                imageView.setImageBitmap(bitmap);
                Toast.makeText(getActivity(),"Download Success",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                progressDialog.dismiss();
                Log.e("firebase ",exception.toString());
                Toast.makeText(getActivity(),"Download Failed",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                progressDialog.setMessage("Downloadeding....");
            }
        });
    }
    private AlertDialog getAlertDialog(final String title){
        //產生一個Builder物件
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //設定Dialog的標題
        builder.setTitle(title);
        //設定Dialog的內容
        builder.setMessage("Are you sure to download this file?");
        //設定Positive按鈕資料
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //按下按鈕時顯示快顯
                downloadFile(title);
            }
        });
        //設定Negative按鈕資料
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //按下按鈕時顯示快顯

            }
        });
        //利用Builder物件建立AlertDialog
        return builder.create();
    }
    private void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
