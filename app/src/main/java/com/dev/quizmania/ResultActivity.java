package com.dev.quizmania;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResultActivity extends AppCompatActivity {

    private DatabaseReference rootRef,demoRef;
    public FirebaseAuth firebaseAuth;
    private TextView t1,t2,t3,t4,t5;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        t1=findViewById(R.id.answer_1);
        t2=findViewById(R.id.answer_2);
        t3=findViewById(R.id.answer_3);
        t4=findViewById(R.id.answer_4);
        t5=findViewById(R.id.tv_review);
        this.rootRef = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setTitle("Retrieving Answers");
        this.progressDialog.setMessage("Loading");
        this.progressDialog.show();

        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.demoRef = rootRef.child("Answers").child(this.firebaseAuth.getCurrentUser().getUid());
        demoRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                String answer1 = (String) dataSnapshot.child("Answer1").getValue(String.class);
                String answer2 = (String) dataSnapshot.child("Answer2").getValue(String.class);
                String answer3 = (String) dataSnapshot.child("Answer3").getValue(String.class);
                String answer4 = (String) dataSnapshot.child("Answer4").getValue(String.class);
                if(answer1!=null){
                    t1.setText("Answer 1. "+answer1);
                }
                if(answer1==null || answer1.equals("")){
                    t1.setText("Answer 1. Not Answered");
                }
                if(answer2!=null ){
                    t2.setText("Answer 2. "+answer2);
                }
                if(answer2==null || answer2.equals("")){
                    t2.setText("Answer 2. Not Answered");
                }
                if(answer3!=null){
                    t3.setText("Answer 3. "+answer3);
                }
                if(answer3==null || answer3.equals("")){
                    t3.setText("Answer 3. Not Answered");
                }
                if(answer4!=null){
                    t4.setText("Answer 4. "+answer4);
                }
                if(answer4==null || answer4.equals("")){
                    t4.setText("Answer 4. Not Answered");
                }
                progressDialog.dismiss();

            }

            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ResultActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
