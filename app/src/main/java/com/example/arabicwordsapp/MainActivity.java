package com.example.arabicwordsapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WordAdapter wordAdapter;
    private DatabaseHelper dbHelper;
    private List<Word> wordList;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddWordActivity.class);
            startActivity(intent);
        });

        loadWords();
    }

    private void loadWords() {
        wordList = dbHelper.getAllWords();
        wordAdapter = new WordAdapter(wordList);
        recyclerView.setAdapter(wordAdapter);

        wordAdapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("WORD_ID", wordList.get(position).getId());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWords();
    }
}