package com.udacity.quiz.gbookapi;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {

    private static final String LOG_TAG = BookActivity.class.getSimpleName();

    private static final int LOADER_ID = 1;

    private String searchText;

    private BookAdapter mAdapter;

    private TextView mEmptyStateTextView;

    private View loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.android.gbookapi.R.layout.activity_book);

        QueryUtils queryUtils = new QueryUtils(this);

        loadingIndicator = (ProgressBar)findViewById(com.example.android.gbookapi.R.id.loading_spinner);

        //pengambilan extra dari intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            searchText = extras.getString("searchKey");
            Log.v("Getting EXTRAS", searchText);
        }

        //proses penampilan data yang ada ke listview
        ListView bookListView = (ListView) findViewById(com.example.android.gbookapi.R.id.list);

        mEmptyStateTextView = (TextView)findViewById(com.example.android.gbookapi.R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = mAdapter.getItem(position);

                Intent intent = new Intent(BookActivity.this, DetailActivity.class);
                intent.putExtra("book", book);
                startActivity(intent);
            }
        });

        if (queryUtils.CheckConnection()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText("No books found");
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG, "Calling onCreateLoader");
        return new BookLoader(this, searchText);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText("No books found");
        mAdapter.clear();
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }
}
