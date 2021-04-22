package info6205.virus.simulation.manager;

import info6205.virus.simulation.entity.Direction;
import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.entity.building.House;
import info6205.virus.simulation.map.SimulationMap;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntityGeneratorTest {
    private SimulationMap map;

    @Test
    void generateBuilding() {

        // Road generate
        List<RoadArea> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            int xLU=10;
            int yLU=10;
            int roadWidth=5;
            int xRD=xLU+roadWidth;
            int yRD=yLU-roadWidth;
            RoadArea newRoad=new RoadArea(xLU+i*roadWidth,yLU,xRD+i*roadWidth,yRD,map);
            list.add(newRoad);
            if(i!=0){
                newRoad.linkRodaArea(list.get(i-1));
            }
        }

        //House generate

        List<House> houses=new ArrayList<>();
        int i=0;
        for (RoadArea roadArea:list){
            if(i%2==0){
                int high=5;
                int width=10;
                int roadWidth=1;
                House house=new House(roadArea.getLeftUpX(),roadArea.getLeftUpY(),high,width,roadWidth,map, Direction.SOUTH,60*60*3);
                roadArea.linkBuilding(house);
                houses.add(house);
                house=new House(roadArea.getLeftUpX(),roadArea.getRightDownY(),high,width,roadWidth,map, Direction.NORTH,60*60*3);
                roadArea.linkBuilding(house);
                houses.add(house);
            }
            i++;
        }


    }

    @Test
    void generatePeople() {
    }

    @Test
    void generateVirus() {
    }
}