package edu.northeastern.team_11.A8;

import java.text.NumberFormat;

public enum StickerCategories {
    FREE (0.00, "free"),
    GOLD (1.00, "gold"),
    PLATINUM (5.00, "platinum");

    private final double cost;
    private final String category;

    StickerCategories(double cost, String category) {
        this.cost = cost;
        this.category = category;
    }

    public String getCost(int qty) {
        double total = qty * cost;
        NumberFormat numberFormatter = NumberFormat.getCurrencyInstance();
        return numberFormatter.format(total);
    }

    public String getDirectory(){
        return "file:///android_asset/stickers/" + category + "/";
    }

    public String getCategory() {return category;}

}
