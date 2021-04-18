package info6205.virus.simulation.map;

import info6205.virus.simulation.entity.RoadArea;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoadTest {

    @Test
    void findPath() {
        RoadArea roadArea=new RoadArea(1,2,2,1);
        RoadArea roadArea2=new RoadArea(0,2,1,1);
        RoadArea roadArea3=new RoadArea(2,2,3,1);
        RoadArea roadArea4=new RoadArea(1,3,2,2);
        RoadArea roadArea5=new RoadArea(2,3,3,2);
        RoadArea roadArea6=new RoadArea(3,3,4,2);
        roadArea.linkRodaArea(roadArea2).linkRodaArea(roadArea3).linkRodaArea(roadArea4);
        roadArea3.linkRodaArea(roadArea5);
        roadArea4.linkRodaArea(roadArea5);
        roadArea5.linkRodaArea(roadArea6);

        try {
            System.out.println(Road.findPath(roadArea2,roadArea6));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}