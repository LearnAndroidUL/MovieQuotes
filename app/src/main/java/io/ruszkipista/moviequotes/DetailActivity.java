package io.ruszkipista.moviequotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class DetailActivity extends AppCompatActivity {
    private TextView mQuoteTextView;
    private TextView mMovieTextView;
    private DocumentReference mDocRef;
    private DocumentSnapshot mDocSnapshot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mQuoteTextView = findViewById(R.id.detail_quote_field);
        mMovieTextView = findViewById(R.id.detail_movie_field);

        String docId = getIntent().getStringExtra(Constants.EXTRA_DOC_ID);
        mDocRef = FirebaseFirestore.getInstance().collection(Constants.firebase_collection_mq).document(docId);
        mDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(Constants.log_tag, "Firebase detail listening failed!");
                    return;
                } else {
                    if (documentSnapshot.exists()){
                        mQuoteTextView.setText((String)documentSnapshot.get(Constants.KEY_QUOTE));
                        mMovieTextView.setText((String)documentSnapshot.get(Constants.KEY_MOVIE));
                        mDocSnapshot = documentSnapshot;
                    }
                }


            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog();
            }
        });
    }

    private void showEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_moviequote,null,false);
        builder.setTitle(R.string.dialog_title_edit);
        builder.setView(view);
        final EditText quoteEditTextView = view.findViewById(R.id.dialog_quote_field);
        final EditText movieEditTextView = view.findViewById(R.id.dialog_movie_field);
        quoteEditTextView.setText((String) mDocSnapshot.get(Constants.KEY_QUOTE));
        movieEditTextView.setText((String) mDocSnapshot.get(Constants.KEY_MOVIE));

        builder.setNegativeButton(android.R.string.cancel,null);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String quote = quoteEditTextView.getText().toString();
                String movie = movieEditTextView.getText().toString();

//              update item with captured details
                Map<String, Object> movieQuote = new HashMap< >();
                movieQuote.put(Constants.KEY_QUOTE,quote);
                movieQuote.put(Constants.KEY_MOVIE,movie);
                movieQuote.put(Constants.KEY_CREATED, new Date());
                mDocRef.update(movieQuote);
            }
        });
        builder.create().show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_delete:
                mDocRef.delete();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
