package com.exam.ztes.SelectItemList;

public class SelectableItem extends Item {
    private boolean isSelected = false;

    public SelectableItem(Item item, boolean isSelected) {
        super(item.getName(), item.getSurname());
        this.isSelected = isSelected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
