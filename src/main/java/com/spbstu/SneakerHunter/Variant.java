package com.spbstu.SneakerHunter;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "sizeId",
        "brandSize",
        "isAvailable",
        "colourWayId",
        "colourCode",
        "colour",
        "price",
})

public class Variant {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("sizeId")
    private Integer sizeId;
    @JsonProperty("brandSize")
    private String brandSize;
    @JsonProperty("isAvailable")
    private Boolean isAvailable;
    @JsonProperty("colourWayId")
    private Integer colourWayId;
    @JsonProperty("colourCode")
    private String colourCode;
    @JsonProperty("colour")
    private String colour;
    @JsonProperty("price")
    private Price price;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("sizeId")
    public Integer getSizeId() {
        return sizeId;
    }

    @JsonProperty("sizeId")
    public void setSizeId(Integer sizeId) {
        this.sizeId = sizeId;
    }

    @JsonProperty("brandSize")
    public String getBrandSize() {
        return brandSize;
    }

    @JsonProperty("brandSize")
    public void setBrandSize(String brandSize) {
        this.brandSize = brandSize;
    }

    @JsonProperty("isAvailable")
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    @JsonProperty("isAvailable")
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @JsonProperty("colourWayId")
    public Integer getColourWayId() {
        return colourWayId;
    }

    @JsonProperty("colourWayId")
    public void setColourWayId(Integer colourWayId) {
        this.colourWayId = colourWayId;
    }

    @JsonProperty("colourCode")
    public String getColourCode() {
        return colourCode;
    }

    @JsonProperty("colourCode")
    public void setColourCode(String colourCode) {
        this.colourCode = colourCode;
    }

    @JsonProperty("colour")
    public String getColour() {
        return colour;
    }

    @JsonProperty("colour")
    public void setColour(String colour) {
        this.colour = colour;
    }

    @JsonProperty("price")
    public Price getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(Price price) {
        this.price = price;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}