package com.tremorint.urlshortener.controller;

import com.tremorint.urlshortener.domain.Url;
import com.tremorint.urlshortener.service.UrlService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*@SpringBootTest*/
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UrlController.class)
class UrlControllerTest {

    @MockBean
    private UrlService urlService;

    private Url mockUrl = new Url("abcd123","http://www.google.com/",LocalDateTime.now(),LocalDateTime.now().plusDays(1),15);


    @Test
    void shortenUrlUnitTest(){
        Mockito.when(urlService.shortenUrl(Mockito.anyString())).thenReturn(mockUrl.getId());
        UrlController controller = new UrlController(urlService);
        ResponseEntity response = controller.shortenUrl(mockUrl.getUrl());
        assertEquals(response.getBody(),mockUrl.getId());
    }

    @Test
    void getUrlByIdUnitTest() {
        Mockito.when(urlService.getFullLinkById(mockUrl.getId())).thenReturn(mockUrl.getUrl());
        UrlController controller = new UrlController(urlService);
        ResponseEntity response = controller.getUrl(mockUrl.getId());
        assertEquals(response.getBody(),mockUrl.getUrl());
    }

    @Test
    void getStatsByIdUnitTest() {
        Mockito.when(urlService.getClicksById(mockUrl.getId())).thenReturn(mockUrl.getClicks());
        UrlController controller = new UrlController(urlService);
        ResponseEntity response = controller.getStatsById(mockUrl.getId());
        assertEquals(response.getBody(),mockUrl.getClicks());
    }

    @Test
    void redirectByIdUnitTest(){
        Mockito.when(urlService.getFullLinkById(mockUrl.getId())).thenReturn(mockUrl.getUrl());
        UrlController controller = new UrlController(urlService);
        RedirectView redirectView = controller.redirectToUrl(mockUrl.getId());
        //can't assert redirectView Object, though there is no diff
        assertEquals(redirectView.toString(),new RedirectView(mockUrl.getUrl()).toString());
    }

}