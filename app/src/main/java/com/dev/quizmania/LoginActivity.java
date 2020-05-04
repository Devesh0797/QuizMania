package com.dev.quizmania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextView t1;
    private EditText et,et1;
    private Button b3,log;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et=(EditText)findViewById(R.id.et_id);
        et1=(EditText)findViewById(R.id.et_pas);
        log=(Button)findViewById(R.id.btn_log);
        auth = FirebaseAuth.getInstance();
        b3=(Button)findViewById(R.id.btn_sign);
        t1=findViewById(R.id.title_reg);

        Animation animation = AnimationUtils .loadAnimation(LoginActivity.this,R.anim.moving);
        t1.startAnimation(animation);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("LOGIN");
        progressDialog.setMessage("Logging You In");

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils .loadAnimation(LoginActivity.this,R.anim.bounce);
                b3.startAnimation(animation);
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });



        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils .loadAnimation(LoginActivity.this,R.anim.bounce);
                log.startAnimation(animation);
                if(haveNetwork()){
                    String email = et.getText().toString().trim();
                    String password = et1.getText().toString().trim();

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    if (password.length() < 6) {
                        Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    progressDialog.show();

                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (!task.isSuccessful()) {
                                        // there was an error

                                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();

                                    } else {
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                            });

                }
                else{
                    Toast.makeText(LoginActivity.this, "Check your internet connection", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private Boolean haveNetwork() {
        boolean have_WIFI = false;
        boolean have_MobileData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for (NetworkInfo info : networkInfos) {
            if (info.getTypeName().equalsIgnoreCase("WIFI"))
                if (info.isConnected())
                    have_WIFI = true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE"))
                if (info.isConnected())
                    have_MobileData = true;
        }
        return have_MobileData || have_WIFI;

    }

}
