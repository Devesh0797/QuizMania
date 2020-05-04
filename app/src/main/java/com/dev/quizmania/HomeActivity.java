package com.dev.quizmania;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class HomeActivity extends AppCompatActivity {

    public FirebaseAuth firebaseAuth;
    private DatabaseReference rootRef,demoRef,databaseReference;
    private Button b1,b2,b3;
    private ImageView img;
    private TextView t1,t2;
    private ProgressDialog progressDialog;
    private ProgressBar pro;
    private String name,gender;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        b1=findViewById(R.id.startquiz);
        b3=findViewById(R.id.btn_quiz);
        b2=findViewById(R.id.btn_deleteattemp);
        t1=findViewById(R.id.tv_name);
        t2=findViewById(R.id.tv_gender);
        img=findViewById(R.id.img_profile);
        pro=findViewById(R.id.progress_pro);

        this.rootRef = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = this.firebaseAuth.getCurrentUser();


        if (user == null) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }
        else {
            this.progressDialog = new ProgressDialog(this);
            this.progressDialog.setTitle("Your Account");
            this.progressDialog.setMessage("Loading");
            this.progressDialog.show();

            this.demoRef = rootRef.child("Users").child(this.firebaseAuth.getCurrentUser().getUid());
            this.databaseReference = rootRef.child("Attempts");
            this.demoRef.child("name").addValueEventListener(new ValueEventListener() {
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = (String) dataSnapshot.getValue(String.class);
                    name = value;
                    if (value == null) {
                        HomeActivity.this.progressDialog.dismiss();
                        HomeActivity homeActivity = HomeActivity.this;
                        homeActivity.startActivity(new Intent(homeActivity, ProfileActivity.class));
                        HomeActivity.this.finish();
                        return;
                    } else {
                        demoRef.child("gender").addValueEventListener(new ValueEventListener() {
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String value1 = (String) dataSnapshot.getValue(String.class);
                                gender = value1;
                                if (value1 != null) {
                                    demoRef.child("image").addValueEventListener(new ValueEventListener() {
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            String value2 = (String) dataSnapshot.getValue(String.class);

                                            if (!value2.equals("null")) {
                                                pro.setVisibility(View.VISIBLE);
                                                Picasso.with(HomeActivity.this).load(value2).fit().centerCrop().placeholder((int) R.drawable.googleg_disabled_color_18).into(HomeActivity.this.img, new Callback() {
                                                    public void onSuccess() {
                                                        HomeActivity.this.t1.setText(name);
                                                        HomeActivity.this.t2.setText(gender);
                                                        HomeActivity.this.progressDialog.dismiss();
                                                        pro.setVisibility(View.GONE);
                                                    }

                                                    public void onError() {
                                                        HomeActivity.this.progressDialog.dismiss();
                                                    }
                                                });

                                            }

                                        }

                                        public void onCancelled(DatabaseError databaseError) {
                                            Toast.makeText(HomeActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }

                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(HomeActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    HomeActivity.this.progressDialog.dismiss();
                }

                public void onCancelled(DatabaseError databaseError) {
                    HomeActivity.this.progressDialog.dismiss();
                    Toast.makeText(HomeActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            });
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro.setVisibility(View.VISIBLE);
                databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    int a=0;
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String attempt = (String) dataSnapshot.child("attempt").getValue(String.class);

                        if(attempt==null){
                            pro.setVisibility(View.GONE);
                            a=1;
                            startActivity(new Intent(HomeActivity.this,RulesActivity.class));
                        }
                        else{
                            if(a!=1) {
                                pro.setVisibility(View.GONE);
                                Toast.makeText(HomeActivity.this, "You have already Attempted the Quiz once....", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(HomeActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.removeValue();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ChoosequizActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.client_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.signout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));

        }
        return true;
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals( state ) ) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals( state ) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals( state ) ) {
            return true;
        }
        return false;
    }
}
