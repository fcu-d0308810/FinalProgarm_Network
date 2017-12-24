package com.example.ihaveadream0528.finalprogarm;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


public class picture_frament extends Fragment {
    private View rootView;
    private StorageReference storageReference;
    private GridView gridView;
    private Button upload_button;
    private Uri uri;
    private User user;
    private String ClassID;
    private DatabaseReference databaseReference;
    private ArrayList<RFile> file_list;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public picture_frament(String ClassID, User user){
        this.user = user;
        this.ClassID = ClassID;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        rootView = inflater.inflate(R.layout.picture_fragment, container, false);
        file_list = new ArrayList<RFile>();
        storageReference = FirebaseStorage.getInstance().getReference();
        gridView = (GridView) rootView.findViewById(R.id.picture_gridview);
        upload_button = (Button) rootView.findViewById(R.id.picture_upload_button);
        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickPicture();
            }
        });
        ShowPicture();
        return rootView;
    }
    private void ShowPicture(){
        GetPicture();
    }
    private void GetPicture(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(ClassID).child("storage").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    RFile rfile = snapshot.getValue(RFile.class);
                    Log.d("file ",rfile.getUrl());
                    file_list.add(rfile);
                }
                gridView.setAdapter(new ImageAdapter(getActivity(), file_list));
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        getAlertDialog(file_list.get(i));
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void PickPicture(){
        if(ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    2000);
        }
        else {
            startGallery();
        }
    }
    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                uri = data.getData();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //showPic_view.setImageBitmap(bitmapImage);
                setAlertDialog("Upload Picture", bitmapImage);
            }
        }
    }
    private void setAlertDialog(String title, final Bitmap picture){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View inputView = inflater.inflate(R.layout.picture_dialog_upload, null);
        final ImageView picture_imageView = (ImageView) inputView.findViewById(R.id.picture_dialog_imageview);
        final EditText pictureName_edittext = (EditText) inputView.findViewById(R.id.picture_dialog_edittext);
        picture_imageView.setImageBitmap(picture);
        //set two buttons
        builder.setView(inputView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //if your edittext is empty!!
                        if(pictureName_edittext.getText().toString().equals("")){
                            setAlertDialog("Upload Picture", picture);
                            Toast.makeText(getActivity(), "Your class name is empty.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            uploadFile(pictureName_edittext.getText().toString());
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNeutralButton("Choose another", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PickPicture();
                    }
                });

        builder.setTitle(title);
        builder.create();
        builder.show();
    }
    private void getAlertDialog(final RFile rFile){
        //產生一個Builder物件
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View inputView = inflater.inflate(R.layout.picture_dialog_download, null);
        final ImageView picture_imageView = (ImageView) inputView.findViewById(R.id.picture_dialog_imageview2);
        final TextView user_textview = (TextView) inputView.findViewById(R.id.picture_dialog_download_user);
        Picasso.with(getActivity()).load(rFile.getUrl()).fit().centerCrop().error(R.drawable.ic_warning).into(picture_imageView);
        user_textview.setText("Uploader :  "+rFile.getUser());
        builder.setView(inputView);
        //設定Dialog的標題
        builder.setTitle(rFile.getFilename());
        //設定Positive按鈕資料
        builder.setPositiveButton("Download", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //按下按鈕時顯示快顯
                downloadFile(rFile.getFilename());
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
        builder.create();
        builder.show();
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
                //imageView.setImageBitmap(bitmap);
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
    private void uploadFile(String pictureName){
        //if there is a file to upload
        final String PictureName = pictureName;

        if (uri != null) {

            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference riversRef = storageReference.child("images/"+pictureName+".jpg");
            final StorageReference temp = riversRef;
            riversRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            temp.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //get URL here.
                                    Log.d("URL :",uri.toString());
                                    Toast.makeText(getActivity(), "File Uploaded ", Toast.LENGTH_SHORT).show();
                                    RFile rFile = new RFile(PictureName, user.getName(), uri.toString());
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                                            .child("TEST0919").child("storage").child("file"+ Calendar.getInstance().getTime().toString());
                                    databaseReference.setValue(rFile);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.setMessage("Uploadeding....");
                        }
                    });

        }
        //if there is not any file
        else {
            //you can display an error toast
            Toast.makeText(getActivity(), "There isn't a file", Toast.LENGTH_SHORT).show();
        }
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
