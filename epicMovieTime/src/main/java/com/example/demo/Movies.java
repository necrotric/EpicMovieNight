package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeId;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movies {

    @Id
    private String id;
    @JsonProperty("Title")
    private String title = "";
    @JsonProperty("Year")
    private String year = "";
    @JsonProperty("Released")
    private String released = "";
    @JsonProperty("Runtime")
    private String runtime = "";
    @JsonProperty("Genre")
    private String genre = "";
    @JsonProperty("Plot")
    private String shortplot = "";

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

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getShortplot() {
        return shortplot;
    }

    public void setShortplot(String shortplot) {
        this.shortplot = shortplot;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "Title: '" + title + '\'' +
                ", Year: " + year + '\'' +
                ", Released: " + released + '\'' +
                ", Runtime: " + runtime + '\'' +
                ", Genre: " + genre + '\'' +
                ", Plot: " + shortplot + '\'' +
                '}';
    }
}
