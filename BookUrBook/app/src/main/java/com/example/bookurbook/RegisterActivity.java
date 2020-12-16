package com.example.bookurbook;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookurbook.MailAPISource.JavaMailAPI;

import java.util.Random;

public class RegisterActivity extends AppCompatActivity {
    //properties
    private EditText txtUsername;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtRePassword;
    private Button txtRegister;
    private final String VERIFICATION_SUBJECT = " Your BookURBook verification code";
    private Random rnd = new Random();
    int number = rnd.nextInt(999999);
    private final String VERIFICATION_CODE = String.format("%06d", number);
    private final String VERIFICATION_MAIL = "Your BookURBook verification code is " + VERIFICATION_CODE + ".";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }
    public void init()
    {
        txtUsername = findViewById(R.id.editemail);
        txtEmail = findViewById(R.id.editemail2);
        txtPassword = findViewById(R.id.editpassword2);
        txtRePassword = findViewById(R.id.editrepassword);
        txtRegister = findViewById(R.id.registerbutton2);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }

    private void createNewAccount()
    {
        String username = txtUsername.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String rePassword = txtRePassword.getText().toString();

        if(TextUtils.isEmpty(username))
        {
            Toast noUsername = Toast.makeText(RegisterActivity.this,"Username field is empty", Toast.LENGTH_LONG);
            noUsername.show();
        }
        else if(TextUtils.isEmpty(email))
        {
            Toast noEmail = Toast.makeText(RegisterActivity.this,"Email field is empty", Toast.LENGTH_LONG);
            noEmail.show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast noPassword = Toast.makeText(RegisterActivity.this,"Password field is empty", Toast.LENGTH_LONG);
            noPassword.show();
        }
        else if(password.length() < 6)
        {
            Toast insufficientPasswordLength = Toast.makeText(RegisterActivity.this,"Password length cannot be less than 6!", Toast.LENGTH_LONG);
            insufficientPasswordLength.show();
        }
        else if(TextUtils.isEmpty(rePassword))
        {
            Toast noRePassword = Toast.makeText(RegisterActivity.this,"Retype Password field is empty", Toast.LENGTH_LONG);
            noRePassword.show();
        }
        else if(!password.equals(rePassword))
        {
            Toast passwordMismatch = Toast.makeText(RegisterActivity.this,"Passwords are different", Toast.LENGTH_LONG);
            passwordMismatch.show();
            txtPassword.setText("");
            txtRePassword.setText("");
        }
        else if(!email.contains("@"))
        {
            Toast notEmail = Toast.makeText(RegisterActivity.this, "Wrong email",  Toast.LENGTH_LONG);
            notEmail.show();
        }
        else if(!email.substring(email.indexOf("@"), email.length()).contains(".edu.tr"))
        {
            Toast notEdu = Toast.makeText(RegisterActivity.this, "This is not a edu.tr mail",  Toast.LENGTH_LONG);
            notEdu.show();
        }
        else
        {
            Toast.makeText(RegisterActivity.this, "Your verification code has been sent to your email.",  Toast.LENGTH_LONG);
            JavaMailAPI javaMailAPI = new JavaMailAPI(RegisterActivity.this,email,VERIFICATION_SUBJECT, VERIFICATION_MAIL);
            javaMailAPI.execute();
            Intent pass = new Intent(RegisterActivity.this, VerificationActivity.class);
            pass.putExtra("username", username);
            pass.putExtra("email", email);
            pass.putExtra("password", password);
            pass.putExtra("code", VERIFICATION_CODE);
            startActivity(pass);
        }
    }

}

