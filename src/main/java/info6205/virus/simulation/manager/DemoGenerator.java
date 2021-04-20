package info6205.virus.simulation.manager;

import info6205.virus.simulation.entity.*;
import info6205.virus.simulation.entity.building.*;
import info6205.virus.simulation.map.GridElement;
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
        for(int i=0;i<16;i++) {
            int xLU = 0; //road 1
            int yLU = 8;
            int roadWidth = 2;
            int roadHeight=2;
            int xRD = xLU + roadWidth;
            int yRD = yLU - roadWidth;
            RoadArea newRoad1 = new RoadArea(xLU + i * roadWidth, yLU, xRD + i * roadWidth, yRD-roadHeight, map); //(0,4)->(2,0)
            list.add(newRoad1);
            if (i != 0) {
                newRoad1.linkRodaArea(list.get(i - 1));
            }
        }

        for(int i=0;i<16;i++) {
            int xLU = 0;//road2
            int yLU = 32;
            int roadWidth=2;
            int roadHeight=2;
            int xRD=xLU+roadWidth;
            int yRD=yLU-roadWidth;
            RoadArea newRoad2 = new RoadArea(xLU + i * roadWidth, yLU, xRD + i * roadWidth, yRD-roadHeight, map); //(0,32)->(2,28)
            list.add(newRoad2);
            if (i != 0) {
                newRoad2.linkRodaArea(list.get(i - 1));
            }
        }

        for(int i=0;i<4;i++) {
            int xLU = 6; //road3
            int yLU = 40;
            int roadWidth=2;
            int xRD=xLU+4;
            int yRD=yLU-roadWidth;
            //int xRD2=xLU2+roadWidth;
            //int yRD2=yLU2-roadWidth;
            RoadArea newRoad3 = new RoadArea(xLU , yLU - i * roadWidth, xRD, yRD - i * roadWidth, map); //(6,40)->(10,38)
            list.add(newRoad3);
            if (i != 0) {
                newRoad3.linkRodaArea(list.get(i - 1));
            }
        }


        for(int i=0;i<10;i++) {
            int xLU = 40;//road4
            int yLU = 32;
            int roadWidth=2;
            int roadHeight=2;
            int xRD=xLU+roadWidth;
            int yRD=yLU-roadWidth;
            RoadArea newRoad4 = new RoadArea(xLU + i * roadWidth, yLU, xRD + i * roadWidth, yRD-roadHeight, map); //(40,32)->(42,28)
            list.add(newRoad4);
            if (i != 0) {
                newRoad4.linkRodaArea(list.get(i - 1));
            }
        }

        for (int i = 0; i < 10; i++) {
            int xLU = 40;//road5
            int yLU = 22;
            int roadWidth=2;
            int roadHeight=2;
            int xRD=xLU+roadWidth;
            int yRD=yLU-roadWidth;
            RoadArea newRoad5 = new RoadArea(xLU + i * roadWidth, yLU, xRD + i * roadWidth , yRD-roadHeight, map); //(40,22)->(42,18)
            list.add(newRoad5);
            if (i != 0) {
                newRoad5.linkRodaArea(list.get(i - 1));
            }
        }

        for (int i = 0; i < 10; i++) {
            int xLU = 40;//road6
            int yLU = 8;
            int roadWidth=2;
            int roadHeight=2;
            int xRD=xLU+roadWidth;
            int yRD=yLU-roadWidth;
            RoadArea newRoad6 = new RoadArea(xLU + i * roadWidth , yLU, xRD + i * roadWidth, yRD-roadHeight, map); //(40,8)->(42,4)
            list.add(newRoad6);
            if (i != 0) {
                newRoad6.linkRodaArea(list.get(i - 1));
            }
        }

        for(int i=0;i<20;i++) {
            int xLU = 32; //road7
            int yLU = 40;
            int roadWidth=2;
            int roadHeight=4;
            int xRD=xLU+4;
            int yRD=yLU-roadWidth;
            //int xRD2=xLU2+roadWidth;
            //int yRD2=yLU2-roadWidth;
            RoadArea newRoad7 = new RoadArea(xLU , yLU - i * roadWidth, xRD+roadHeight, yRD - i * roadWidth, map); //(6,40)->(10,38)
            list.add(newRoad7);
            if (i != 0) {
                newRoad7.linkRodaArea(list.get(i - 1));
            }
        }

        for(int i=0;i<2;i++) {
            int xLU = 6; //road8
            int yLU = 28;
            int roadWidth=2;
            int xRD=xLU+4;
            int yRD=yLU-roadWidth;
            //int xRD2=xLU2+roadWidth;
            //int yRD2=yLU2-roadWidth;
            RoadArea newRoad8 = new RoadArea(xLU , yLU - i * roadWidth, xRD, yRD - i * roadWidth, map); //(6,40)->(10,38)
            list.add(newRoad8);
            if (i != 0) {
                newRoad8.linkRodaArea(list.get(i - 1));
            }
        }

        for(int i=0;i<20;i++) {
            int xLU = 60; //road9
            int yLU = 40;
            int roadWidth=2;
            int roadHeight=4;
            int xRD=xLU+4;
            int yRD=yLU-roadWidth;
            RoadArea newRoad9 = new RoadArea(xLU , yLU - i * roadWidth, xRD+roadHeight, yRD - i * roadWidth, map); //(6,40)->(10,38)
            list.add(newRoad9);
            if (i != 0) {
                newRoad9.linkRodaArea(list.get(i - 1));
            }
        }

        for(int i=0;i<20;i++) {
            int xLU = 60; //road9
            int yLU = 40;
            int roadWidth=2;
            int roadHeight=4;
            int xRD=xLU+4;
            int yRD=yLU-roadWidth;
            RoadArea newRoad9 = new RoadArea(xLU , yLU - i * roadWidth, xRD+roadHeight, yRD - i * roadWidth, map); //(6,40)->(10,38)
            list.add(newRoad9);
            if (i != 0) {
                newRoad9.linkRodaArea(list.get(i - 1));
            }
        }

        // House generate
        List<House> houses = new ArrayList<>();
        for (int i=0;i<3;i++) {
            int xLU=0;
            int yLU=12;
            int high = 4;
            int width = 10;
            int roadWidth=1;
            House house = new House(xLU+i*width, yLU, high, width, roadWidth, map, Direction.SOUTH);
            houses.add(house);
        }
        for (int i=0;i<3;i++) {
            int xLU=0;
            int yLU=4;
            int high = 4;
            int width = 10;
            int roadWidth=1;
            House house = new House(xLU+i*width, yLU, high, width, roadWidth, map, Direction.NORTH);
            houses.add(house);
        }
        for (int i=0;i<2;i++) {
            int xLU=10;
            int yLU=36;
            int high = 4;
            int width = 10;
            int roadWidth=1;
            House house = new House(xLU+i*width, yLU, high, width, roadWidth, map, Direction.SOUTH);
            houses.add(house);
        }
        for (int i=0;i<2;i++) {
            int xLU=10;
            int yLU=28;
            int high = 4;
            int width = 10;
            int roadWidth=1;
            House house = new House(xLU+i*width, yLU, high, width, roadWidth, map, Direction.NORTH);
            houses.add(house);
        }

        //Apartment Generator
        for (int i=0;i<3;i++) {
            int xLU=68;
            int yLU=40;
            int high = 10;
            int width = 6;
            int roadWidth=1;
            Apartment apartment = new Apartment(xLU, yLU-i*(high+4), high, width, roadWidth, map, Direction.WEST);
            houses.add(apartment);
        }

        //Office generator
        List<Office> office = new ArrayList<>();

        Office office1 = new Office(0, 40, 8, 6, 1, map, Direction.SOUTH);
        office.add(office1);

        Office office2 = new Office(26, 24, 6, 6, 1, map, Direction.EAST);
        office.add(office2);

        Office office3 = new Office(52, 28, 6, 8, 1, map, Direction.NORTH);
        office.add(office3);

        Office office4 = new Office(52, 4, 4, 8, 1, map, Direction.NORTH);
        office.add(office4);


        //Park generator
        List<Park> park = new ArrayList<>();
        Park park1 = new Park(40, 40, 8, 20, 1, map, Direction.SOUTH);
        park.add(park1);

        //School generator
        List<School> school = new ArrayList<>();
        School school1 = new School(4, 24, 10, 18, 1, map, Direction.NORTH);
        school.add(school1);

        //Restaurant generator
        List<Restaurant> restaurant = new ArrayList<>();
        Restaurant restaurant1 = new Restaurant(26, 16, 4, 6, 1, map, Direction.EAST);
        restaurant.add(restaurant1);

        Restaurant restaurant2 = new Restaurant(40, 28, 6, 12, 1, map, Direction.NORTH);
        restaurant.add(restaurant2);

        //Mall generator
        List<Mall> mall = new ArrayList<>();
        Mall mall1 = new Mall(40, 18, 10, 20, 1, map, Direction.NORTH);
        mall.add(mall1);

        //Hospital generator
        List<Hospital> hospital = new ArrayList<>();
        Hospital hospital1 = new Hospital(40, 4, 4, 12, 1, map, Direction.NORTH);
        hospital.add(hospital1);

        //2->3
        linkRoadAreaBy2points(7,30,8,33);
        linkRoadAreaBy2points(9,30,8,33);
        //2->8
        linkRoadAreaBy2points(7,30,8,27);
        linkRoadAreaBy2points(9,30,8,27);
        //2->7
        linkRoadAreaBy2points(31,30,36,31);
        linkRoadAreaBy2points(31,30,36,29);
        //1->7
        linkRoadAreaBy2points(31,6,36,7);
        linkRoadAreaBy2points(31,6,36,5);
        //5->7
        linkRoadAreaBy2points(41,20,36,21);
        linkRoadAreaBy2points(41,20,36,19);
        //4->9
        linkRoadAreaBy2points(59,30,64,29);
        linkRoadAreaBy2points(59,30,64,31);
        //5->9
        linkRoadAreaBy2points(59,20,64,21);
        linkRoadAreaBy2points(59,20,64,19);
        //6->9
        linkRoadAreaBy2points(59,6,64,5);
        linkRoadAreaBy2points(59,6,64,7);


        List<AreaBase> output = new ArrayList<>();
        output.addAll(list);
        output.addAll(houses);
        output.addAll(office);
        output.addAll(park);
        output.addAll(school);
        output.addAll(restaurant);
        output.addAll(mall);
        output.addAll(hospital);

        return output;
    }

    private void linkRoadAreaBy2points(double x,double y,double x2,double y2){
        RoadArea elementRoadAreaA=null;
        RoadArea elementRoadAreaB=null;
        try {
            GridElement elementA=map.getGridElimentByXY(x,y);
            GridElement elementB=map.getGridElimentByXY(x2,y2);
            for(AreaBase areaBase:elementA.getAreas()){
                if(areaBase instanceof RoadArea){
                    elementRoadAreaA= (RoadArea) areaBase;
                }
            }
            for(AreaBase areaBase:elementB.getAreas()){
                if(areaBase instanceof RoadArea){
                    elementRoadAreaB= (RoadArea) areaBase;
                }
            }
            if(elementRoadAreaA==null||elementRoadAreaB==null) throw new Exception("Can't find roadArea");
            elementRoadAreaA.linkRodaArea(elementRoadAreaB);
        } catch (Exception e) {
            e.printStackTrace();
        }
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