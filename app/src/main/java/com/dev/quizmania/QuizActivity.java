package com.dev.quizmania;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    public int Totaltime = 0;

    /* renamed from: b1 */
    private Button b1,b2,b3,b4;
    private String answer;
    private DatabaseReference demoRef;


    Handler handler;
    int f45i = 0;
    int number = 1;
    ProgressDialog progressDialog;
    Boolean removehandler = Boolean.valueOf(false);
    private DatabaseReference rootRef;
    Boolean runhandler = Boolean.valueOf(true);
    Runnable runnable;
    public TextView f46t1;
    public TextView f47t2,t3,t4;
    private ImageView img;
    public FirebaseAuth firebaseAuth;
    int f48x = 0;
    int x=0;
    private int y=0;
    public static int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        this.rootRef = FirebaseDatabase.getInstance().getReference();
        this.f46t1 = (TextView) findViewById(R.id.tv_timer);
        this.f47t2 = (TextView) findViewById(R.id.tv_question);
        this.t3 = (TextView) findViewById(R.id.tv_questionno);
        t4=(TextView)findViewById(R.id.tv_score);
        this.b1 = (Button) findViewById(R.id.btn_option1);
        b2 = (Button) findViewById(R.id.btn_option2);
        b3 = (Button) findViewById(R.id.btn_option3);
        b4 = (Button) findViewById(R.id.btn_option4);

        img=(ImageView)findViewById(R.id.img_people);
        this.firebaseAuth = FirebaseAuth.getInstance();
        final String userid = this.firebaseAuth.getCurrentUser().getUid();

        this.demoRef = this.rootRef.child("Quiz").child("Gk Quiz").child("Slot1");


        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setTitle("Question");
        this.progressDialog.setMessage("Loading");
        this.progressDialog.setCancelable(false);
        this.handler = new Handler();




        retrivequestion();
        this.b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               if(answer.equals("A")){
                   Toast.makeText(QuizActivity.this, "Correct solution", Toast.LENGTH_SHORT).show();
                   b1.setBackgroundResource(R.color.green);
                   score++;
                   t4.setText("Score:- "+score+"/10");
               }
               else{
                   Toast.makeText(QuizActivity.this, "Sorry!! Incorrect solution", Toast.LENGTH_SHORT).show();
                   b1.setBackgroundResource(R.color.red);
                   if(answer.equals("B")){
                       b2.setBackgroundResource(R.color.green);
                   }
                   if(answer.equals("C")){
                       b3.setBackgroundResource(R.color.green);
                   }
                   if(answer.equals("D")){
                       b4.setBackgroundResource(R.color.green);
                   }
               }
               y=1;
                b1.setEnabled(false);
                b2.setEnabled(false);
                b3.setEnabled(false);
                b4.setEnabled(false);
                new CountDownTimer(2000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    @Override
                    public void onFinish() {
                        time();
                        retrivequestion();
                    }
                }.start();
            }
        });
        this.b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(answer.equals("B")){
                    Toast.makeText(QuizActivity.this, "Correct solution", Toast.LENGTH_SHORT).show();
                    b2.setBackgroundResource(R.color.green);
                    score++;
                    t4.setText("Score:- "+score+"/10");
                }
                else{
                    Toast.makeText(QuizActivity.this, "Sorry!! Incorrect solution", Toast.LENGTH_SHORT).show();
                    b2.setBackgroundResource(R.color.red);
                    if(answer.equals("A")){
                        b1.setBackgroundResource(R.color.green);
                    }
                    if(answer.equals("C")){
                        b3.setBackgroundResource(R.color.green);
                    }
                    if(answer.equals("D")){
                        b4.setBackgroundResource(R.color.green);
                    }
                }
                y=1;
                b1.setEnabled(false);
                b2.setEnabled(false);
                b3.setEnabled(false);
                b4.setEnabled(false);
                new CountDownTimer(2000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    @Override
                    public void onFinish() {
                        time();
                        retrivequestion();
                    }
                }.start();
            }
        });
        this.b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(answer.equals("C")){
                    Toast.makeText(QuizActivity.this, "Correct solution", Toast.LENGTH_SHORT).show();
                    b3.setBackgroundResource(R.color.green);
                    score++;
                    t4.setText("Score:- "+score+"/10");
                }
                else{
                    Toast.makeText(QuizActivity.this, "Sorry!! Incorrect solution", Toast.LENGTH_SHORT).show();
                    b3.setBackgroundResource(R.color.red);
                    if(answer.equals("A")){
                        b1.setBackgroundResource(R.color.green);
                    }
                    if(answer.equals("B")){
                        b2.setBackgroundResource(R.color.green);
                    }
                    if(answer.equals("D")){
                        b4.setBackgroundResource(R.color.green);
                    }
                }
                y=1;
                b1.setEnabled(false);
                b2.setEnabled(false);
                b3.setEnabled(false);
                b4.setEnabled(false);
                new CountDownTimer(2000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    @Override
                    public void onFinish() {
                        time();
                        retrivequestion();
                    }
                }.start();
            }
        });
        this.b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(answer.equals("D")){
                    Toast.makeText(QuizActivity.this, "Correct solution", Toast.LENGTH_SHORT).show();
                    b4.setBackgroundResource(R.color.green);
                    score++;
                    t4.setText("Score:- "+score+"/10");
                }
                else{
                    Toast.makeText(QuizActivity.this, "Sorry!! Incorrect solution", Toast.LENGTH_SHORT).show();
                    b4.setBackgroundResource(R.color.red);
                    if(answer.equals("A")){
                        b1.setBackgroundResource(R.color.green);
                    }
                    if(answer.equals("B")){
                        b2.setBackgroundResource(R.color.green);
                    }
                    if(answer.equals("C")){
                        b3.setBackgroundResource(R.color.green);
                    }
                }
                y=1;
                b1.setEnabled(false);
                b2.setEnabled(false);
                b3.setEnabled(false);
                b4.setEnabled(false);
                new CountDownTimer(2000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    @Override
                    public void onFinish() {
                        time();
                        retrivequestion();
                    }
                }.start();

            }
        });
    }

    public void retrivequestion() {
        this.progressDialog.show();
        y=0;
        img.setVisibility(View.GONE);
        t3.setText("Question- "+number);
        if (this.f48x == 1) {
            this.runhandler = Boolean.valueOf(true);
            this.f48x = 0;
        }
        if (this.number == 11) {
            progressDialog.dismiss();
            startActivity(new Intent(this, QuizResultActivity.class));
            x=1;
            finish();
        }
        DatabaseReference databaseReference = this.demoRef;
        StringBuilder sb = new StringBuilder();
        String str = "question";
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        b4.setEnabled(true);
        b1.setBackgroundResource(R.color.dark_blue);
        b2.setBackgroundResource(R.color.dark_blue);
        b3.setBackgroundResource(R.color.dark_blue);
        b4.setBackgroundResource(R.color.dark_blue);
        sb.append(str);
        int n=random(number);
        sb.append(n);
        databaseReference.child(sb.toString()).addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String value = (String) dataSnapshot.child("question").getValue(String.class);
                final String option1 = (String) dataSnapshot.child("option1").getValue(String.class);
                final String option2 = (String) dataSnapshot.child("option2").getValue(String.class);
                final String option3 = (String) dataSnapshot.child("option3").getValue(String.class);
                final String option4 = (String) dataSnapshot.child("option4").getValue(String.class);
                answer = (String) dataSnapshot.child("answer").getValue(String.class);
                if (value != null) {
                    DatabaseReference access$200 = demoRef;
                    StringBuilder sb = new StringBuilder();
                    sb.append("question");
                    sb.append(number);

                    access$200.child(sb.toString()).child("image").addValueEventListener(new ValueEventListener() {
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            final String value3 = (String) dataSnapshot.getValue(String.class);
                            DatabaseReference access$200 = demoRef;
                            StringBuilder sb = new StringBuilder();
                            sb.append("question");
                            sb.append(number);
                            access$200.child(sb.toString()).child("time").addValueEventListener(new ValueEventListener() {
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String value1 = (String) dataSnapshot.getValue(String.class);
                                    Totaltime = Integer.parseInt(value1);
                                    if (value3 != null) {
                                        img.setVisibility(View.VISIBLE);
                                        Picasso.with(QuizActivity.this).load(value3).fit().centerCrop().placeholder((int) R.drawable.googleg_disabled_color_18).into(QuizActivity.this.img, new Callback() {
                                            public void onSuccess() {
                                                f47t2.setText(value);
                                                b1.setText("A- "+option1);
                                                b2.setText("B- "+option2);
                                                b3.setText("C- "+option3);
                                                b4.setText("D- "+option4);

                                                number++;
                                                time();
                                                progressDialog.dismiss();
                                            }

                                            public void onError() {
                                                progressDialog.dismiss();
                                            }
                                        });
                                    }
                                    else{
                                        f47t2.setText(value);
                                        b1.setText("A- "+option1);
                                        b2.setText("B- "+option2);
                                        b3.setText("C- "+option3);
                                        b4.setText("D- "+option4);
                                        number++;
                                        time();
                                        progressDialog.dismiss();
                                    }

                                }

                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(QuizActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(QuizActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    });



                    return;
                }
                progressDialog.dismiss();
            }

            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(QuizActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* access modifiers changed from: private */
    public void time() {

        Boolean valueOf = Boolean.valueOf(false);
        this.f45i = 0;
        if (this.removehandler.booleanValue()) {
            this.handler.removeCallbacks(this.runnable);
            this.f48x = 1;
            this.removehandler = valueOf;
        }
        this.runnable = new Runnable() {
            public void run() {

                    if (f45i <= Totaltime) {
                        TextView access$500 = f46t1;
                        StringBuilder sb = new StringBuilder();
                        sb.append("");
                        sb.append(f45i);
                        sb.append(" / ");
                        sb.append(Totaltime);
                        access$500.setText(sb.toString());
                        f45i++;
                    } else {
                        QuizActivity mainActivity = QuizActivity.this;
                        mainActivity.f45i = 0;
                        if (x == 0  && y==0) {
                            mainActivity.retrivequestion();
                        }
                    }
                    handler.postDelayed(QuizActivity.this.runnable, 1000);

            }
        };
        if (this.runhandler.booleanValue()) {
            this.runnable.run();
            this.runhandler = valueOf;
        }
    }

    public int random(int n){
        Random random = new Random();
        if(n==1){
            return random.ints(1,(3)).findFirst().getAsInt();
        }
        if(n==2){
            return random.ints(3,(5)).findFirst().getAsInt();
        }
        if(n==3){
            return random.ints(5,(7)).findFirst().getAsInt();
        }
        if(n==4){
            return random.ints(7,(9)).findFirst().getAsInt();
        }
        if(n==5){
            return random.ints(9,(11)).findFirst().getAsInt();
        }
        if(n==6){
            return random.ints(11,(13)).findFirst().getAsInt();
        }
        if(n==7){
            return random.ints(13,(15)).findFirst().getAsInt();
        }
        if(n==8){
            return random.ints(15,(17)).findFirst().getAsInt();
        }
        if(n==9){
            return random.ints(17,(19)).findFirst().getAsInt();
        }
        if(n==10){
            return random.ints(19,(21)).findFirst().getAsInt();
        }
        return 0;
    }

    public void onBackPressed() {
        // exit();
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
        builder.setMessage("Are you Sure you Want to leave the Quiz");
        builder.setCancelable(true);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                x=1;
                finish();
                //Toast.makeText(MainActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        x=1;
    }

}
