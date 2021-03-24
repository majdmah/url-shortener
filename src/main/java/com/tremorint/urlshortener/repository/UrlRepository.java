package com.tremorint.urlshortener.repository;
import com.tremorint.urlshortener.domain.Url;

public interface UrlRepository {
    Url saveUrlObject(Url url);
    Url getUrlById(String id);
    void incrementClicks(String id);
    Url resetClicksAndDate(String id);
}
