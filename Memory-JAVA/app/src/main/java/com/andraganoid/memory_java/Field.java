package com.andraganoid.memory_java;

public class Field {

    private int item;
    private boolean solved;
    private boolean open;


    public Field(int item) {
        this.item = item;
        this.solved = false;
        this.open = false;
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

}
