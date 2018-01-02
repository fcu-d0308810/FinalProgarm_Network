package com.example.ihaveadream0528.finalprogarm;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register_page extends AppCompatActivity {
    private EditText username_edittext, password_edittext, invitationCode_edittext, userID_edittext;
    private Button confirm_button, cancel_button;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference class_databaseReference, user_databaseReference,userdetail_databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        username_edittext = (EditText) findViewById(R.id.username_edittext);
        password_edittext = (EditText) findViewById(R.id.password_edittext);
        invitationCode_edittext = (EditText) findViewById(R.id.invitationCode_edittext);
        confirm_button = (Button) findViewById(R.id.confirm_button);
        userID_edittext = (EditText) findViewById(R.id.userid_edittext);
        progressDialog = new ProgressDialog(this);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
        cancel_button = (Button)findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(Register_page.this, Login_page.class));
            }
        });
    }
    private void Register(){
        String userID = userID_edittext.getText().toString().trim();
        String password = password_edittext.getText().toString().trim();
        final String username = username_edittext.getText().toString().trim();
        final String invitationCode = invitationCode_edittext.getText().toString().trim();
        if(TextUtils.isEmpty(userID)){
            Toast.makeText(getApplicationContext(), "請不要空白", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "請不要空白", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(username)){
            Toast.makeText(getApplicationContext(), "請不要空白", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(invitationCode)){
            Toast.makeText(getApplicationContext(), "請不要空白", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length()<6){
            Toast.makeText(getApplicationContext(), "密碼長度不可小於6", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("註冊中.....");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(userID, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "註冊成功", Toast.LENGTH_SHORT).show();
                            setUserDatabase(invitationCode, username);
                            finish();
                            startActivity(new Intent(Register_page.this, MainActivity.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "註冊失敗" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void setUserDatabase(final String invitationCode, final String username){
        firebaseUser = firebaseAuth.getCurrentUser();
        final String UID = firebaseUser.getUid();
        final String[] CID = new String[1];
        Log.i("Register UID", UID);
        class_databaseReference = firebaseDatabase.getReference();
        class_databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CID[0] = dataSnapshot.child("invitationCode").child(invitationCode).getValue().toString();
                Log.i("Register CID", CID[0]);
                if(CID[0] !=null){
                    user_databaseReference = firebaseDatabase.getReference();
                    user_databaseReference.child("user").child(UID).child("class").setValue(CID[0]);
                    userdetail_databaseReference = firebaseDatabase.getReference().child(CID[0]).child("user").child(UID);
                    userdetail_databaseReference.child("name").setValue(username);
                    userdetail_databaseReference.child("note").child("test").setValue(new Notes("歡迎加入","謝謝您使用本服務"));
                    userdetail_databaseReference.child("permission").setValue("0");
                    userdetail_databaseReference.child("self").setValue("大家好！");
                    Log.i("set Database"," Finish");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
