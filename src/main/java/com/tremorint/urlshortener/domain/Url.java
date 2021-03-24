package com.tremorint.urlshortener.domain;
import com.google.common.hash.Hashing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Url {
    private String id;
    private String url;
    private LocalDateTime created;
    private LocalDateTime clickResetDate;
    private int clicks;

    //this method could be implemented in an implementation of an interface (HashString) that has Hash Method declaration
    public static Url shortenUrl(final String url){
        final String id = Hashing.murmur3_32().hashString(url,StandardCharsets.UTF_8).toString();
        return new Url(id, url, LocalDateTime.now(),LocalDateTime.now().plusDays(1),0);
    }

    public void incrementClicks(){
        this.clicks++;
    }
    public void incrementClickResetDate() {this.clickResetDate = this.clickResetDate.plusDays(1);}
}
