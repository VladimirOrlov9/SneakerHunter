package com.spbstu.SneakerHunter.services.AliApiService;

import com.spbstu.SneakerHunter.controllers.GoodsController;
import com.spbstu.SneakerHunter.models.GoodsModel;
import com.spbstu.SneakerHunter.repos.GoodsRepo;
import com.spbstu.SneakerHunter.services.AliApiService.AliApiDataLoader;
import com.spbstu.SneakerHunter.services.AsosApiService.Sneaker;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AliApiDataLoaderTest {
//    @Autowired
//    AliApiDataLoader loader;
//
//    @MockBean
//    GoodsRepo goodsRepo;
//
//    @MockBean
//    private RestTemplate restTemplate;
//
//    @Test
//    void getRapidApiHttpHeaderTest() {
//        assertEquals(AliApiDataLoader.API_KEY_ALI, Objects.requireNonNull(loader.getRapidApiHttpHeader().getHeaders().get("x-rapidapi-key")).get(0));
//        assertEquals(AliApiDataLoader.API_HOST_ALI, Objects.requireNonNull(loader.getRapidApiHttpHeader().getHeaders().get("x-rapidapi-host")).get(0));
//    }
//
//    @Test
//    void getProductsFromRapid() {
//        ResponseEntity<Product> entity = new ResponseEntity<>(getTestProduct(), HttpStatus.OK);
//
//        doReturn(entity).when(restTemplate).exchange(anyString(), ArgumentMatchers.any(), ArgumentMatchers.any(),
//                Mockito.eq(Product.class));
//
//        assertEquals("tapok",
//                loader.getProductsFromRapid(1)
//                        .getData()
//                        .getItems()
//                        .get(0)
//                        .getProductElements()
//                        .getTitle()
//                        .getTitle());
//    }
//
//    @Test
//    void saveSneakersTest() {
//        AliApiDataLoader loaderSpy = spy(loader);
//
//        GoodsModel good = new GoodsModel();
//        good.setName("tapok");
//        good.setUri("tapok.com");
//        good.setGender("Man");
//
//        List<GoodsModel> goods = new ArrayList<GoodsModel>();
//        goods.add(good);
//
//        doReturn(goods).when(loaderSpy).fromSneakerToGoods(any());
//
//        loaderSpy.saveSneakers(getTestProduct());
//        verify(goodsRepo, times(1)).save(any(GoodsModel.class));
//    }
//
//
//    private Product getTestProduct(){
//        Title title = new Title();
//        title.setTitle("tapok");
//        Image image = new Image();
//        image.setImgUrl("http://testimage.com");
//        Price price = new Price();
//        SellPrice sellPrice = new SellPrice();
//        sellPrice.setFormatedAmount("1000$");
//        price.setSellPrice(sellPrice);
//        ProductElements productElements = new ProductElements();
//        productElements.setTitle(title);
//        productElements.setImage(image);
//        productElements.setPrice(price);
//        Item item = new Item();
//        item.setProductElements(productElements);
//        List<Item> items = new ArrayList<Item>() {};
//        items.add(item);
//        Data data = new Data();
//        data.setItems(items);
//        Product product = new Product();
//        product.setData(data);
//        return product;
//    }
//
//    @Test
//    void loadDataFromRapidTest() {
//        AliApiDataLoader loaderSpy = spy(loader);
//
//        ResponseEntity<Product> entity = new ResponseEntity<>(getTestProduct(), HttpStatus.OK);
//        doReturn(entity.getBody()).when(loaderSpy).getProductsFromRapid(anyInt());
//
//        GoodsModel good = new GoodsModel();
//        good.setName("tapok");
//        good.setUri("tapok.com");
//        good.setGender("Man");
//        List<GoodsModel> goods = new ArrayList<GoodsModel>();
//        goods.add(good);
//
//        doReturn(goods).when(loaderSpy).fromSneakerToGoods(any());
//
//        loaderSpy.loadDataFromRapid();
//
//        verify(loaderSpy, atLeastOnce()).getProductsFromRapid(anyInt());
//    }
//
//    @Test
//    void fromSneakerToGoodsTest() {
//        Item item = getTestProduct().getData().getItems().get(0);
//        assertEquals("tapok", loader.fromSneakerToGoods(item).get(0).getName());
//        assertEquals("1000$", loader.fromSneakerToGoods(item).get(0).getMoney());
//    }
}