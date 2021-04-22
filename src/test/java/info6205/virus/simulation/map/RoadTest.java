package info6205.virus.simulation.map;

import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.util.RoadAreaUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoadTest {

    @Test
    void findPath() {
        SimulationMap map=new SimulationMap(10,10);
        RoadArea roadArea=new RoadArea(1,2,2,1,map);
        RoadArea roadArea2=new RoadArea(0,2,1,1,map);
        RoadArea roadArea3=new RoadArea(2,2,3,1,map);
        RoadArea roadArea4=new RoadArea(1,3,2,2,map);
        RoadArea roadArea5=new RoadArea(2,3,3,2,map);
        RoadArea roadArea6=new RoadArea(3,3,4,2,map);
        roadArea.linkRodaArea(roadArea2).linkRodaArea(roadArea3).linkRodaArea(roadArea4);
        roadArea3.linkRodaArea(roadArea5);
        roadArea4.linkRodaArea(roadArea5);
        roadArea5.linkRodaArea(roadArea6);


        try {
            System.out.println("BFS Path:");
            List<RoadArea> path=RoadAreaUtil.findPath(roadArea2,roadArea6);
            for (RoadArea roadArea1:path){
                System.out.println(roadArea1);
            }
            assertTrue(path.size()!=0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}