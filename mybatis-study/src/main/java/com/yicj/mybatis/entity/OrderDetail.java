package com.yicj.mybatis.entity;

import lombok.Data;

@Data
public class OrderDetail {
    private Integer id;
    private Integer orderId;
    private Double totalPrice;
    private Integer status;
    private Item item ;
}