package com.example.ihaveadream0528.finalprogarm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_page extends AppCompatActivity implements View.OnClickListener{
    private Button login_button, registered_button;
    private EditText email_edittext, password_edittext;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Window window = this.getWindow();
        // Followed by google doc.
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        // For not opaque(transparent) color.
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(Login_page.this, MainActivity.class));
        }
        setContentView(R.layout.login_page);

        // Obtain the FirebaseAnalytics instance.

        login_button = (Button)findViewById(R.id.login_button);
        registered_button = (Button)findViewById(R.id.registered_button);
        email_edittext = (EditText)findViewById(R.id.email_edittext);
        password_edittext = (EditText)findViewById(R.id.password_edittext);
        progressDialog = new ProgressDialog(this);
        login_button.setOnClickListener(this);
        registered_button.setOnClickListener(this);
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
        String email = email_edittext.getText().toString().trim();
        String password = password_edittext.getText().toString().trim();
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
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(Login_page.this, MainActivity.class));
                        }
                    }
                });
    }
    private void Registered(){
        finish();
        startActivity(new Intent(Login_page.this, Register_page.class));
    }

}
