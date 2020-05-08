package com.dev.quizmania;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChoosequizActivity extends AppCompatActivity {

    private Button b1,b2,b3;
    public static String quiz ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosequiz);

        b1=(Button)findViewById(R.id.btn_gk);
        b2=(Button)findViewById(R.id.btn_hq);
        b3=(Button)findViewById(R.id.btn_ic);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoosequizActivity.this,QuizRuleActivity.class));
                quiz="Gk Quiz";
                finish();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoosequizActivity.this,QuizRuleActivity.class));
                quiz="History Quiz";
                finish();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoosequizActivity.this,QuizRuleActivity.class));
                quiz="Indian Culture";
                finish();
            }
        });
    }
}
