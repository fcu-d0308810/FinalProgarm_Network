package com.example.ihaveadream0528.finalprogarm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Login_page extends AppCompatActivity implements View.OnClickListener{
    private Button login_button, registered_button;
    private EditText email_edittext, password_edittext;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth firebaseAuth;
    private UserDAO userDAO;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private Bundle bundle;
    private ArrayList<User> user_arrayList;
    public Login_page(){
        user_arrayList = new ArrayList<User>();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDAO = new UserDAO(getApplicationContext());
        getLocalUser();
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        // For not opaque(transparent) color.
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        //firebaseAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.login_page);

        // Obtain the FirebaseAnalytics instance.

        login_button = (Button)findViewById(R.id.login_button);
        registered_button = (Button)findViewById(R.id.registered_button);
        email_edittext = (EditText)findViewById(R.id.email_edittext);
        password_edittext = (EditText)findViewById(R.id.password_edittext);
        progressDialog = new ProgressDialog(this);
        login_button.setOnClickListener(this);
        registered_button.setOnClickListener(this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("TEST0919").child("user");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("dataSnapshot ", dataSnapshot.toString());
                User user = dataSnapshot.getValue(User.class);
                Log.d("User ", user.getId());
                user_arrayList.add(user);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void getLocalUser(){

        if(userDAO.getUser() != null){
            finish();
            startActivity(new Intent(Login_page.this, MainActivity.class));
        }
    }
    @Override
    public void onClick(View view) {

        int ID = view.getId();

        if(ID == R.id.login_button){
            userLogin();
        }
        else if(ID == R.id.registered_button){
            Registered();
        }
        else{

        }
    }
    private void userLogin(){
        final String email = email_edittext.getText().toString();
        final String password = password_edittext.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Loading please wait...");
        progressDialog.show();
        for(int i=0; i<user_arrayList.size(); i++){

            if(user_arrayList.get(i).getId().equals(email) && user_arrayList.get(i).getPassword().equals(password)){
                if(userDAO.insert(user_arrayList.get(i)) == -1){
                    Toast.makeText(getApplicationContext(), "insert Error!", Toast.LENGTH_SHORT).show();
                }
                else{
                    finish();
                    startActivity(new Intent(Login_page.this, MainActivity.class));
                }
            }
        }
        progressDialog.dismiss();


    }
    private void Registered(){
        finish();
        startActivity(new Intent(Login_page.this, Register_page.class));
    }

}
