package com.wsf.kol;

import java.util.ArrayList;
import java.util.List;

public class Square implements Cloneable{


    private ArrayList<SquareLocation> locs;
    private int id;
    private int type;

    public final static int _2X2=4;
    public final static int _2X1=3;
    public final static int _1X2=2;
    public final static int _1X1=1;


    public Square(int id, int type,ArrayList<SquareLocation> locs) {
        this.locs = locs;
        this.id = id;
        this.type = type;
    }
    public List<SquareLocation> getLocs() {
        return locs;
    }
    public void setLocs(ArrayList<SquareLocation> locs) {
        this.locs = locs;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public Square clone(){
        ArrayList<SquareLocation> newlocs=new ArrayList<>();

        for(SquareLocation loc:this.locs){
            newlocs.add(loc.clone());
        }

        int id=this.id;
        int tp=this.type;
        return new Square(id, tp, newlocs);

    }

    public ArrayList<SquareLocation> getBound(int direct){

        ArrayList<SquareLocation> l=new ArrayList<>();

        switch (this.type) {
            case _1X1: l.add(locs.get(0));

                break;
            case _1X2:
                if(direct==1){
                    l.add(locs.get(1));
                }else if(direct==0||direct==2){
                    l.addAll(locs);
                }else if(direct==3){
                    l.add(locs.get(0));
                }else{
                    l=null;
                }
                break;
            case _2X1:
                if(direct==0){
                    l.add(locs.get(0));
                }else if(direct==1||direct==3){
                    l.addAll(locs);
                }else if(direct==2){
                    l.add(locs.get(1));
                }else{
                    l=null;
                }

                break;
            case _2X2:
                if(direct==0){
                    l.add(locs.get(0));
                    l.add(locs.get(1));
                }else if(direct==1){
                    l.add(locs.get(1));
                    l.add(locs.get(3));
                }else if(direct==2){
                    l.add(locs.get(2));
                    l.add(locs.get(3));
                }else if(direct==3){
                    l.add(locs.get(0));
                    l.add(locs.get(2));
                }else{
                    l=null;
                }
                break;
            default:
                l=null;
                break;
        }


        return l;

    }






}
