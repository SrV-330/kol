package com.wsf.kol;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;


public class Kol {

    public static int[][] matrix={
            {1,1,1,1,1,1,1},
            {1,0,0,0,0,1,1},
            {1,0,0,0,0,1,1},
            {1,0,0,0,0,1,1},
            {1,0,0,0,0,1,1},
            {1,0,0,0,0,1,1},
            {1,1,1,1,1,1,1}
    };

    public static int[][] initMatrix={
            {1,1,1,1,1,1,1},
            {1,0,0,0,0,1,1},
            {1,0,0,0,0,1,1},
            {1,0,0,0,0,1,1},
            {1,0,0,0,0,1,1},
            {1,0,0,0,0,1,1},
            {1,1,1,1,1,1,1}
    };
    public static final int MATRIX_ROW=7;
    public static final int MATRIX_COL=6;
    public static final int STOP_SEARCH_NUM=1;
    private HashMap<String,PathNode> pathNodes=new HashMap<>();

    private ArrayList<Square> squares=new ArrayList<>();


    private Queue<MoveInfo> moveQueue=new LinkedList<>();

    private ArrayList<PathNode> endNodes=new ArrayList<>();

    private Queue<PathNode> currs=new LinkedList<>();
    private PathNode curr=null;
    private PathNode root=null;

    private void init(){

        ArrayList<SquareLocation> locs=new ArrayList<>();
        SquareLocation loc=new SquareLocation(1, 1);
        locs.add(loc);
        loc=new SquareLocation(2, 1);
        locs.add(loc);
        Square square=new Square(21, Square._2X1, locs);
        squares.add(square);


        locs=new ArrayList<>();
        loc=new SquareLocation(5, 1);
        locs.add(loc);
        square=new Square(41, Square._1X1, locs);
        squares.add(square);
        locs=new ArrayList<>();
        loc=new SquareLocation(5, 2);
        locs.add(loc);
        square=new Square(42, Square._1X1, locs);
        squares.add(square);
        locs=new ArrayList<>();
        loc=new SquareLocation(5, 3);
        locs.add(loc);
        square=new Square(43, Square._1X1, locs);
        squares.add(square);
        locs=new ArrayList<>();
        loc=new SquareLocation(5, 4);
        locs.add(loc);
        square=new Square(44, Square._1X1, locs);
        squares.add(square);



        locs=new ArrayList<>();
        loc=new SquareLocation(1, 2);
        locs.add(loc);
        loc=new SquareLocation(1, 3);
        locs.add(loc);
        loc=new SquareLocation(2, 2);
        locs.add(loc);
        loc=new SquareLocation(2, 3);
        locs.add(loc);
        square=new Square(11, Square._2X2, locs);
        squares.add(square);

        locs=new ArrayList<>();
        loc=new SquareLocation(1, 4);
        locs.add(loc);
        loc=new SquareLocation(2, 4);
        locs.add(loc);
        square=new Square(22, Square._2X1, locs);
        squares.add(square);


        locs=new ArrayList<>();
        loc=new SquareLocation(3, 1);
        locs.add(loc);
        loc=new SquareLocation(4, 1);
        locs.add(loc);
        square=new Square(23, Square._2X1, locs);
        squares.add(square);

        locs=new ArrayList<>();
        loc=new SquareLocation(3, 4);
        locs.add(loc);
        loc=new SquareLocation(4, 4);
        locs.add(loc);
        square=new Square(24, Square._2X1, locs);
        squares.add(square);

        locs=new ArrayList<>();
        loc=new SquareLocation(3, 2);
        locs.add(loc);
        loc=new SquareLocation(3, 3);
        locs.add(loc);
        square=new Square(31, Square._1X2, locs);
        squares.add(square);




    }
    public void run(){

        root=new PathNode(-1, null);
        root.setChilds(new ArrayList<>());
        root.setParent(null);
        root.setCurrmatrix(fullMatrix());
        root.setMovedSquares(copySquares(this.squares));

        curr=root;
        currs.add(curr);

        matrix=fullMatrix();
        printMatrix(matrix);
        pathNodes.put(matrix2String(matrix), curr);
        //curr=doDeepFirstSearch();
        doBreadthFirstSearch();

    }
    public boolean isEnd(int[][] map){
        return ((map[4][2]==Square._2X2&&map[4][3]==Square._2X2&&
                map[5][2]==Square._2X2&&map[5][3]==Square._2X2));
    }
    public boolean stopSearch(){
        return endNodes.size()>=STOP_SEARCH_NUM;
    }
    public void doBreadthFirstSearch(){



        while(!currs.isEmpty()){
            PathNode c=currs.poll();
            if(breadthFirstSearch(c)){
                break;
            }

        }





    }

    public boolean breadthFirstSearch(PathNode parent){


        PathNode n=null;

        addMoveInfo(parent);
        MoveInfo mif=null;

        while(!moveQueue.isEmpty()){
            mif=moveQueue.poll();
            n=new PathNode(mif.d, mif.sq.clone());
            n.setMovedSquares(copySquares(parent.getMovedSquares()));
            int index=parent.getMovedSquares().indexOf(mif.sq);
            n.setMovedSquare(n.getMovedSquares().get(index));
            n=squareMove(n.getMovedSquares().get(index), mif.d,n, parent);
            curr=n;
            currs.offer(n);

            if(isEnd(curr.getCurrmatrix())){
                endNodes.add(curr);
            }
        }

        return stopSearch();



    }

    public void addMoveInfo(PathNode parent){
        int direct=-1;
        Square square=null;
        for(Square item:parent.getMovedSquares()){

            for(int i=0;i<4;i++){
                if(squareMoveable(item, i,parent.getCurrmatrix())){
                    square=item;
                    direct=i;

                    if(direct>=0&&direct<=3&&square!=null){
                        System.out.println(square.getId()+":"+square.getType()+":"+direct);
                        moveQueue.add(new MoveInfo(square, direct));
                    }
                }

            }


        }
    }
    public ArrayList<Square> copySquares(ArrayList<Square> squares){

        ArrayList<Square> sqs=new ArrayList<>();
        for(Square sq:squares){
            sqs.add(sq.clone());
        }

        return sqs;
    }
    int deep=0;

    public PathNode deepFirstSearch(PathNode parent){

        int direct=-1;
        Square square=null;
        PathNode p=parent;
        PathNode n=null;
        //if(deep++==100) return null;
        System.out.println("deep:"+(deep++));
        boolean flag=false;
        for(Square item:squares){

            for(int i=0;i<4;i++){
                if(squareMoveable(item, i, matrix)){
                    square=item;
                    direct=i;
                    System.out.println(square.getId()+":"+square.getType()+":"+direct);
                    flag=true;
                    break;
                }

            }
            if(flag) break;


        }

        if(direct>=0&&direct<=3&&square!=null){

            n=squareMove(square, direct, p);
            curr=n;
            return curr;
        }else{
            curr=squareMoveback(curr.getMovedSquare(), curr.getDirect(), curr);
            return curr;
        }


    }

    public PathNode doDeepFirstSearch(){
        PathNode n=null;
        PathNode c=curr;


        do{
            n=deepFirstSearch(c);
            if(n!=null){
                c=n;
            }else{
                n=c.getParent();
            }
            //System.gc();
        }while(!isEnd(matrix));

        return c;

    }
    public PathNode deepFirstSearchRecursion(PathNode parent){
        int direct=-1;
        Square square=null;
        PathNode p=parent;
        PathNode n=null;
        //if(deep++==100) return null;
        System.out.println("deep:"+(deep++));
        if(isEnd(matrix)) return p;
        boolean flag=false;
        for(Square item:squares){

            for(int i=0;i<4;i++){
                if(squareMoveable(item, i, matrix)){
                    square=item;
                    direct=i;
                    System.out.println(square.getId()+":"+square.getType()+":"+direct);
                    flag=true;
                    break;
                }

            }
            if(flag) break;


        }

        if(direct>=0&&direct<=3&&square!=null){

            n=squareMove(square, direct, p);
            if(n!=null){
                curr=n;
                p=n;
                return deepFirstSearchRecursion(curr);
            }else{

                curr=curr.getParent();
                return deepFirstSearchRecursion(curr);
            }
        }else{
            curr=squareMoveback(curr.getMovedSquare(), curr.getDirect(), curr);
            return deepFirstSearchRecursion(curr);
        }




    }

    public int[][] copyArrays(int[][] matrix){
        int[][] map=new int[MATRIX_ROW][MATRIX_ROW];
        for(int i =0;i<MATRIX_ROW;i++){
            for(int j=0;j<MATRIX_ROW;j++){
                map[i][j]=matrix[i][j];
            }
        }
        return map;

    }
    public void printMatrix(int[][] matrix){
        for(int i=0;i<Kol.MATRIX_ROW;i++){
            for(int j=0;j<MATRIX_ROW;j++)
                System.out.print(matrix[i][j]);
            System.out.println();
        }
    }
    public int[][] fullMatrix(){
        int[][] map=copyArrays(initMatrix);


        for(Square item:squares){
            for(SquareLocation loc:item.getLocs()){

                map[loc.getRow()][loc.getCol()]=item.getType();

            }

        }


        return map;
    }

    public int[][] fullMatrix(ArrayList<Square> items){
        int[][] map=copyArrays(initMatrix);


        for(Square item:items){
            for(SquareLocation loc:item.getLocs()){

                map[loc.getRow()][loc.getCol()]=item.getType();

            }

        }


        return map;
    }

    public String matrix2String(int[][] matrix){
        String s="";
        for(int i=0;i<MATRIX_ROW;i++){
            for(int j=0;j<MATRIX_COL;j++){
                s+=Integer.toString(matrix[i][j]);
            }
        }
        return s;
    }
    public boolean squareMoveable(Square square,int direct,int[][] matrix){
        ArrayList<SquareLocation> bounds=square.getBound(direct);
        boolean flag=false;

        switch (direct) {
            case 0:
                if(upMoveable(square,bounds, matrix)){
                    flag=true;
                }
                break;
            case 1:
                if(rightMoveable(square,bounds, matrix)){
                    flag=true;
                }
                break;
            case 2:
                if(downMoveable(square,bounds, matrix)){
                    flag=true;
                }
                break;
            case 3:
                if(leftMoveable(square,bounds, matrix)){
                    flag=true;
                }
                break;
            default:
                flag=false;
                break;
        }



        return flag;
    }
    public PathNode squareMoveback(Square square,int direct,PathNode curr){

        int d=(direct+2)%4;
        changeSquarLocation(square, d);
        matrix=fullMatrix();
        return curr.getParent();

    }
    public PathNode squareMove(Square square,int direct,PathNode parent){

        PathNode node=null;
        changeSquarLocation(square, direct);
        matrix=fullMatrix();
        printMatrix(matrix);
        node=new PathNode(direct, square);
        node.setParent(parent);
        parent.addChild(node);
        String key=matrix2String(matrix);
        pathNodes.put(key, node);
        return node;

    }
    public PathNode squareMove(Square square,int direct,PathNode node,PathNode parent){


        changeSquarLocation(node.getMovedSquare(), direct);
        node.setCurrmatrix(fullMatrix(node.getMovedSquares()));

        printMatrix(node.getCurrmatrix());
        node.setParent(parent);
        parent.addChild(node);
        String key=matrix2String(node.getCurrmatrix());
        pathNodes.put(key, node);
        return node;

    }



    public boolean upMoveable(Square square,ArrayList<SquareLocation> bounds,int[][] matrix){

        int[][] map=copyArrays(matrix);
        for(SquareLocation b:bounds){
            if((b.getRow()-1)<0) return false;
            if(map[b.getRow()-1][b.getCol()]!=0) return false;
        }
        for(SquareLocation loc:square.getLocs()){
            map[loc.getRow()][loc.getCol()]=0;
        }
        for(SquareLocation loc:square.getLocs()){
            map[loc.getRow()-1][loc.getCol()]=square.getType();
        }

        if(pathNodes.containsKey(matrix2String(map))) return false;
        return true;

    }

    public boolean downMoveable(Square square,ArrayList<SquareLocation> bounds,int[][] matrix){

        int[][] map=copyArrays(matrix);
        for(SquareLocation b:bounds){
            if((b.getRow()+1)>Kol.MATRIX_ROW-1) return false;
            if(map[b.getRow()+1][b.getCol()]!=0) return false;
        }

        for(SquareLocation loc:square.getLocs()){
            map[loc.getRow()][loc.getCol()]=0;
        }
        for(SquareLocation loc:square.getLocs()){
            map[loc.getRow()+1][loc.getCol()]=square.getType();
        }

        if(pathNodes.containsKey(matrix2String(map))) return false;

        return true;

    }

    public boolean rightMoveable(Square square,ArrayList<SquareLocation> bounds,int[][] matrix){

        int[][] map=copyArrays(matrix);
        for(SquareLocation b:bounds){
            if((b.getCol()+1)>Kol.MATRIX_COL-1) return false;
            if(map[b.getRow()][b.getCol()+1]!=0) return false;
        }

        for(SquareLocation loc:square.getLocs()){
            map[loc.getRow()][loc.getCol()]=0;
        }
        for(SquareLocation loc:square.getLocs()){
            map[loc.getRow()][loc.getCol()+1]=square.getType();
        }

        if(pathNodes.containsKey(matrix2String(map))) return false;


        return true;

    }

    public boolean leftMoveable(Square square,ArrayList<SquareLocation> bounds,int[][] matrix){

        int[][] map=copyArrays(matrix);
        for(SquareLocation b:bounds){
            if((b.getCol()-1)<0) return false;
            if(map[b.getRow()][b.getCol()-1]!=0) return false;
        }

        for(SquareLocation loc:square.getLocs()){
            map[loc.getRow()][loc.getCol()]=0;
        }
        for(SquareLocation loc:square.getLocs()){
            map[loc.getRow()][loc.getCol()-1]=square.getType();
        }

        if(pathNodes.containsKey(matrix2String(map))) return false;


        return true;

    }


    public void changeSquarLocation(Square square,int direct){
        for(SquareLocation loc:square.getLocs()){
            switch (direct) {
                case 0:
                    loc.setRow(loc.getRow()-1);
                    break;
                case 1:
                    loc.setCol(loc.getCol()+1);
                    break;
                case 2:
                    loc.setRow(loc.getRow()+1);
                    break;
                case 3:
                    loc.setCol(loc.getCol()-1);
                    break;
                default:
                    break;
            }
        }
    }


    private StringBuffer path=null;
    public void printPath(PathNode curr){
        //path=new StringBuffer();
        System.out.println("start");

        while(curr!=null&&curr!=root){
            //System.out.println("ID:"+curr.getMovedSquare().getId());
            //path.append("ID:"+curr.getMovedSquare().getId()+"  ");
            //System.out.println("DERICT:"+curr.getDirect());
            //path.append("DERICT:"+curr.getDirect()+"\t");

            printMatrix(curr.getCurrmatrix());
            System.out.println();
            curr=curr.getParent();
        }
        System.out.println("end");
    }

    public void outputPath(String filePath){
        File file=new File(filePath);
        try(FileOutputStream fos=new FileOutputStream(file)) {

            fos.write(path.toString().getBytes());

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    private void printPath(ArrayList<PathNode> currs){
        int deep=0;
        String d="";
        for(PathNode curr:currs){
            path=new StringBuffer();
            while(curr!=null&&curr!=root){
                System.out.println("ID:"+curr.getMovedSquare().getId());
                path.append("ID:"+curr.getMovedSquare().getId()+"  ");
                System.out.println("DERICT:"+curr.getDirect());
                path.append("DERICT:"+curr.getDirect()+"\t");
                deep++;
                curr=curr.getParent();
            }
            d+=Integer.toString(deep)+"\t";
            deep=0;
        }
        System.out.println(d);
    }
    class MoveInfo{

        public Square sq;
        public int d;
        public MoveInfo(Square sq,int d){
            this.sq=sq;
            this.d=d;
        }

    }
    public class Test{
        public int i;
        public Test(int i){
            this.i=i;
        }
    }
    private final static Kol kol=new Kol();
    public static void main(String[] args) {

        kol.init();
        kol.run();

        /**
         * test
         */
		/*
		ArrayList<SquareLocation> locs=new ArrayList<>();
		locs.add(new SquareLocation(1, 1));
		Square sq=new Square(11,Square._1X1,locs );

		Square sq1=sq.clone();
		sq1.setId(22);
		System.out.println(sq.getId());
		System.out.println(sq1.getId());
		*/

		/*
		Queue< Integer> q=new LinkedList<>();
		q.offer(1);
		q.offer(2);
		q.offer(3);
		System.out.println(q.peek());

		while(!q.isEmpty()){
			System.out.println(q.poll());
		}
		*/

        //kol.matrix=kol.fullMatrix(matrix);
        //Square s=kol.squares.get(5);
        //System.out.println(kol.squareMoveable(s, 2, kol.matrix));
        //System.out.println(kol.downMoveable(s, s.getBound(2),kol.matrix));
        /**
         * test_end
         */
        System.out.println("============================================");
        //System.out.println();
        kol.printPath(kol.endNodes.get(0));
        //kol.outputPath("D://path.txt");






        System.out.println("\nEnd\n");


    }

}
