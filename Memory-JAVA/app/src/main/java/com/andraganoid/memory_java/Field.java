package com.andraganoid.memory_java;


public class Field {

    private int item;
    private boolean solved;
    private boolean open;
    private int index;


    public Field(int item, int index) {
        this.item = item;
        this.solved = false;
        this.open = false;
        this.index = index;
    }


    public int getItem() {
        return item;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getIndex() {
        return index;
    }

}
