package com.spbstu.SneakerHunter.services.AliApiService;


import com.spbstu.SneakerHunter.models.*;
import com.spbstu.SneakerHunter.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

//@Service
@EnableScheduling
public class AliApiDataLoader {
    private ShopRepo shopRepo;
    private PictureRepo pictureRepo;
    private GoodsRepo goodsRepo;
    public static final String aliTitle = "Ali Express";
    public static final String aliUrl = "en-aliexpress.com";

    public AliApiDataLoader() {
    }

    @Autowired
    public AliApiDataLoader(ShopRepo shopRepo, PictureRepo pictureRepo, GoodsRepo goodsRepo) {
        this.shopRepo = shopRepo;
        this.pictureRepo = pictureRepo;
        this.goodsRepo = goodsRepo;
    }

    public GoodsRepo getGoodsRepo() {
        return goodsRepo;
    }

    public void setGoodsRepo(GoodsRepo goodsRepo) {
        this.goodsRepo = goodsRepo;
    }

    public ShopRepo getCategoryRepo() {
        return shopRepo;
    }

    public void setCategoryRepo(ShopRepo shopRepo) {
        this.shopRepo = shopRepo;
    }

    public PictureRepo getPictureRepo() {
        return pictureRepo;
    }

    public void setPictureRepo(PictureRepo pictureRepo) {
        this.pictureRepo = pictureRepo;
    }

    public HttpEntity<String> getRapidApiHttpHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-rapidapi-key", "90d179237emsha25a63905b9d21bp12e8a6jsnf87f038e1521");
        headers.add("x-rapidapi-host", "ali-express1.p.rapidapi.com");
        return new HttpEntity<>("body", headers);
    }

    public void getSneakers(Integer offset){
        RestTemplate template = new RestTemplate();
        ResponseEntity<Product> response = template.exchange(
                "https://ali-express1.p.rapidapi.com/productsByCategory/200005276?from=" + offset + "&country=US"
                , HttpMethod.GET, getRapidApiHttpHeader(), Product.class);

        Product sneakers = response.getBody();

        if (sneakers != null) {
            for(Item item: sneakers.getData().getItems()){
                List<GoodsModel> goods = fromSneakerToGoods(item);
                for (GoodsModel good : goods) {
                    if ((good.getUri().length() < 255) && (!good.getGender().equals("not specified")))
                        goodsRepo.save(good);
                }
            }

        }

    }

    @Scheduled(fixedDelay = 1_000_000)
    public void loadData() {

        for (int offset = 0; offset <= 100; offset += 20)
            getSneakers(offset);
    }

    public List<GoodsModel> fromSneakerToGoods(Item sneaker){
        List<GoodsModel> goodsList = new LinkedList<>();
        List<SizeModel> sizes = new LinkedList<>();

        String gender = sneaker.getProductElements().getTitle().getTitle();
        String lowerCaseGender = gender.toLowerCase(Locale.ROOT);
        if(lowerCaseGender.contains("woman") || lowerCaseGender.contains("female") || lowerCaseGender.contains("women")){
            gender = "Women";
        }
        else if (lowerCaseGender.contains("men") || lowerCaseGender.contains("man") || lowerCaseGender.contains("male")){
            gender = "Men";
        }
        else
            gender = "not specified";

        String imageUrl = "";
        if (!sneaker.getProductElements().getImage().getImgUrl().isEmpty())
            imageUrl = sneaker.getProductElements().getImage().getImgUrl();
        if(imageUrl.contains("http:"))
            imageUrl = imageUrl.substring(imageUrl.indexOf("//"));

        PictureModel picture = pictureRepo.findByUrl(imageUrl);
        if (picture == null) {
            pictureRepo.save(new PictureModel(imageUrl));
            picture = pictureRepo.findByUrl(imageUrl);
        }

        ShopModel shop = shopRepo.findByTitle(aliTitle);
        if (shop == null) {
            shopRepo.save(new ShopModel(aliTitle, aliUrl));
            shop = shopRepo.findByTitle(aliTitle);
        }

        String price = sneaker.getProductElements().getPrice().getSellPrice().getFormatedAmount();
        price = price.substring(price.indexOf(" "));

        GoodsModel goods = new GoodsModel(shop, sneaker.getProductElements().getTitle().getTitle(), null, null,
                picture, price, gender, "https:" + sneaker.getAction(), null);
        goodsList.add(goods);
        return goodsList;
    }

}