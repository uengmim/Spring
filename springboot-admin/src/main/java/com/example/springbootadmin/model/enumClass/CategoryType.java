package com.admin.springbootadmin.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryType {

    COMPUTER(0,"컴퓨터","컴퓨터-전자제품"),
    CLOTHING(1,"의류","다양한 의류"),
    MULTI_SHOP(2,"멀티샵","각종 브랜드 취급"),
    INTERIOR(3,"인테리어","홈데코 주력 상품"),
    FOOD(4,"음식","웰빙 기획전 상품"),
    SPORTS(5,"스포츠","스포츠 상품"),
    SHOPPING_MALL(6,"쇼핑몰","쇼핑몰 취급 상품"),
    DUTY_FREE(7,"면세점","각종 면세 상품"),
    BEAUTY(8,"화장","피부 화장품");

    private int id;
    private String title;
    private String description;


    public static CategoryType getById(int id){
        for(CategoryType c : values()){
            if(c.id == id) return c;
        }

        throw new IllegalArgumentException();
    }
}
