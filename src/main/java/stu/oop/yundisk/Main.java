package stu.oop.yundisk;


import java.util.ArrayList;
import java.util.Scanner;

class GeographicInfo {
    double latitude;
    double longitude;
}


public class Main{
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        ArrayList<GeographicInfo> geographicInfoArrayList=new ArrayList<>();
        for (int i=0;i<2;i++){
            GeographicInfo g = new GeographicInfo();
            g.latitude=sc.nextDouble();
            g.longitude=sc.nextDouble();
            geographicInfoArrayList.add(g);
        }

        for (GeographicInfo g:geographicInfoArrayList){
            System.out.println("("+g.latitude+","+g.longitude+")");
        }
    }
}
