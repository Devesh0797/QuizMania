package com.dev.quizmania;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    public int Totaltime = 0;

    /* renamed from: b1 */
    private Button f43b1;
    /* access modifiers changed from: private */
    private DatabaseReference demoRef;
    private DatabaseReference demoRef1;
    private EditText f44e1;
    Handler handler;
    int f45i = 0;
    int number = 1;
    ProgressDialog progressDialog;
    Boolean removehandler = Boolean.valueOf(false);
    private DatabaseReference rootRef;
    Boolean runhandler = Boolean.valueOf(true);
    Runnable runnable;
    public TextView f46t1;
    public TextView f47t2,t3;
    private ImageView img;
    public FirebaseAuth firebaseAuth;
    int f48x = 0;
    int x=0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);

        this.rootRef = FirebaseDatabase.getInstance().getReference();
        this.f46t1 = (TextView) findViewById(R.id.tv_timer);
        this.f47t2 = (TextView) findViewById(R.id.tv_question);
        this.t3 = (TextView) findViewById(R.id.tv_questionno);
        this.f43b1 = (Button) findViewById(R.id.btn_submit);
        this.f44e1 = (EditText) findViewById(R.id.et_answer);
        img=(ImageView)findViewById(R.id.img_people);
        this.firebaseAuth = FirebaseAuth.getInstance();
        final String userid = this.firebaseAuth.getCurrentUser().getUid();

        this.demoRef = this.rootRef.child("questions");
        this.demoRef1 = this.rootRef.child("Answers");

        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setTitle("Question");
        this.progressDialog.setMessage("Loading");
        this.progressDialog.setCancelable(false);
        this.handler = new Handler();




        retrivequestion();
        this.f43b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String answer=f44e1.getText().toString();
                if(answer.isEmpty()){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Are you Sure you Want to submit EMPTY Answer");
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
                            MainActivity.this.removehandler = Boolean.valueOf(true);
                            MainActivity.this.time();
                            String an = "Answer" + (number - 1);
                            MainActivity.this.demoRef1.child(userid).child(an).setValue(f44e1.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(Task<Void> task) {
                                    MainActivity.this.retrivequestion();
                                    f44e1.setText("");
                                }
                            });
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else {
                    MainActivity.this.removehandler = Boolean.valueOf(true);
                    MainActivity.this.time();
                    String an = "Answer" + (number - 1);
                    MainActivity.this.demoRef1.child(userid).child(an).setValue(f44e1.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(Task<Void> task) {
                            MainActivity.this.retrivequestion();
                            f44e1.setText("");
                        }
                    });
                }
            }
        });
    }

    public void retrivequestion() {
        this.progressDialog.show();
        img.setVisibility(View.GONE);
        t3.setText("Question- "+number);
        if (this.f48x == 1) {
            this.runhandler = Boolean.valueOf(true);
            this.f48x = 0;
        }
        if(this.number == 4){
            f43b1.setText("Save");
        }
        if (this.number == 5) {
            progressDialog.dismiss();
            startActivity(new Intent(this, ResultActivity.class));
            x=1;
            finish();
        }
        DatabaseReference databaseReference = this.demoRef;
        StringBuilder sb = new StringBuilder();
        String str = "question";
        sb.append(str);
        sb.append(this.number);
        databaseReference.child(sb.toString()).child(str).addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String value = (String) dataSnapshot.getValue(String.class);
                if (value != null) {
                    DatabaseReference access$200 = MainActivity.this.demoRef;
                    StringBuilder sb = new StringBuilder();
                    sb.append("question");
                    sb.append(MainActivity.this.number);

                    access$200.child(sb.toString()).child("image").addValueEventListener(new ValueEventListener() {
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            final String value3 = (String) dataSnapshot.getValue(String.class);
                            DatabaseReference access$200 = MainActivity.this.demoRef;
                            StringBuilder sb = new StringBuilder();
                            sb.append("question");
                            sb.append(MainActivity.this.number);
                            access$200.child(sb.toString()).child("time").addValueEventListener(new ValueEventListener() {
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String value1 = (String) dataSnapshot.getValue(String.class);
                                    MainActivity.this.Totaltime = Integer.parseInt(value1);
                                    if (value3 != null) {
                                        img.setVisibility(View.VISIBLE);
                                        Picasso.with(MainActivity.this).load(value3).fit().centerCrop().placeholder((int) R.drawable.googleg_disabled_color_18).into(MainActivity.this.img, new Callback() {
                                            public void onSuccess() {
                                                MainActivity.this.f47t2.setText(value);
                                                MainActivity.this.number++;
                                                MainActivity.this.time();
                                                MainActivity.this.progressDialog.dismiss();
                                            }

                                            public void onError() {
                                                MainActivity.this.progressDialog.dismiss();
                                            }
                                        });
                                    }
                                    else{
                                        MainActivity.this.f47t2.setText(value);
                                        MainActivity.this.number++;
                                        MainActivity.this.time();
                                        MainActivity.this.progressDialog.dismiss();
                                    }

                                }

                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(MainActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(MainActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    });



                    return;
                }
                MainActivity.this.progressDialog.dismiss();
            }

            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
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
                if (MainActivity.this.f45i <= MainActivity.this.Totaltime) {
                    TextView access$500 = MainActivity.this.f46t1;
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(MainActivity.this.f45i);
                    sb.append(" / ");
                    sb.append(MainActivity.this.Totaltime);
                    access$500.setText(sb.toString());
                    MainActivity.this.f45i++;
                } else {
                    MainActivity mainActivity = MainActivity.this;
                    mainActivity.f45i = 0;
                    f44e1.setText("");
                    if(x==0) {
                        mainActivity.retrivequestion();
                    }
                }
                MainActivity.this.handler.postDelayed(MainActivity.this.runnable, 1000);
            }
        };
        if (this.runhandler.booleanValue()) {
            this.runnable.run();
            this.runhandler = valueOf;
        }
    }

    public void onBackPressed() {
        // exit();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
