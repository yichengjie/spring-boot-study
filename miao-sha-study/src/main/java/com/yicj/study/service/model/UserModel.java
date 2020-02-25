package com.yicj.study.service.model;

import lombok.Data;

@Data
public class UserModel {
    private Integer id;
    private String name;
    private Integer gender;
    private Integer age;
    private String telephone;
    private String registerMode;
    private String thirdPartId;
    private String encrptPassword ;

}
