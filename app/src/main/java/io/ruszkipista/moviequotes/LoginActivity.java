package io.ruszkipista.moviequotes;


/**
 * A login screen that offers login via email/password.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailEditText = findViewById(R.id.email_edittext);
        mPasswordEditText = findViewById(R.id.password_edittext);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void handleSignIn(View view){
        Toast.makeText(this,"Sign In", Toast.LENGTH_SHORT).show();
    }
    public void handleSignUp(View view){
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        if (email.length() <5 || !email.contains("@")) {
            mEmailEditText.setError(getString(R.string.invalid_email));
        } else if (password.length() < 5) {
            mEmailEditText.setError(getString(R.string.invalid_password));
        } else {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this,"Sign Up failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
