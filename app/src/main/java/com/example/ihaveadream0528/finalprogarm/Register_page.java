package com.example.ihaveadream0528.finalprogarm;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register_page extends AppCompatActivity {
    private EditText username_edittext, password_edittext, invitationCode_edittext;
    private Button confirm_button, cancel_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        username_edittext = (EditText)findViewById(R.id.username_edittext);
        password_edittext = (EditText)findViewById(R.id.password_edittext);
        invitationCode_edittext = (EditText)findViewById(R.id.invitationCode_edittext);
        confirm_button = (Button)findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
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
}
