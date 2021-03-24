package com.tremorint.urlshortener.service;

import com.tremorint.urlshortener.domain.Url;
import com.tremorint.urlshortener.repository.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
public class UrlServiceImpl implements UrlService {

    private final String[] protocolScheme = new String[]{"http","https"};
    private UrlRepository urlRepository;

    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public String shortenUrl(String longUrl) {
        //url validator the support http,https as our scheme, we can add as much as needed of the web protocols
        final UrlValidator urlValidator = new UrlValidator(protocolScheme);
        if(!urlValidator.isValid(longUrl)){
            return "Invalid Url";
        }
        //
        final Url url = Url.shortenUrl(longUrl);
        log.info("URL id is generated : {}",url.getId());
        urlRepository.saveUrlObject(url);
        return url.getId();
    }

    @Override
    public String getFullLinkById(String id) {
        Url url = urlRepository.getUrlById(id);
        if(Objects.isNull(url)){
            return "No Such Id Found";
        }
        url = validateDateDifference(url);
        urlRepository.incrementClicks(id);
        return url.getUrl();
    }

    @Override
    public int getClicksById(String id) {
        Url url = urlRepository.getUrlById(id);
        if(Objects.isNull(url)){
            return -1;
        }
        url = validateDateDifference(url);
        return url.getClicks();
    }

    private Url validateDateDifference(Url url){
        Duration duration = Duration.between(url.getClickResetDate(),LocalDateTime.now());
        if(duration.toDays()>1){
            return urlRepository.resetClicksAndDate(url.getId());
        }
        return url;
    }


}
