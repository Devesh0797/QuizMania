package com.dev.quizmania;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailid,password;
    private TextView t1;
    private Button register;
    private ProgressBar pro;
    private FirebaseAuth auth;
    int a=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.emailid = (EditText) findViewById(R.id.username_reg);
        this.password = (EditText) findViewById(R.id.password_reg);
        this.t1 = (TextView) findViewById(R.id.show_register);
        this.register = (Button) findViewById(R.id.regiter_reg);
        this.pro = (ProgressBar) findViewById(R.id.loading_reg);
        this.auth = FirebaseAuth.getInstance();
        this.t1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (RegisterActivity.this.a == 0) {
                    RegisterActivity.this.password.setTransformationMethod(null);
                    RegisterActivity.this.t1.setText("Hide Password");
                    RegisterActivity.this.a = 1;
                    return;
                }
                RegisterActivity.this.password.setTransformationMethod(new PasswordTransformationMethod());
                RegisterActivity.this.t1.setText("Show Password");
                RegisterActivity.this.a = 0;
            }
        });
        this.register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (RegisterActivity.this.haveNetwork().booleanValue()) {
                    String email = RegisterActivity.this.emailid.getText().toString().trim();
                    String passwordid = RegisterActivity.this.password.getText().toString().trim();
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(RegisterActivity.this.getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(passwordid)) {
                        Toast.makeText(RegisterActivity.this.getApplicationContext(), "Enter Your Password!", Toast.LENGTH_SHORT).show();
                    } else if (passwordid.length() < 6) {
                        Toast.makeText(RegisterActivity.this.getApplicationContext(), "Password Length should be greater than 6", Toast.LENGTH_SHORT).show();
                    } else if (!RegisterActivity.this.isEmailValid(email)) {
                        Toast.makeText(RegisterActivity.this.getApplicationContext(), "Your Email Id is Invalid.", Toast.LENGTH_SHORT).show();
                    } else {
                        RegisterActivity.this.pro.setVisibility(View.VISIBLE);
                        RegisterActivity.this.auth.createUserWithEmailAndPassword(email, passwordid).addOnCompleteListener((Activity) RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            public void onComplete(Task<AuthResult> task) {
                                RegisterActivity registerActivity = RegisterActivity.this;
                                StringBuilder sb = new StringBuilder();
                                sb.append("createUserWithEmail:onComplete: ");
                                sb.append(task.isSuccessful());
                                Toast.makeText(registerActivity, sb.toString(), Toast.LENGTH_SHORT).show();
                                RegisterActivity.this.pro.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this.getApplicationContext(), "Either there is some error or Email already exist", Toast.LENGTH_SHORT).show();
                                } else {
                                    RegisterActivity.this.sendemail();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(RegisterActivity.this.getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public boolean isEmailValid(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /* access modifiers changed from: private */
    public Boolean haveNetwork() {
        NetworkInfo[] networkInfos;
        boolean have_MobileData = false;
        boolean z = false;
        boolean have_WIFI = false;
        for (NetworkInfo info : ((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE)).getAllNetworkInfo()) {
            if (info.getTypeName().equalsIgnoreCase("WIFI") && info.isConnected()) {
                have_WIFI = true;
            }
            if (info.getTypeName().equalsIgnoreCase("MOBILE") && info.isConnected()) {
                have_MobileData = true;
            }
        }
        if (have_MobileData || have_WIFI) {
            z = true;
        }
        return Boolean.valueOf(z);
    }

    /* access modifiers changed from: private */
    public void sendemail() {
        FirebaseUser user = this.auth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registeration Successsful. We have send you a verification email", Toast.LENGTH_SHORT).show();
                        RegisterActivity.this.auth.signOut();
                        RegisterActivity.this.finish();
                        return;
                    }
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
