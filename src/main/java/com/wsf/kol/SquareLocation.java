package com.wsf.kol;

public class SquareLocation implements Cloneable{

    private int row;
    private int col;

    public SquareLocation(int row, int col) {

        this.row = row;
        this.col = col;
    }
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getCol() {
        return col;
    }
    public void setCol(int col) {
        this.col = col;
    }
    public SquareLocation clone(){
        return new SquareLocation(this.getRow(), this.getCol());
    }




}
