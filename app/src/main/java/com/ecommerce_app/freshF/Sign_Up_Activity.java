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
import com.google.firebase.database.FirebaseDatabase;

public class Sign_Up_Activity extends AppCompatActivity {

    Button signUp;
    EditText firstname,lastname,email,password,rePassword;
    TextView signIn;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        signUp = findViewById(R.id.btn_sign_up);
        firstname = findViewById(R.id.edit_first_name);
        lastname = findViewById(R.id.edit_last_name);
        email = findViewById(R.id.edit_email_signUp);
        password = findViewById(R.id.edit_password_signUp);
        rePassword = findViewById(R.id.edit_re_password);
        signIn = findViewById(R.id.textView_sign_in);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Sign_Up_Activity.this,Login_Activity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });

    }
    private void createUser(){
        String userFirstName = firstname.getText().toString();
        String userLastName = lastname.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        String userRePassword = rePassword.getText().toString();

        if(TextUtils.isEmpty(userFirstName)){
            Toast.makeText(this,"First Name is empty",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userLastName)){
            Toast.makeText(this,"Last Name is empty",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this,"Email is empty",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this,"Password is empty",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userRePassword)){
            Toast.makeText(this,"Re Password is empty",Toast.LENGTH_SHORT).show();
            return;
        }

        if(userPassword.length() < 6){
            Toast.makeText(this,"Password should be at least 6 characters",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!userPassword.equals(userRePassword)){
            Toast.makeText(this,"Re Password not match",Toast.LENGTH_SHORT).show();
            return;
        }

        // create User
        auth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Sign_Up_Activity.this,"Sign Up Successful",Toast.LENGTH_SHORT).show();
                            UserModel userModel = new UserModel(userFirstName,userLastName,userEmail,userPassword);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(userModel);
                            startActivity(new Intent(Sign_Up_Activity.this,Login_Activity.class));
                            finish();
                        } else {
                            //System.out.println(task.);
                            Toast.makeText(Sign_Up_Activity.this,"User with this email already exist.",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
}
