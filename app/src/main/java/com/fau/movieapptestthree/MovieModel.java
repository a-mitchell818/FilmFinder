package com.fau.movieapptestthree;

public class MovieModel {

    private String title, year, imdbID, posterURL;

    public String getPosterURL(){
        return posterURL;
    }

    public void setPosterURL(String posterURL){
        this.posterURL = posterURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }
}
