package com.example.myapplication.Model;

public class ThaThinh {
    private int number;
    private String sentence;

    public ThaThinh() {
    }

    public ThaThinh(int number, String sentence) {
        this.number = number;
        this.sentence = sentence;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
}
