package com.example.ihaveadream0528.finalprogarm;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register_page extends AppCompatActivity implements View.OnClickListener{
    private EditText username_edittext, password_edittext, invitationCode_edittext;
    private Button confirm_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        username_edittext = (EditText)findViewById(R.id.username_edittext);
        password_edittext = (EditText)findViewById(R.id.password_edittext);
        invitationCode_edittext = (EditText)findViewById(R.id.invitationCode_edittext);
        confirm_button = (Button)findViewById(R.id.confirm_button);
    }

    @Override
    public void onClick(View view) {
        
    }
}
