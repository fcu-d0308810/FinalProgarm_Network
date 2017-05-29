package com.example.ihaveadream0528.finalprogarm;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.media.MediaRecorder.VideoSource.CAMERA;


public class Upload_fragment extends Fragment implements View.OnClickListener{
    private View rootView;
    private Button select_button, upload_button;
    private ImageButton camera_button;
    private StorageReference storageReference;
    private ImageView showPic_view;
    private String FileName;
    private Uri uri;
    private File file;
    private EditText fileName_edittext;
    private static final int GALLERY_INTENT = 2;
    private FirebaseUser User;
    public Upload_fragment(FirebaseUser user){
        this.User = user;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        rootView = inflater.inflate(R.layout.upload_fragment, container, false);

        storageReference = FirebaseStorage.getInstance().getReference();

        camera_button = (ImageButton) rootView.findViewById(R.id.shotphoto_button);
        upload_button = (Button) rootView.findViewById(R.id.upload_button);
        select_button = (Button) rootView.findViewById(R.id.pick_button);
        showPic_view = (ImageView) rootView.findViewById(R.id.pick_img);
        fileName_edittext = (EditText) rootView.findViewById(R.id.filename_editText);
        select_button.setOnClickListener(this);
        upload_button.setOnClickListener(this);
        camera_button.setOnClickListener(this);
        return rootView;
    }
    @Override
    public void onClick(View view) {
        int command = view.getId();
        if(command == R.id.pick_button){
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
        else if(command == R.id.shotphoto_button){

            if(ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(
                        new String[]{Manifest.permission.CAMERA},
                        2000);
            }
            else {
                takePhotoFromCamera();
            }
        }
        else if(command == R.id.upload_button){
            FileName = fileName_edittext.getText().toString() + ".jpg";
            if(!FileName.equals("")){
                uploadFile();
            }
            else{
                Toast.makeText(getActivity(),"try upload again",Toast.LENGTH_SHORT).show();
            }
            FileName = "";
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
                showPic_view.setImageBitmap(bitmapImage);
            }
            else if(requestCode == CAMERA){
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                uri = data.getData();
                showPic_view.setImageBitmap(imageBitmap);

                Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }
    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // put Uri as extra in intent object
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA);
        }
    }
    private void uploadFile(){
        //if there is a file to upload
        if (uri != null) {
            RFile rFile = new RFile(FileName, User.getEmail());
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("storage").child("file"+rFile.getTime());
            databaseReference.setValue(rFile);
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference riversRef = storageReference.child("images/"+FileName+".jpg");
            riversRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getActivity(), "File Uploaded ", Toast.LENGTH_SHORT).show();
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
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
            Toast.makeText(getActivity(), "There isn't a file", Toast.LENGTH_SHORT).show();
        }
    }
}
