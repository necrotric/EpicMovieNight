package com.example.demo.Classes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Search {
    @JsonProperty("Search")
    private ArrayList<SearchMovie> search = new ArrayList<>();
    @JsonProperty("totalResults")
    private String totalResults;
    @JsonProperty("Response")
    private String response;
    public Search(){

    }

    public Search(ArrayList<SearchMovie> search, String totalResults, String response) {
        this.search = search;
        this.totalResults = totalResults;
        this.response = response;
    }

    public ArrayList<SearchMovie> getSearch() {
        return search;
    }

    public void setSearch(ArrayList<SearchMovie> search) {
        this.search = search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "Search{" +
                "search=" + search +
                ", totalResults='" + totalResults + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
