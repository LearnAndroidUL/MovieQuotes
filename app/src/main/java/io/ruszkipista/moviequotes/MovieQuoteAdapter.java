package io.ruszkipista.moviequotes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import android.content.Context;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class MovieQuoteAdapter  extends RecyclerView.Adapter<MovieQuoteAdapter.MovieQuoteViewHolder>{
        private List<DocumentSnapshot> mMovieQuoteSnapshots = new ArrayList<>();
        private RecyclerView mRecyclerView;

        public MovieQuoteAdapter(){
            CollectionReference moviequoteRef = FirebaseFirestore.getInstance().collection(Constants.firebase_collection_mq);
            moviequoteRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(Constants.log_tag, "Firebase listening failed!");
                        return;
                    } else {
                        mMovieQuoteSnapshots = queryDocumentSnapshots.getDocuments();
                        notifyDataSetChanged();
                    }
                }
            });
        }


        public void removeMovieQuote(int position){
            mMovieQuoteSnapshots.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(0, mMovieQuoteSnapshots.size());
        }

        @Override
        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            mRecyclerView = recyclerView;
        }

        @NonNull
        @Override
        public MovieQuoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.moviequote_itemview,viewGroup, false);
            return new MovieQuoteViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MovieQuoteViewHolder movieQuoteViewHolder, int i) {
            DocumentSnapshot movieQuote = mMovieQuoteSnapshots.get(i);
            String quote = (String) movieQuote.get(Constants.firebase_column_quote);
            movieQuoteViewHolder.mQuoteTextView.setText(quote);
            String movie = (String) movieQuote.get(Constants.firebase_column_movie);
            movieQuoteViewHolder.mMovieTextView.setText(movie);
        }

        @Override
        public int getItemCount() {
            return mMovieQuoteSnapshots.size();
        }

        class MovieQuoteViewHolder extends RecyclerView.ViewHolder {
            private TextView mQuoteTextView;
            private TextView mMovieTextView;

            public MovieQuoteViewHolder(View itemView){
                super(itemView);
                mQuoteTextView = itemView.findViewById(R.id.itemview_quote);
                mMovieTextView = itemView.findViewById(R.id.itemview_movie);
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        removeMovieQuote(getAdapterPosition());
                        return true;
                    }
                });
            }
        }
    }
