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
import com.example.bookurbook.models.Admin;
import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class VerificationActivity extends AppCompatActivity {

    private boolean resendable;
    private EditText verification;
    private Button verify;
    private TextView resend;
    private FirebaseAuth auth;
    private FirebaseFirestore db;


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
        db = FirebaseFirestore.getInstance();
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
                   auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task)
                       {
                           if(task.isSuccessful())
                           {
                               Toast.makeText(VerificationActivity.this, "Your account has been created. You are being taken to the main menu.", Toast.LENGTH_LONG).show();

                               HashMap<String, Object> newUserData = new HashMap<>();
                               HashMap<String, Object> topUserInfo = new HashMap<>();
                               newUserData.put("username", username);
                               newUserData.put("email", email);
                               newUserData.put("banned", false);
                               newUserData.put("admin", false);
                               db.collection("users").document(auth.getCurrentUser().getUid()).set(newUserData);
                               RegularUser a = new RegularUser("", "",  null);



                               new CountDownTimer(2000, 1000)
                               {
                                   public void onTick(long millisUntilFinished){}
                                   public void onFinish()
                                   {
                                       startActivity(new Intent(VerificationActivity.this, MainMenuActivity.class));
                                   }
                               } .start();
                           }
                           else
                               Toast.makeText(getApplicationContext(), "Error occurred.", Toast.LENGTH_LONG).show();

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