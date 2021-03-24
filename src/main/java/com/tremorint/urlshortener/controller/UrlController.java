package com.tremorint.urlshortener.controller;

import com.tremorint.urlshortener.exception.UrlShortenerError;
import com.tremorint.urlshortener.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
@RequestMapping(UrlController.BASE_URL)
public class UrlController {
    static final String BASE_URL = "/rest/url";
    private static final String NO_SUCH_ID= "No Such Id Found";
    private static final String OOPS= "https://www.tremorinternational.com/abc";
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    //shorten url and return as id in header
    @PostMapping
    public ResponseEntity shortenUrl(@RequestBody final String url){
        String id = urlService.shortenUrl(url);
        if(id.equals("Invalid Url")){
            return ResponseEntity.badRequest().body(new UrlShortenerError(id));
        }
        return ResponseEntity.ok(id);
    }

    //get the full url if found by id, for client use
    @GetMapping(value = "/{id}")
    public ResponseEntity getUrl(@PathVariable final String id){
        String fullUrl = urlService.getFullLinkById(id);
        if(fullUrl.equals("No Such Id Found")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UrlShortenerError(NO_SUCH_ID));
        }
        log.info("Url Found = {}",fullUrl);
        return ResponseEntity.ok(fullUrl);
    }

    //for getting stats of clicks by id
    @GetMapping(value = "/{id}/stats")
    public ResponseEntity getStatsById(@PathVariable final String id){
        int clicks = urlService.getClicksById(id);
        if(clicks == -1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UrlShortenerError(NO_SUCH_ID));
        }
        log.info("number of clicks for id {} is {}",id,clicks);
        return ResponseEntity.ok(clicks);
    }

    //redirect to the page if found, OOPS if not :). if we want to immediately redirect to page
    @GetMapping(value = "/{id}/redirect")
    public RedirectView redirectToUrl(@PathVariable String id){
        String url = urlService.getFullLinkById(id);
        if(url.equals(NO_SUCH_ID)){
            return new RedirectView(OOPS);
        }
        log.info("redirected to {}",url);
        return new RedirectView(url);
    }
}
