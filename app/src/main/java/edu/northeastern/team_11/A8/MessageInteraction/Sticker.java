package edu.northeastern.team_11.A8.MessageInteraction;

import edu.northeastern.team_11.A8.StickerCategories;

public class Sticker {
    private String path;
    private StickerCategories category;
    private boolean isSelected;

    public Sticker(String path, StickerCategories category) {
        this.path = path;
        this.category = category;
        this.isSelected = false;
    }

    public String getPath() {
        return path;
    }

    public String getDirectory() {
        return category.getDirectory();
    }

    public String getCategory() {
        return category.getCategory();
    }

    public boolean isSelected() {return isSelected;}

    public void changeSelection() {this.isSelected = !this.isSelected;}

    public void deselect() {this.isSelected = false;}

}
