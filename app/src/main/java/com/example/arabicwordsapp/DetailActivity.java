package com.example.arabicwordsapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private TextView tvArabic, tvTranslation, tvTranscription, tvGrammar;
    private Button btnEdit, btnDelete;
    private DatabaseHelper dbHelper;
    private Word currentWord;
    private int wordId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dbHelper = new DatabaseHelper(this);

        tvArabic = findViewById(R.id.tvArabic);
        tvTranslation = findViewById(R.id.tvTranslation);
        tvTranscription = findViewById(R.id.tvTranscription);
        tvGrammar = findViewById(R.id.tvGrammar);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        wordId = getIntent().getIntExtra("WORD_ID", -1);
        if (wordId != -1) {
            currentWord = dbHelper.getWordById(wordId);
            displayWord();
        }

        btnEdit.setOnClickListener(v -> showEditDialog());
        btnDelete.setOnClickListener(v -> confirmDelete());
    }

    private void displayWord() {
        if (currentWord != null) {
            tvArabic.setText(currentWord.getArabic());
            tvTranslation.setText(currentWord.getTranslation());
            tvTranscription.setText(currentWord.getTranscription());
            tvGrammar.setText(currentWord.getGrammar().isEmpty() ? "—" : currentWord.getGrammar());
        }
    }

    private void showEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Редактировать слово");

        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 40);

        EditText inputArabic = new EditText(this);
        inputArabic.setHint("Арабское слово");
        inputArabic.setText(currentWord.getArabic());
        layout.addView(inputArabic);

        EditText inputTranslation = new EditText(this);
        inputTranslation.setHint("Перевод");
        inputTranslation.setText(currentWord.getTranslation());
        layout.addView(inputTranslation);

        EditText inputTranscription = new EditText(this);
        inputTranscription.setHint("Транскрипция");
        inputTranscription.setText(currentWord.getTranscription());
        layout.addView(inputTranscription);

        EditText inputGrammar = new EditText(this);
        inputGrammar.setHint("Грамматика");
        inputGrammar.setText(currentWord.getGrammar());
        layout.addView(inputGrammar);

        builder.setView(layout);

        builder.setPositiveButton("Сохранить", (dialog, which) -> {
            String newArabic = inputArabic.getText().toString().trim();
            String newTranslation = inputTranslation.getText().toString().trim();
            String newTranscription = inputTranscription.getText().toString().trim();
            String newGrammar = inputGrammar.getText().toString().trim();

            if (newArabic.isEmpty() || newTranslation.isEmpty()) {
                Toast.makeText(this, "Арабское слово и перевод обязательны", Toast.LENGTH_SHORT).show();
                return;
            }

            currentWord.setArabic(newArabic);
            currentWord.setTranslation(newTranslation);
            currentWord.setTranscription(newTranscription);
            currentWord.setGrammar(newGrammar);

            if (dbHelper.updateWord(currentWord)) {
                displayWord();
                Toast.makeText(this, "Обновлено", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Отмена", null);
        builder.show();
    }

    private void confirmDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Удалить слово")
                .setMessage("Вы уверены?")
                .setPositiveButton("Да", (dialog, which) -> {
                    if (dbHelper.deleteWord(wordId)) {
                        Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Нет", null)
                .show();
    }
}