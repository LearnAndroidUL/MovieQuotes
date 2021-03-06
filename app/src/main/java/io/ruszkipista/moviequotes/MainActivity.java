package io.ruszkipista.moviequotes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final MovieQuoteAdapter movieQuoteAdapter = new MovieQuoteAdapter();
        recyclerView.setAdapter(movieQuoteAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });
    }

    private void showInputDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.dialog_moviequote,null,false);
            builder.setTitle(R.string.dialog_title_add);
            builder.setView(view);
            final EditText quoteEditTextView = view.findViewById(R.id.dialog_quote_field);
            final EditText movieEditTextView = view.findViewById(R.id.dialog_movie_field);

            builder.setNegativeButton(android.R.string.cancel,null);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String quote = quoteEditTextView.getText().toString();
                    String movie = movieEditTextView.getText().toString();

//                  create new item with captured details
                        Map<String, Object> movieQuote = new HashMap< >();
                        movieQuote.put(Constants.KEY_QUOTE,quote);
                        movieQuote.put(Constants.KEY_MOVIE,movie);
                        movieQuote.put(Constants.KEY_CREATED, new Date());
                        FirebaseFirestore.getInstance().collection(Constants.firebase_collection_mq).add(movieQuote);
                    }
            });
            builder.create().show();
    }

}
