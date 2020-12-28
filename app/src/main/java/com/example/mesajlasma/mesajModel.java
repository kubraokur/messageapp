package com.example.mesajlasma;

public class mesajModel {
    private String from,text;

    public mesajModel(){

    }

    public mesajModel(String from, String text) {
        this.from = from;
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "mesajModel{" +
                "from='" + from + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
