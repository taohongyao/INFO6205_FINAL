package info6205.virus.simulation.entity.building;

import info6205.virus.simulation.entity.Direction;
import info6205.virus.simulation.map.SimulationMap;

public class Apartment extends House{

    public Apartment(double leftUpX, double leftUpY, double high, double width, double roadWidth, SimulationMap map, Direction direction,int taskDuration) {
        super(leftUpX, leftUpY, high, width,roadWidth, map,direction,taskDuration);
//        taskTime=60*60*1;
    }
//    public Apartment(double leftUpX, double leftUpY, double high, double width, double roadWidth, SimulationMap map, Direction direction) {
//        super(leftUpX, leftUpY, leftUpX+width, leftUpY-high, map,direction,roadWidth);
//    }
}
