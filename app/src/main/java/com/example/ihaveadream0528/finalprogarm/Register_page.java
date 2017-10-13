package com.example.ihaveadream0528.finalprogarm;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_page extends AppCompatActivity {
    private EditText username_edittext, password_edittext, invitationCode_edittext, userID_edittext
            ,checkPassword_edittext;
    private Button confirm_button, cancel_button;
    private DatabaseReference databaseReference, user_databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        username_edittext = (EditText) findViewById(R.id.username_edittext);
        password_edittext = (EditText) findViewById(R.id.password_edittext);
        invitationCode_edittext = (EditText) findViewById(R.id.invitationCode_edittext);
        checkPassword_edittext  = (EditText) findViewById(R.id.check_password_edittext);
        confirm_button = (Button) findViewById(R.id.confirm_button);
        userID_edittext = (EditText) findViewById(R.id.userid_edittext);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmpty();
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
    private void checkEmpty(){
        // check ID isn't empty
        if(!userID_edittext.getText().equals("")){
            // check password isn't empty
            if(!password_edittext.getText().equals("")){
                // check password is same with check password
                if(password_edittext.getText().equals(checkPassword_edittext.getText())){
                    // check username isn't empty
                    if(!username_edittext.getText().equals("")){
                        // check invitation code isn't empty
                        if(!invitationCode_edittext.getText().equals("")){
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("TEST0919");
                        }
                        else{

                        }
                    }
                    else{

                    }
                }
                else{

                }
            }
            else{

            }
        }
        else{

        }
    }
}
