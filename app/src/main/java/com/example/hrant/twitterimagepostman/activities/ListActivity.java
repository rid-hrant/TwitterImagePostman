package com.example.hrant.twitterimagepostman.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hrant.twitterimagepostman.DBHelper;
import com.example.hrant.twitterimagepostman.R;
import com.example.hrant.twitterimagepostman.StatusAdapter;

public class ListActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        setContentView(R.layout.activity_list);
        mListView = (ListView) findViewById(R.id.list);
        StatusAdapter statusAdapter = new StatusAdapter(this, dbHelper.getAllStatus());
        mListView.setAdapter(statusAdapter);
    }
}
