package io.ruszkipista.moviequotes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import android.content.Context;
import java.util.List;

public class MovieQuoteAdapter  extends RecyclerView.Adapter<MovieQuoteAdapter.MovieQuoteViewHolder>{
        private List<MovieQuote> mMovieQuotes = new ArrayList<>();
        private Context mContext;
        private RecyclerView mRecyclerView;

        public MovieQuote addMovieQuote(String movie, String quote){
            MovieQuote movieQuote = new MovieQuote(movie, quote);
            mMovieQuotes.add(0, movieQuote);
//      notifyDataSetChanged();  // works OK, but we want animation
            notifyItemInserted(0);
            notifyItemRangeChanged(0, mMovieQuotes.size());
            mRecyclerView.scrollToPosition(0);
            return movieQuote;
        }

        public void removeMovieQuote(int position){
            mMovieQuotes.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(0, mMovieQuotes.size());
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
            MovieQuote movieQuote = mMovieQuotes.get(i);
            movieQuoteViewHolder.mQuoteTextView.setText(movieQuote.getQuote());
            movieQuoteViewHolder.mMovieTextView.setText(movieQuote.getMovie());
        }

        @Override
        public int getItemCount() {
            return mMovieQuotes.size();
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
