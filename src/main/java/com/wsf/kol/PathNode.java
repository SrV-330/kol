package com.wsf.kol;

import java.util.ArrayList;
import java.util.List;

public class PathNode {


    private PathNode parent;
    private ArrayList<PathNode> childs;
    private int direct;
    private Square movedSquare;
    private int[][] currmatrix;
    private ArrayList<Square> movedSquares;



    public ArrayList<Square> getMovedSquares() {
        return movedSquares;
    }

    public void setMovedSquares(ArrayList<Square> movedSquares) {
        this.movedSquares = movedSquares;
    }

    public int[][] getCurrmatrix() {
        return currmatrix;
    }

    public void setCurrmatrix(int[][] currmatrix) {
        this.currmatrix = currmatrix;
    }

    public PathNode(int direct, Square movedSquare) {
        this.direct = direct;
        this.movedSquare = movedSquare;
        parent=null;
        childs=new ArrayList<>();
        movedSquares=new ArrayList<>();
    }

    public boolean childsIsEmpty(){
        return childs==null||childs.isEmpty();
    }
    public boolean movedSquaresIsEmpty(){
        return movedSquares==null||movedSquares.isEmpty();
    }

    public void addChild(PathNode child){
        this.childs.add(child);
    }
    public PathNode getParent() {
        return parent;
    }
    public void setParent(PathNode parent) {
        this.parent = parent;
    }
    public List<PathNode> getChilds() {
        return childs;
    }
    public void setChilds(ArrayList<PathNode> childs) {
        this.childs = childs;
    }
    public int getDirect() {
        return direct;
    }
    public void setDirect(int direct) {
        this.direct = direct;
    }
    public Square getMovedSquare() {
        return movedSquare;
    }
    public void setMovedSquare(Square movedSquare) {
        this.movedSquare = movedSquare;
    }




}
