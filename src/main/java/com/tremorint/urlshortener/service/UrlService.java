package com.tremorint.urlshortener.service;

//interface for the possibility if we want to have other services implementation of Url Service with same functions
public interface UrlService {
    String shortenUrl(String url);
    String getFullLinkById(String id);
    int getClicksById(String id);
}
