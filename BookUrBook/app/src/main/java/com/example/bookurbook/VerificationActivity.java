package com.example.bookurbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookurbook.MailAPISource.JavaMailAPI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class VerificationActivity extends AppCompatActivity {

    private boolean resendable;
    private EditText verification;
    private Button verify;
    private TextView resend;
    private FirebaseAuth auth;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        init();
    }
    public void init()
    {
        resendable = false;
        verification = findViewById(R.id.editverification);
        verify = findViewById(R.id.verifybutton);
        resend = findViewById(R.id.resend);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        Bundle bundle = getIntent().getExtras();
        new CountDownTimer(60000, 1000)
        {
            public void onTick(long millisUntilFinished){}
            public void onFinish()
            {
                resendable = true;
            }
        } .start();
        String username = bundle.getString("username");
        String email = bundle.getString("email");
        String password = bundle.getString("password");
        String code = bundle.getString("code");
        final String VERIFICATION_SUBJECT = " Your BookURBook verification code";
        final String VERIFICATION_MAIL = "Your BookURBook verification code is " +  code + ".";
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verification.getText().toString().equals(code))
                {
                   auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful())
                           {
                               Toast successful = Toast.makeText(VerificationActivity.this, "Account is created. You are being taken to the login screen.", Toast.LENGTH_LONG);
                               successful.show();
                               Map<String, Object> user = new HashMap<>();
                               user.put("mail", email);
                               user.put("username", username);
                               
                               new CountDownTimer(2000, 1000)
                               {
                                   public void onTick(long millisUntilFinished){}
                                   public void onFinish()
                                   {
                                       Intent pass = new Intent(VerificationActivity.this, LoginActivity.class);
                                       startActivity(pass);
                                 }
                               } .start();
                           }
                           else
                           {
                               Toast unsuccessful = Toast.makeText(VerificationActivity.this,
                                       "Something is wrong. Admins are notified. Your verification code is correct. Check your internet connection", Toast.LENGTH_LONG);
                               unsuccessful.show();
                           }
                       }
                   });
                }
                else {
                    Toast unsuccessful = Toast.makeText(VerificationActivity.this, "Verification code is not correct.", Toast.LENGTH_LONG);
                    unsuccessful.show();
                    verification.setText("");
                }
            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resendable == true)
                {
                    JavaMailAPI javaMailAPI = new JavaMailAPI(VerificationActivity.this,email,VERIFICATION_SUBJECT, VERIFICATION_MAIL);
                    javaMailAPI.execute();
                    Toast code = Toast.makeText(VerificationActivity.this, "The code has been re-sent to your email.", Toast.LENGTH_LONG);
                    code.show();
                    Intent pass = new Intent(VerificationActivity.this, VerificationActivity.class);
                    pass.putExtra("username", username);
                    pass.putExtra("email", email);
                    pass.putExtra("password", password);
                    pass.putExtra("code", bundle.getString("code"));
                    startActivity(pass);
                }
                else
                {
                    Toast wait = Toast.makeText(VerificationActivity.this, "Please wait 60 seconds to resend.", Toast.LENGTH_LONG);
                    wait.show();
                }
            }
        });
    }
}