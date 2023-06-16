package com.ecommerce_app.freshF;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ecommerce_app.freshF.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_Activity extends AppCompatActivity {

    Button login;
    EditText email,password;
    TextView signUp;
    FirebaseAuth auth;
    //FirebaseDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        login = findViewById(R.id.btn_login);
        email = findViewById(R.id.edit_email_login);
        password = findViewById(R.id.edit_password_login);
        signUp = findViewById(R.id.textView_sign_up);
        auth = FirebaseAuth.getInstance();
        //database = FirebaseDatabase.getInstance();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_Activity.this,Sign_Up_Activity.class));

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser(){

        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();


        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this,"Email is empty",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this,"Password is empty",Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login_Activity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                   /* Bundle bundle = new Bundle();
                    String id = task.getResult().getUser().getUid();
                    DatabaseReference databaseRef = database.getReference().child("Users").child(id);
                    databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Get the user's information as a User object
                            UserModel user = dataSnapshot.getValue(UserModel.class);
                            bundle.putString("userEmail", userEmail);
                            bundle.putString("username", user.getFirstname() + user.getLastname());

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle any errors that occur
                        }
                    });*/
                    startActivity(new Intent(Login_Activity.this,Main_Activity.class));
                    finish();
                } else{
                    Toast.makeText(Login_Activity.this,"Email or Password is incorrect",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
