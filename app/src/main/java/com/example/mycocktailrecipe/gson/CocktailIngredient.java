package com.example.mycocktailrecipe.gson;

/**
 * project name：MyCocktailRecipe
 * className：
 * author：shuoyang
 * Date：2019-08-05 05:42
 */
public class CocktailIngredient {
    public String Iname;
    public Float Iquantity;

    public void setIname(String iname) {
        Iname = iname;
    }

    public String getIname() {
        return Iname;
    }

    public void setIquantity(Float iquantity) {
        Iquantity = iquantity;
    }

    public Float getIquantity() {
        return Iquantity;
    }
}
