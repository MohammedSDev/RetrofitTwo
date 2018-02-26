package co.clickapps.retrofittwo.realm.model;

import io.realm.RealmObject;

/**
 * Created by clickapps on 22/12/17.
 */

public class BookModel extends RealmObject {


    private String title;
    private String author;
    private String year;
    private String library;
    private int pages;



    //constructors


    public BookModel() {
    }

    public BookModel(String title, String author, String year, String library, int pages) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.library = library;
        this.pages = pages;
    }


    //Getter


    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getYear() {
        return year;
    }

    public String getLibrary() {
        return library;
    }

    public int getPages() {
        return pages;
    }
}
