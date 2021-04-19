package info6205.virus.simulation.manager;

import info6205.virus.simulation.entity.*;
import info6205.virus.simulation.entity.building.*;
import info6205.virus.simulation.map.SimulationMap;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DemoGenerator extends EntityGenerator{
    public DemoGenerator(SimulationMap map) {
        super(map);
    }

    private static Logger logger=Logger.getLogger(DemoGenerator.class.getName());

    @Override
    public List<AreaBase> generateBuilding() {
        // Road generate
        List<RoadArea> list=new ArrayList<>();
        for(int i=0;i<16;i++){
            int xLU=0;
            int yLU=4;
            int roadWidth=2;
            int xRD=xLU+roadWidth;
            int yRD=yLU-roadWidth;
            RoadArea newRoad1=new RoadArea(xLU+i*roadWidth,yLU,xRD+i*roadWidth,yRD,map);
            list.add(newRoad1);
            if(i!=0){
                newRoad1.linkRodaArea(list.get(i-1));
            }
        }

        //House generate
        List<House> houses=new ArrayList<>();
        int i=0;
        for (RoadArea roadArea:list){
            if(i%2==0){
                int high=2;
                int width=4;
                int roadWidth=1;
                logger.log(Level.INFO,"Generate House:"+i);
                House house=new House(roadArea.getLeftUpX(),roadArea.getLeftUpY()+high,high,width,roadWidth,map, Direction.SOUTH);
                roadArea.linkBuilding(house);
                houses.add(house);
                house=new House(roadArea.getLeftUpX(),roadArea.getRightDownY(),high,width,roadWidth,map, Direction.NORTH);
                roadArea.linkBuilding(house);
                houses.add(house);
            }
            i++;
        }

        //Office generator
        List<Office> office = new ArrayList<>();

        Office office1 = new Office(0, 40, 8, 6,1, map,Direction.SOUTH);
        office.add(office1);

        Office office2 = new Office(26, 24, 6, 6,1, map,Direction.EAST);
        office.add(office2);

        Office office3 = new Office(52, 28, 6, 8,1, map,Direction.NORTH);
        office.add(office3);

        Office office4 = new Office(52, 4, 4, 8,1, map,Direction.NORTH);
        office.add(office4);


        //Park generator
        List<Park> park = new ArrayList<>();
        Park park1 = new Park(40, 40, 8, 20, 1, map, Direction.SOUTH);
        park.add(park1);

        //School generator
        List<School> school = new ArrayList<>();
        School school1 = new School(4, 24, 12, 8, 1, map, Direction.EAST);
        school.add(school1);

        //Restaurant generator
        List<Restaurant> restaurant = new ArrayList<>();
        Restaurant restaurant1 = new Restaurant(26, 16, 4, 6, 1, map, Direction.EAST);
        restaurant.add(restaurant1);

        Restaurant restaurant2 = new Restaurant(40, 28, 6, 12, 1, map, Direction.SOUTH);
        restaurant.add(restaurant2);

        //Mall generator
        List<Mall> mall = new ArrayList<>();
        Mall mall1 = new Mall(40, 18, 10, 20,1, map,Direction.NORTH);


        List<AreaBase> output=new ArrayList<>();
        output.addAll(list);
        output.addAll(houses);
        output.addAll(office);
        output.addAll(park);
        output.addAll(school);
        output.addAll(restaurant);
        output.addAll(mall);

        return output;
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
