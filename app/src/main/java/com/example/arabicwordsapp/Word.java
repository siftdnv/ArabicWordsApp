package com.example.arabicwordsapp;

public class Word {
    private int id;
    private String arabic;
    private String translation;
    private String transcription;
    private String grammar;

    // Конструктор (без id, для новых слов)
    public Word(String arabic, String translation, String transcription, String grammar) {
        this.arabic = arabic;
        this.translation = translation;
        this.transcription = transcription;
        this.grammar = grammar;
    }

    // Конструктор (с id, для слов из БД)
    public Word(int id, String arabic, String translation, String transcription, String grammar) {
        this.id = id;
        this.arabic = arabic;
        this.translation = translation;
        this.transcription = transcription;
        this.grammar = grammar;
    }

    // Геттеры
    public int getId() { return id; }
    public String getArabic() { return arabic; }
    public String getTranslation() { return translation; }
    public String getTranscription() { return transcription; }
    public String getGrammar() { return grammar; }

    // Сеттеры
    public void setId(int id) { this.id = id; }
    public void setArabic(String arabic) { this.arabic = arabic; }
    public void setTranslation(String translation) { this.translation = translation; }
    public void setTranscription(String transcription) { this.transcription = transcription; }
    public void setGrammar(String grammar) { this.grammar = grammar; }
}