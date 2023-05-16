package com.example.shopmanager.Forms;

public class FormInventoryModel {

    private String size;
    private boolean selected;
    private int count;

    public FormInventoryModel(String size, boolean selected, int count) {
        this.size = size;
        this.selected = selected;
        this.count = count;
    }

    public FormInventoryModel(String size) {
        this(size, false, 0);
    }

    public void setCount(int count) { this.count = count; }
    public void setSelected(boolean selected) { this.selected = selected; }

    public String getSize() { return size; }
    public int getCount() { return count; }
    public boolean isSelected() { return selected; }
}
