package org.ecaib.countries2;

public class Mnemotecnique {
    String title;
    String text;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Mnemotecnique{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public Mnemotecnique() {
    }
}