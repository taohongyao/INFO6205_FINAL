package info6205.virus.simulation.manager;

import info6205.virus.simulation.entity.*;
import info6205.virus.simulation.entity.building.House;
import info6205.virus.simulation.map.SimulationMap;

import java.util.ArrayList;
import java.util.List;

public class DemoGenerator extends EntityGenerator{
    public DemoGenerator(SimulationMap map) {
        super(map);
    }

    @Override
    public List<AreaBase> generateBuilding() {
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
                House house=new House(roadArea.getLeftUpX(),roadArea.getLeftUpY(),high,width,roadWidth,map, Direction.SOUTH);
                roadArea.linkBuilding(house);
                houses.add(house);
                house=new House(roadArea.getLeftUpX(),roadArea.getRightDownY(),high,width,roadWidth,map, Direction.NORTH);
                roadArea.linkBuilding(house);
                houses.add(house);
            }
            i++;
        }



        return null;
    }

    @Override
    public List<PeopleBase> generatePeople(List<AreaBase> AreaBase) {
        return null;
    }

    @Override
    public List<VirusBase> generateVirus(List<PeopleBase> peopleBases) {
        return null;
    }
}
