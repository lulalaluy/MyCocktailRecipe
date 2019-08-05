package com.example.mycocktailrecipe.db;

import org.litepal.crud.DataSupport;

/**
 * project name：MyCocktailRecipe
 * className：
 * author：shuoyang
 * Date：2019-08-04 22:31
 */
public class Type extends DataSupport {

//    private int id;
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getId() {
//        return id;
//    }

}
