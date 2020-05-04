package com.dev.quizmania;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizResultActivity extends AppCompatActivity {

    private TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        t1=findViewById(R.id.answer_4);
        t2=findViewById(R.id.tv_review);

        t1.setText(QuizActivity.score+"/10");

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
