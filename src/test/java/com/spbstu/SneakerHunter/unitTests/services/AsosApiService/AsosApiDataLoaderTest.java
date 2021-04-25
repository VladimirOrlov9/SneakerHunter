package com.spbstu.SneakerHunter.services.AsosApiService;

import com.spbstu.SneakerHunter.models.BrandModel;
import com.spbstu.SneakerHunter.models.GoodsModel;
import com.spbstu.SneakerHunter.models.PictureModel;
import com.spbstu.SneakerHunter.models.ShopModel;
import com.spbstu.SneakerHunter.repos.BrandRepo;
import com.spbstu.SneakerHunter.repos.GoodsRepo;
import com.spbstu.SneakerHunter.services.AliApiService.AliApiDataLoader;
import org.aspectj.weaver.ast.Var;
import org.checkerframework.checker.units.qual.C;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AsosApiDataLoaderTest {
    @Autowired
    AsosApiDataLoader loader;

    @MockBean
    private GoodsRepo goodsRepo;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private ResponseEntity<Sneakers> response;

    @Test
    void getRapidApiHttpHeaderTest() {
        assertEquals(AsosApiDataLoader.API_KEY_ASOS, Objects.requireNonNull(loader.getRapidApiHttpHeader().getHeaders().get("x-rapidapi-key")).get(0));
        assertEquals(AsosApiDataLoader.API_HOST_ASOS, Objects.requireNonNull(loader.getRapidApiHttpHeader().getHeaders().get("x-rapidapi-host")).get(0));
    }

    @Test
    void getSneakersTest() {
    }

    @Test
    void getSneakerByIdTest() {
        ResponseEntity<Sneaker> entity = new ResponseEntity<>(getTestSneaker(), HttpStatus.OK);

        doReturn(entity).when(restTemplate).exchange(anyString(), ArgumentMatchers.any(), ArgumentMatchers.any(),
                Mockito.eq(Sneaker.class));

        assertEquals("tapok", loader.getSneakerById(1).get(0).getName());
    }

    @Test
    void getSneakersFromRapidTest() {
        ResponseEntity<Sneakers> entity = new ResponseEntity<>(getTestSneakers(), HttpStatus.OK);

        doReturn(entity).when(restTemplate).exchange(anyString(), ArgumentMatchers.any(), ArgumentMatchers.any(),
                Mockito.eq(Sneakers.class));

        assertEquals("sneakers", loader.getSneakersFromRapid(10).getCategoryName());
    }

    @Test
    void saveSneakers() {
        AsosApiDataLoader loaderSpy = spy(loader);

        List<Sneaker> sneakerList = new ArrayList<>();
        sneakerList.add(getTestSneaker());

        GoodsModel good = new GoodsModel();
        good.setName("tapok");
        good.setUri("tapok.com");
        good.setGender("Man");

        List<GoodsModel> goods = new ArrayList<GoodsModel>();
        goods.add(good);

        doReturn(goods).when(loaderSpy).fromSneakerToGoods(ArgumentMatchers.any(), anyString());
        doReturn(sneakerList).when(loaderSpy).getSneakerById(ArgumentMatchers.any());

        loaderSpy.saveSneakers(getTestSneakers());
        verify(goodsRepo, times(1)).save(ArgumentMatchers.any(GoodsModel.class));
    }

    private Sneakers getTestSneakers() {
        Sneakers sneakers = new Sneakers();
        sneakers.setCategoryName("sneakers");

        Product product = new Product();
        product.setUrl("http://test.com");
        List<Product> products = new ArrayList<>();
        products.add(product);
        sneakers.setProducts(products);
        return sneakers;
    }

    @Test
    void fromSneakerToGoods() {
        Sneaker sneaker = getTestSneaker();
        assertEquals("tapok", loader.fromSneakerToGoods(sneaker, "http://test.com").get(0).getName());
        assertEquals("Man", loader.fromSneakerToGoods(sneaker, "http://test.com").get(0).getGender());
    }

    @Test
    void loadDataTest() {
        AsosApiDataLoader loaderSpy = spy(loader);

        ResponseEntity<Sneakers> entity = new ResponseEntity<>(getTestSneakers(), HttpStatus.OK);
        doReturn(entity.getBody()).when(loaderSpy).getSneakersFromRapid(anyInt());

        List<Sneaker> sneakerList = new ArrayList<>();
        sneakerList.add(getTestSneaker());
        doReturn(sneakerList).when(loaderSpy).getSneakerById(anyInt());

        GoodsModel good = new GoodsModel();
        good.setName("tapok");
        good.setUri("tapok.com");
        good.setGender("Man");
        List<GoodsModel> goods = new ArrayList<GoodsModel>();
        goods.add(good);

        doReturn(goods).when(loaderSpy).fromSneakerToGoods(ArgumentMatchers.any(), anyString());

        loaderSpy.loadData();
        verify(loaderSpy, atLeastOnce()).getSneakersFromRapid(anyInt());
    }

    private Sneaker getTestSneaker() {
        Sneaker sneaker = new Sneaker();
        sneaker.setName("tapok");
        sneaker.setBrand(null);
        sneaker.setBaseUrl(null);
        sneaker.setGender("Man");
        sneaker.setId(1);
        sneaker.setProductCode(1234L);
        sneaker.setMedia(null);
        sneaker.setVariants(null);
        Variant variant = new Variant();
        variant.setBrandSize("42");
        Price price = new Price();
        Current current = new Current();

        price.setCurrent(current);
        variant.setPrice(price);
        List<Variant> variants = new ArrayList<>();
        variants.add(variant);
        sneaker.setVariants(variants);
        Brand brand = new Brand();
        brand.setName("adidas");
        sneaker.setBrand(brand);
        Image image = new Image();
        Media media = new Media();
        List<Image> images = new ArrayList<>();
        media.setImages(images);
        sneaker.setMedia(media);


        try {
            sneaker.setBaseUrl(new URI("http://asos.com"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return sneaker;
    }
}