package com.yicj.study.model;

import lombok.Data;

@Data
public class UserModel {
    private Integer id;
    private String name;
    private Boolean gender;
    private Integer age;
    private String telephone;
    private String registerMode;
    private String thirdPartId;
    private String encrptPassword ;

}
