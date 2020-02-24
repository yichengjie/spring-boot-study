package com.yicj.study.entity;

public class UserInfo {
    private Integer id;

    private String name;

    private Boolean gendeer;

    private Integer age;

    private String telephone;

    private String registerMode;

    private String thirdPartId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getGendeer() {
        return gendeer;
    }

    public void setGendeer(Boolean gendeer) {
        this.gendeer = gendeer;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getRegisterMode() {
        return registerMode;
    }

    public void setRegisterMode(String registerMode) {
        this.registerMode = registerMode == null ? null : registerMode.trim();
    }

    public String getThirdPartId() {
        return thirdPartId;
    }

    public void setThirdPartId(String thirdPartId) {
        this.thirdPartId = thirdPartId == null ? null : thirdPartId.trim();
    }
}