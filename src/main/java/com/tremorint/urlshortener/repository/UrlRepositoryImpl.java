package com.tremorint.urlshortener.repository;

import com.tremorint.urlshortener.domain.Url;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Repository
public class UrlRepositoryImpl implements UrlRepository {

    //will be initialized inside the RedisConfig Configuration class.
    private RedisTemplate<String, Url> redisTemplate;

    @Value("${redis.ttl}")
    private long ttl;

    public UrlRepositoryImpl(RedisTemplate<String, Url> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    //save url object as K,V object, K is the id and V is the json representation of the Url Obj that lives for 24 hours
    @Override
    public Url saveUrlObject(Url url) {
        Url dbObj = redisTemplate.opsForValue().get(url.getId());
        if(Objects.isNull(dbObj)){
            redisTemplate.opsForValue().set(url.getId(), url, ttl, TimeUnit.HOURS);
            return url;
        }
        return dbObj;
    }

    @Override
    public Url getUrlById(String id) {
        return redisTemplate.opsForValue().get(id);

    }

    @Override
    public void incrementClicks(String id) {
        Url url = redisTemplate.opsForValue().get(id);
        assert url != null;
        url.incrementClicks();
        overrideObject(url);
    }

    @Override
    public Url resetClicksAndDate(String id) {
        Url url = redisTemplate.opsForValue().get(id);
        assert url != null;
        url.incrementClickResetDate();
        url.setClicks(0);
        overrideObject(url);
        return url;
    }

    private void overrideObject(Url url){
        redisTemplate.opsForValue().set(url.getId(), url, ttl, TimeUnit.HOURS);
    }
}
