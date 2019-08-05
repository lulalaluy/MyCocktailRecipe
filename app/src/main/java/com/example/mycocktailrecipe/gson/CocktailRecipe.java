package com.example.mycocktailrecipe.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * project name：MyCocktailRecipe
 * className：
 * author：shuoyang
 * Date：2019-08-05 06:03
 */
public class CocktailRecipe {

    public String Cname;
    public String Ctype;

    @SerializedName("ingredients")
    public List<CocktailIngredient> ingredientList;

    public void setCname(String cname) {
        Cname = cname;
    }

    public String getCname() {
        return Cname;
    }

    public void setCtype(String ctype) {
        Ctype = ctype;
    }

    public String getCtype() {
        return Ctype;
    }
}
