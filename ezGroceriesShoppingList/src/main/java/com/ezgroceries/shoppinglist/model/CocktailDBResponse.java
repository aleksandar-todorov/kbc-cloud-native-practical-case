package com.ezgroceries.shoppinglist.model;

import java.util.List;

/**
 * @author Aleksandar Todorov (jf08663)
 */
public class CocktailDBResponse {

    private List<DrinkResource> drinks;

    public List<DrinkResource> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<DrinkResource> drinks) {
        this.drinks = drinks;
    }

    public static class DrinkResource {

        private String idDrink;
        private String strDrink;
        private String strGlass;
        private String strInstructions;
        private String strDrinkThumb;
        private String strIngredient1;
        private String strIngredient2;
        private String strIngredient3;
        private String strIngredient4;

        public DrinkResource() {
        }

        public DrinkResource(String idDrink, String strDrink, String strGlass, String strInstructions, String strDrinkThumb, String strIngredient1,
                String strIngredient2, String strIngredient3, String strIngredient4) {
            this.idDrink = idDrink;
            this.strDrink = strDrink;
            this.strGlass = strGlass;
            this.strInstructions = strInstructions;
            this.strDrinkThumb = strDrinkThumb;
            this.strIngredient1 = strIngredient1;
            this.strIngredient2 = strIngredient2;
            this.strIngredient3 = strIngredient3;
            this.strIngredient4 = strIngredient4;
        }

        public String getIdDrink() {
            return idDrink;
        }

        public void setIdDrink(String idDrink) {
            this.idDrink = idDrink;
        }

        public String getStrDrink() {
            return strDrink;
        }

        public void setStrDrink(String strDrink) {
            this.strDrink = strDrink;
        }

        public String getStrGlass() {
            return strGlass;
        }

        public void setStrGlass(String strGlass) {
            this.strGlass = strGlass;
        }

        public String getStrInstructions() {
            return strInstructions;
        }

        public void setStrInstructions(String strInstructions) {
            this.strInstructions = strInstructions;
        }

        public String getStrDrinkThumb() {
            return strDrinkThumb;
        }

        public void setStrDrinkThumb(String strDrinkThumb) {
            this.strDrinkThumb = strDrinkThumb;
        }

        public String getStrIngredient1() {
            return strIngredient1;
        }

        public void setStrIngredient1(String strIngredient1) {
            this.strIngredient1 = strIngredient1;
        }

        public String getStrIngredient2() {
            return strIngredient2;
        }

        public void setStrIngredient2(String strIngredient2) {
            this.strIngredient2 = strIngredient2;
        }

        public String getStrIngredient3() {
            return strIngredient3;
        }

        public void setStrIngredient3(String strIngredient3) {
            this.strIngredient3 = strIngredient3;
        }

        public String getStrIngredient4() {
            return strIngredient4;
        }

        public void setStrIngredient4(String strIngredient4) {
            this.strIngredient4 = strIngredient4;
        }
    }
}
