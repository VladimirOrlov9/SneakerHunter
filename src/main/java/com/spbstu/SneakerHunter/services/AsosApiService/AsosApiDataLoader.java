package com.spbstu.SneakerHunter.services.AsosApiService;

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
import java.util.Objects;

@Service
//@EnableScheduling
public class AsosApiDataLoader {
    private ShopRepo shopRepo;
    private SizeRepo sizeRepo;
    private BrandRepo brandRepo;
    private PictureRepo pictureRepo;
    private GoodsRepo goodsRepo;
    private RestTemplate template;
    public static final String asosTitle = "Asos";
    public static final String asosUrl = "https://www.asos.com/us/men/";
    public static final String API_KEY_ASOS = "90d179237emsha25a63905b9d21bp12e8a6jsnf87f038e1521";
    public static final String API_HOST_ASOS = "asos2.p.rapidapi.com";

    public AsosApiDataLoader() {
    }

    @Autowired
    public AsosApiDataLoader(ShopRepo shopRepo, SizeRepo sizeRepo, BrandRepo brandRepo, PictureRepo pictureRepo, GoodsRepo goodsRepo, RestTemplate template) {
        this.shopRepo = shopRepo;
        this.sizeRepo = sizeRepo;
        this.brandRepo = brandRepo;
        this.pictureRepo = pictureRepo;
        this.goodsRepo = goodsRepo;
        this.template = template;
    }

//    public GoodsRepo getGoodsRepo() {
//        return goodsRepo;
//    }
//
//    public void setGoodsRepo(GoodsRepo goodsRepo) {
//        this.goodsRepo = goodsRepo;
//    }
//
//    public SizeRepo getSizeRepo() {
//        return sizeRepo;
//    }
//
//    public void setSizeRepo(SizeRepo sizeRepo) {
//        this.sizeRepo = sizeRepo;
//    }
//
//    public BrandRepo getBrandRepo() {
//        return brandRepo;
//    }
//
//    public void setBrandRepo(BrandRepo brandRepo) {
//        this.brandRepo = brandRepo;
//    }
//
//    public ShopRepo getCategoryRepo() {
//        return shopRepo;
//    }
//
//    public void setCategoryRepo(ShopRepo shopRepo) {
//        this.shopRepo = shopRepo;
//    }
//
//    public PictureRepo getPictureRepo() {
//        return pictureRepo;
//    }
//
//    public void setPictureRepo(PictureRepo pictureRepo) {
//        this.pictureRepo = pictureRepo;
//    }

    public HttpEntity<String> getRapidApiHttpHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-rapidapi-key", API_KEY_ASOS);
        headers.add("x-rapidapi-host", API_HOST_ASOS);

        return new HttpEntity<>("body", headers);
    }

    public Sneakers getSneakersFromRapid(Integer offset) {
        final int limit = 50;

        ResponseEntity<Sneakers> response = template.exchange(
                "https://asos2.p.rapidapi.com/products/v2/list?offset=" + offset + "&categoryId=4209&limit=" + limit
                        + "&store=US&country=US&currency=USD&sort=freshness&lang=en-US&sizeSchema=US"
                , HttpMethod.GET, getRapidApiHttpHeader(), Sneakers.class);

        return response.getBody();
    }

    public void saveSneakers(Sneakers sneakers){
        if(Objects.requireNonNull(sneakers).getProducts() == null)
            return;

        try{
            for (Product product : sneakers.getProducts()) {
                Integer id = product.getId();
                List<Sneaker> sneakerList = getSneakerById(id);
                for (Sneaker sneaker : sneakerList) {
                    List<GoodsModel> goods = fromSneakerToGoods(sneaker, product.getUrl());
                    for (GoodsModel good : goods) {
                        goodsRepo.save(good);
                    }
                }
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public List<Sneaker> getSneakerById(Integer sneakersId){
        List<Sneaker> sneakers = new LinkedList<>();

        ResponseEntity<Sneaker> response = template.exchange(
                "https://asos2.p.rapidapi.com/products/v3/detail?id="+ sneakersId +
                        "&store=US&sizeSchema=US&lang=en-US&currency=USD",
                HttpMethod.GET, getRapidApiHttpHeader(), Sneaker.class);

        Sneaker sneaker = response.getBody();
        sneakers.add(sneaker);

        return sneakers;
    }

    //@Scheduled(fixedDelay = 1_000_000)
    public void loadData() {
        for (int offset = 0; offset <= 100; offset += 50)
            saveSneakers(getSneakersFromRapid(offset));
    }

    public List<GoodsModel> fromSneakerToGoods(Sneaker sneaker, String url){
        List<GoodsModel> goodsList = new LinkedList<>();
        List<SizeModel> sizes = new LinkedList<>();

        for(Variant sneakerVariant: sneaker.getVariants()){
            String variantSize = sneakerVariant.getBrandSize();
            SizeModel size = sizeRepo.findBySize(variantSize);
            if (size == null) {
                sizeRepo.save(new SizeModel(variantSize));
                size = sizeRepo.findBySize(variantSize);
            }
            sizes.add(size);
        };

        String brandName = sneaker.getBrand().getName();
        BrandModel brand = brandRepo.findByName(brandName);
        if (brand == null) {
            brandRepo.save(new BrandModel(brandName));
            brand = brandRepo.findByName(brandName);
        }

        ShopModel shop = shopRepo.findByTitle(asosTitle);
        if (shop == null) {
            shopRepo.save(new ShopModel(asosTitle, asosUrl));
            shop = shopRepo.findByTitle(asosTitle);
        }

        String imageUrl = "";
        if (!sneaker.getMedia().getImages().isEmpty())
            imageUrl = sneaker.getMedia().getImages().get(0).getUrl();

        PictureModel picture = pictureRepo.findByUrl(imageUrl);
        if (picture == null) {
            pictureRepo.save(new PictureModel(imageUrl));
            picture = pictureRepo.findByUrl(imageUrl);
        }

        GoodsModel goods = new GoodsModel(shop, sneaker.getName(), sizes, brand, picture,
                sneaker.getVariants().get(0).getPrice().getCurrent().getText(), sneaker.getGender(),
                sneaker.getBaseUrl().toString() + "/" + url);
        goodsList.add(goods);
        return goodsList;
    }
}