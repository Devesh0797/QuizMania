package com.dev.quizmania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class QuizRuleActivity extends AppCompatActivity {

    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_rule);

        b1=findViewById(R.id.btn_startqiz);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        startActivity(new Intent(QuizRuleActivity.this,QuizActivity.class));
                        finish();
                    }
                });

            }


}
