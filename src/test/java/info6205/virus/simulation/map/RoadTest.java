package info6205.virus.simulation.map;

import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.util.RoadAreaUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoadTest {

    @Test
    void findPath() {
        SimulationMap map=new SimulationMap(10,10);
        RoadArea roadArea=new RoadArea(0,2,1,1,map);
        RoadArea roadArea1=new RoadArea(1,2,2,1,map);
        RoadArea roadArea2=new RoadArea(2,2,3,1,map);
        RoadArea roadArea3=new RoadArea(2,3,3,2,map);
        RoadArea roadArea4=new RoadArea(3,3,4,2,map);
        RoadArea roadArea5=new RoadArea(4,3,5,2,map);
        RoadArea roadArea6=new RoadArea(4,4,5,3,map);

        RoadArea roadArea7=new RoadArea(3,2,4,1,map);
        RoadArea roadArea8=new RoadArea(1,3,2,2,map);
        roadArea.linkRodaArea(roadArea1);
        roadArea1.linkRodaArea(roadArea2);
        roadArea7.linkRodaArea(roadArea2).linkRodaArea(roadArea4);
        roadArea2.linkRodaArea(roadArea3);
        roadArea3.linkRodaArea(roadArea4);
        roadArea4.linkRodaArea(roadArea5);
        roadArea5.linkRodaArea(roadArea6);
//        roadArea8.linkRodaArea(roadArea1).linkRodaArea(roadArea3);


        try {
            System.out.println("BFS Path:");
            List<RoadArea> path=RoadAreaUtil.findPath(roadArea,roadArea4);
            for (RoadArea i:path){
                System.out.println(i);
            }
            path=RoadAreaUtil.compactPath(path);
            System.out.println("Compact:"+path.size());
            for (RoadArea i:path){
                System.out.println(i);
            }


            assertTrue(path.size()!=0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void subTest(){
        List<Integer> a= Arrays.asList(1,2,3,4,5,6);
        a.subList(2,2).clear();
        System.out.println(a.size());
    }
}