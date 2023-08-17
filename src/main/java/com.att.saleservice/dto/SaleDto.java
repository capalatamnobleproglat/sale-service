package com.att.saleservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

import java.util.Date;

public class SaleDto {


    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("car_id")
    private Long carId;

    private Integer quantity;

    @JsonProperty("sale_date")
    private Date saleDate;

    public SaleDto() {
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }
}
