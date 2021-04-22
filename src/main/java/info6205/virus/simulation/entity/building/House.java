package info6205.virus.simulation.entity.building;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.Direction;
import info6205.virus.simulation.map.SimulationMap;

public class House extends BuildingBase {

    public House(double leftUpX, double leftUpY,double high,double width, double roadWidth,SimulationMap map, Direction direction,int taskDuration) {
        super(leftUpX, leftUpY, leftUpX+width, leftUpY-high, map,direction,roadWidth,taskDuration);
//        taskTime=60*60*1;
    }

//    public House(double leftUpX, double leftUpY, double rightDownX, double rightDownY, SimulationMap map, Direction direction, double width) {
//        super(leftUpX, leftUpY, rightDownX, rightDownY, map,direction,width);
//    }
}
