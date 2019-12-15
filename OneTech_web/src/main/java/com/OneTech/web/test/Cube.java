package com.OneTech.web.test;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cube {
    long operationTimes=0;
    Random rdm = new Random();
    String w[][] = {{"white","white","white"},{"white","white","white"},{"white","white","white"},{"white","white","white"}};
    String y[][] = {{"yellow","yellow","yellow"},{"yellow","yellow","yellow"},{"yellow","yellow","yellow"},{"yellow","yellow","yellow"}};
    String b[][] = {{"blue","blue","blue"},{"blue","blue","blue"},{"blue","blue","blue"},{"blue","blue","blue"}};
    String g[][] = {{"green","green","green"},{"green","green","green"},{"green","green","green"},{"green","green","green"}};
    String r[][] = {{"red","red","red"},{"red","red","red"},{"red","red","red"},{"red","red","red"}};
    String o[][] = {{"orange","orange","orange"},{"orange","orange","orange"},{"orange","orange","orange"},{"orange","orange","orange"}};
    String cube[][][] = {
            w,y,b,g,r,o
    };
    public void wF(int n){
        String tmp1[] = {"tmp","tmp","tmp"};
        String tmp2[] = {"t","t","t"};
        for(int j=0;j<n;j++){
            tmp1 = w[0];
            tmp2 = r[0];
            for(int i=1;i<w.length;i++){
                w[i-1] = w[i];
            }
            w[w.length-1] = tmp1;
            r[0] = b[1];
            b[1] = o[2];
            o[2] = g[3];
            g[3] = tmp2;
        }
    }
    public void wB(int n){
        if(n==4){
            return;
        }else if(n==3){
            n = 1;
        }else if(n == 2){
            n =2;
        }else if(n == 1){
            n =3;
        }
        String tmp1[] = {"tmp","tmp","tmp"};
        String tmp2[] = {"t","t","t"};
        for(int j=0;j<n;j++){
            tmp1 = w[0];
            tmp2 = r[0];
            for(int i=1;i<w.length;i++){
                w[i-1] = w[i];
            }
            w[w.length-1] = tmp1;
            r[0] = b[1];
            b[1] = o[2];
            o[2] = g[3];
            g[3] = tmp2;
        }
    }
    //其他几个颜色转动的方法还没有补充。有兴趣可以自己补充。
    public void yF(int n){

    }
    public void yB(int n){

    }

    public void bF(int n){

    }

    public void bB(int n){

    }

    public void gF(int n){

    }

    public void gB(int n){

    }
    public void rF(int n){

    }

    public void rB(int n){

    }

    public void oF(int n){

    }

    public void oB(int n){

    }

    public boolean isSixFaceRecover(){
        for(int i=0;i<cube.length;i++){
            for(int j=0;j<cube[i].length;j++){
                for(int k=0;k<cube[i][j].length;k++){
                    System.out.println(cube[i][j][k]+"->"+cube[i][0][0]);
                    try{
                        if(cube[i][j][k]!=cube[i][0][0]){
                            System.out.println("cube[i][j][k]:"+i+","+j+","+k+"与第一个颜色不一致");
                            return false;
                        }
                    }catch(ArrayIndexOutOfBoundsException e){
                        System.out.println("cube[i][j][k]:"+i+","+j+","+k);
                        e.printStackTrace();

                    }
                }
            }
        }
        return true;
    }
    public void log(long i,int j, int k){
        System.out.println("第"+i+"次，动作是"+j+"，执行"+k+"次");
    }
    public void showCube(){
        for(int i=0;i<cube.length;i++){
            for(int j=0;j<cube[i].length;j++){
                for(int k=0;k<cube[i][j].length;k++){
                    System.out.print(cube[i][j][k]);
                    System.out.print(",");
                }
                System.out.println();
            }

            System.out.println();
        }
    }
    public void rangePlay(){
        while(!isSixFaceRecover()){
            int rMethod = rdm.nextInt(12);
            int rTurn = rdm.nextInt(4);
            if(rMethod==0){
                this.wF(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==1){
                this.wB(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==2){
                this.yF(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==3){
                this.yB(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==4){
                this.bF(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==5){
                this.bB(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==6){
                this.gF(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==7){
                this.gB(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }
            else if(rMethod==8){
                this.rF(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==9){
                this.rB(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==10){
                this.oF(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==11){
                this.oB(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }
        }
        showCube();
        System.out.println("cube经过"+operationTimes+"次操作后复原");
    }
    public void rangePlay(int i){
        for(int m=0;m<i;m++){
            int rMethod = rdm.nextInt(12);
            int rTurn = rdm.nextInt(4);
            if(rMethod==0){
                this.wF(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==1){
                this.wB(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==2){
                this.yF(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==3){
                this.yB(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==4){
                this.bF(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==5){
                this.bB(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==6){
                this.gF(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==7){
                this.gB(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }
            else if(rMethod==8){
                this.rF(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==9){
                this.rB(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==10){
                this.oF(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }else if(rMethod==11){
                this.oB(rTurn);
                operationTimes++;
                log(operationTimes,rMethod,rTurn);
            }
        }
        System.out.println("cube已经被操作"+i+"次");
    }
    public static void main(String[] args){
        Cube c = new Cube();
        c.rangePlay(20);
        c.showCube();
        c.rangePlay();
    }
}


