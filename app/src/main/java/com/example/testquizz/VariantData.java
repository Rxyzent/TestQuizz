package com.example.testquizz;

public class VariantData {
    private String question;
    private String answer;
    private String[] variants;

    public VariantData() {
    }

    public VariantData(String question, String answer, String[] variants) {
        this.question = question;
        this.answer = answer;
        this.variants = variants;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String[] getVariants() {
        return variants;
    }

    public void setVariants(String[] variants) {
        this.variants = variants;
    }
}
