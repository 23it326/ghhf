
package com.plantix.demo.Beans;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Example 
{

    @SerializedName("count")

    private Integer count;
    @SerializedName("recipes")

    private List<Recipe> recipes = null;
    private final static long serialVersionUID = 2140075637728174957L;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

}
