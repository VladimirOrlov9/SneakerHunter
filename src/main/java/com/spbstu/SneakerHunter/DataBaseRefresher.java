package com.spbstu.SneakerHunter;

import com.spbstu.SneakerHunter.models.*;
import com.spbstu.SneakerHunter.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//@Component
@EnableScheduling
public class DataBaseRefresher {
    private CategoryRepo categoryRepo;
    private SizeRepo sizeRepo;
    private BrandRepo brandRepo;
    private PictureRepo pictureRepo;
    private GoodsRepo goodsRepo;

    public DataBaseRefresher() {
    }

    @Autowired
    public DataBaseRefresher(CategoryRepo categoryRepo, SizeRepo sizeRepo, BrandRepo brandRepo, PictureRepo pictureRepo, GoodsRepo goodsRepo) {
        this.categoryRepo = categoryRepo;
        this.sizeRepo = sizeRepo;
        this.brandRepo = brandRepo;
        this.pictureRepo = pictureRepo;
        this.goodsRepo = goodsRepo;
    }

    public GoodsRepo getGoodsRepo() {
        return goodsRepo;
    }

    public void setGoodsRepo(GoodsRepo goodsRepo) {
        this.goodsRepo = goodsRepo;
    }

    public SizeRepo getSizeRepo() {
        return sizeRepo;
    }

    public void setSizeRepo(SizeRepo sizeRepo) {
        this.sizeRepo = sizeRepo;
    }

    public BrandRepo getBrandRepo() {
        return brandRepo;
    }

    public void setBrandRepo(BrandRepo brandRepo) {
        this.brandRepo = brandRepo;
    }

    public CategoryRepo getCategoryRepo() {
        return categoryRepo;
    }

    public void setCategoryRepo(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
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
        headers.add("x-rapidapi-key", "cb119519a1mshb1deaa42c38add2p1e2208jsn76d2565e7003");
        headers.add("x-rapidapi-host", "asos2.p.rapidapi.com");
        return new HttpEntity<>("body", headers);
    }

    public void getSneakers(){
        final Integer limit = 5;

        RestTemplate template = new RestTemplate();
        ResponseEntity<Sneakers> response = template.exchange(
                "https://asos2.p.rapidapi.com/products/v2/list?offset=0&categoryId=4209&limit=" + limit
                        + "&store=US&country=US&currency=USD&sort=freshness&lang=en-US&sizeSchema=US"
                , HttpMethod.GET, getRapidApiHttpHeader(), Sneakers.class);

        Sneakers sneakers = response.getBody();
        if(sneakers.getProducts() == null)
            return;

        try{
            for (Product product : sneakers.getProducts()) {
                List<Sneaker> sneakerList = getSneakerById(product.getId());

                for (Sneaker sneaker : sneakerList) {
                    List<GoodsModel> goods = fromSneakerToGoods(sneaker, sneakers.getCategoryName(),
                            product.getUrl());

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
        RestTemplate template = new RestTemplate();
        List<Sneaker> sneakers = new LinkedList<>();

        ResponseEntity<Sneaker> response = template.exchange(
                "https://asos2.p.rapidapi.com/products/v3/detail?id="+ sneakersId +
                        "&store=US&sizeSchema=US&lang=en-US&currency=USD",
                HttpMethod.GET, getRapidApiHttpHeader(), Sneaker.class);

        Sneaker sneaker = response.getBody();
        sneakers.add(sneaker);
//            if (sneaker != null) {
//                System.out.println("Sneaker: " + sneaker.getVariants().get(1).getName() + sneaker.getGender() + " - "
//                        + sneaker.getBaseUrl() + " Size: " + sneaker.getVariants().get(1).getBrandSize());
//            }
        return sneakers;
    }

    @Scheduled(fixedDelay = 1_000_000)
    public void loadData() {

        getSneakers();

    }

    public List<GoodsModel> fromSneakerToGoods(Sneaker sneaker, String categoryTitle, String url){
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

        CategoryModel category = categoryRepo.findByTitle(categoryTitle);
        if (category == null) {
            categoryRepo.save(new CategoryModel(categoryTitle));
            category = categoryRepo.findByTitle(categoryTitle);
        }

        String brandName = sneaker.getBrand().getName();
        BrandModel brand = brandRepo.findByName(brandName);
        if (brand == null) {
            brandRepo.save(new BrandModel(brandName));
            brand = brandRepo.findByName(brandName);
        }

        String imageUrl = "";
        if (!sneaker.getMedia().getImages().isEmpty())
            imageUrl = sneaker.getMedia().getImages().get(0).getUrl();

        PictureModel picture = pictureRepo.findByUrl(imageUrl);
        if (picture == null) {
            pictureRepo.save(new PictureModel(imageUrl));
            picture = pictureRepo.findByUrl(imageUrl);
        }

        GoodsModel goods = new GoodsModel(category, sizes, brand, picture,
                sneaker.getVariants().get(0).getPrice().getCurrent().getText(), sneaker.getGender(),
                sneaker.getBaseUrl().toString() + "/" + url, null);
        goodsList.add(goods);
        return goodsList;
    }


}