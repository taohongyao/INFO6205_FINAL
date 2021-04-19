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
            int xLU=0; //road 1
            int yLU=4;
            int roadWidth=2;
            int xRD=xLU+roadWidth;
            int yRD=yLU-roadWidth;
            RoadArea newRoad1=new RoadArea(xLU+i*roadWidth,yLU,xRD-i*roadWidth*2,yRD,map); //(0,4)->(2,0)
            list.add(newRoad1);
            if(i!=0){
                newRoad1.linkRodaArea(list.get(i-1));
            }

        for(int j=0;j<16;j++){
            int xLU1=0;//road2
            int yLU1=32;
             /*
             int roadWidth2=2;
             int xRD1=xLU1+roadWidth;
             int yRD1=yLU1-roadWidth;
             */
            RoadArea newRoad2=new RoadArea(xLU1+i*roadWidth,yLU1,xRD-i*roadWidth*2,yRD,map); //(0,32)->(2,28)
             list.add(newRoad2);
             if(i!=0){
                 newRoad2.linkRodaArea(list.get(i-1));
             }

        for(int k=0;k<4;k++){
             int xLU2=6; //road3
             int yLU2=40;
             //int roadWidth2=2;
             //int xRD2=xLU2+roadWidth;
             //int yRD2=yLU2-roadWidth;
             RoadArea newRoad3=new RoadArea(xLU2+i*roadWidth*2,yLU2,xRD-i*roadWidth,yRD,map); //(6,40)->(10,38)
             list.add(newRoad3);
             if(i!=0){
                 newRoad3.linkRodaArea(list.get(i-1));
             }


        for(int l=0;l<4;l++){
            int xLU3=40;//road4
            int yLU3=32;
                //int roadWidth2=2;
                //int xRD2=xLU2+roadWidth;
                //int yRD2=yLU2-roadWidth;
            RoadArea newRoad4=new RoadArea(xLU3+i*roadWidth,yLU3,xRD-i*roadWidth*2,yRD,map); //(40,32)->(42,28)
            list.add(newRoad4);
            if(i!=0){
                newRoad4.linkRodaArea(list.get(i-1));
            }

        for(int m=0;m<4;m++){
             int xLU5=40;//road5
             int yLU5=22;
             RoadArea newRoad5=new RoadArea(xLU5+i*roadWidth,yLU5,xRD-i*roadWidth*2,yRD,map); //(40,22)->(42,18)
             list.add(newRoad5);
             if(i!=0){
                newRoad5.linkRodaArea(list.get(i-1));
             }

        for(int n=0;n<4;n++){
             int xLU6=40;//road6
             int yLU6=8;
             RoadArea newRoad6=new RoadArea(xLU6+i*roadWidth,yLU6,xRD-i*roadWidth*2,yRD,map); //(40,8)->(42,4)
             list.add(newRoad6);
             if(i!=0){
                 newRoad6.linkRodaArea(list.get(i-1));
             }

        for(int o=0;o<4;o++){
             int xLU7=32; //road7
             int yLU7=40;
            int roadWidth2=4;
             RoadArea newRoad7=new RoadArea(xLU7+i*roadWidth*2,yLU7,xRD-i*roadWidth*0.5,yRD,map); //(32,40)->(40,38)
             list.add(newRoad7);
             if(i!=0){
                newRoad7.linkRodaArea(list.get(i-1));
             }
        }

        //House generate
        List<House> houses=new ArrayList<>();
        int i=0;
        for (RoadArea roadArea:list){
            if(i%2==0){
                int high=2;
                int width=4;
                roadWidth=1;
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
