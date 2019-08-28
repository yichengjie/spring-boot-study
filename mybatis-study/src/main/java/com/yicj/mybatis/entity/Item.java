package com.yicj.mybatis.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Item {
    private Integer id;
    private String itemName;
    private Float itemPrice;
    private String itemDetail;
}