package io.ruszkipista.moviequotes;


/**
 * A login screen that offers login via email/password.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void handleSignIn(View view){
        Toast.makeText(this,"Sign In", Toast.LENGTH_SHORT).show();
    }
    public void handleSignUp(View view){
        Toast.makeText(this,"Sign Up", Toast.LENGTH_SHORT).show();
    }
}
