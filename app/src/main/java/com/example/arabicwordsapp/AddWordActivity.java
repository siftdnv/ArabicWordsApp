package com.example.arabicwordsapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddWordActivity extends AppCompatActivity {

    private EditText editArabic, editTranslation, editTranscription, editGrammar;
    private Button btnSave;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        dbHelper = new DatabaseHelper(this);

        editArabic = findViewById(R.id.editArabic);
        editTranslation = findViewById(R.id.editTranslation);
        editTranscription = findViewById(R.id.editTranscription);
        editGrammar = findViewById(R.id.editGrammar);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> saveWord());
    }

    private void saveWord() {
        String arabic = editArabic.getText().toString().trim();
        String translation = editTranslation.getText().toString().trim();
        String transcription = editTranscription.getText().toString().trim();
        String grammar = editGrammar.getText().toString().trim();

        if (TextUtils.isEmpty(arabic)) {
            editArabic.setError("Введите арабское слово");
            return;
        }

        if (TextUtils.isEmpty(translation)) {
            editTranslation.setError("Введите перевод");
            return;
        }

        Word word = new Word(arabic, translation, transcription, grammar);
        boolean success = dbHelper.addWord(word);

        if (success) {
            Toast.makeText(this, "Слово добавлено", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Ошибка при добавлении", Toast.LENGTH_SHORT).show();
        }
    }
}