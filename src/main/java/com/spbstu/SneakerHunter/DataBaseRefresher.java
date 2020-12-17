package com.spbstu.SneakerHunter;

import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
@EnableScheduling
public class DataBaseRefresher {
    public DataBaseRefresher() {
    }

    public HttpEntity<String> getRapidApiHttpHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-rapidapi-key", "5da9217069msh6e8e27c8df6dfb8p1e5eafjsnf530c07e1568");
        headers.add("x-rapidapi-host", "asos2.p.rapidapi.com");
        return new HttpEntity<>("body", headers);
    }

    public List<Integer> getSneakers(){
        final Integer limit = 10;
        List<Integer> idList = new LinkedList<Integer>();

        RestTemplate template = new RestTemplate();
        ResponseEntity<Sneakers> response = template.exchange(
                "https://asos2.p.rapidapi.com/products/v2/list?offset=0&categoryId=4209&limit=" + limit
                        + "&store=US&country=US&currency=USD&sort=freshness&lang=en-US&sizeSchema=US"
                , HttpMethod.GET, getRapidApiHttpHeader(), Sneakers.class);

        Sneakers sneakers = response.getBody();

        if (sneakers != null) {
            for(Product sneaker: sneakers.getProducts()){
                idList.add(sneaker.getId());
            }
        }
        return idList;
    }

    public void getSneakerById(){
        RestTemplate template = new RestTemplate();
        List<Integer> sneakersId = getSneakers();

        for(Integer id: sneakersId){
            ResponseEntity<Sneaker> response = template.exchange(
                    "https://asos2.p.rapidapi.com/products/v3/detail?id="+ id +
                            "&store=US&sizeSchema=US&lang=en-US&currency=USD",
                    HttpMethod.GET, getRapidApiHttpHeader(), Sneaker.class);

            Sneaker sneaker = response.getBody();
            if (sneaker != null) {
                System.out.println("Sneaker: " + sneaker.getVariants().get(1).getName() + sneaker.getGender() + " - "
                        + sneaker.getBaseUrl() + " Size: " + sneaker.getVariants().get(1).getBrandSize());
            }

        }
    }

    @Scheduled(fixedDelay = 100_000)
    public void loadData() {
        getSneakerById();


    }
}