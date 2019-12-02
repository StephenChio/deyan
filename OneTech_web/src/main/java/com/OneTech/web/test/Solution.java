package com.OneTech.web.test;
import com.OneTech.common.util.pingyinUtils.FirstLetterUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Solution {
            public ArrayList<ArrayList<Integer> > FindContinuousSequence(int sum) {
                ArrayList<ArrayList<Integer>> finalList = new ArrayList();
                for (int i = 1; i < sum; i++) {
                    int s = 0;
                    ArrayList<Integer> list = new ArrayList();
                    for (int j = i; j < sum; j++) {
                        s = s + j;
                        list.add(j);
                        if (s == sum) {
                            System.out.println(list);
                            finalList.add(list);
                            break;
                        }
                        if(s>sum){
                            break;
                        }
                    }
                }
                return finalList;
            }
            public int rabbit(int month){
                if(month ==1 || month ==2){
                    return 2;
                }
                else{
                    return rabbit(month-1) +rabbit(month-2);
                }
            }
    public static void main(String arg[]){
        System.out.println(FirstLetterUtil.getFirstLetter("齋藤飛鳥"));
    }

}