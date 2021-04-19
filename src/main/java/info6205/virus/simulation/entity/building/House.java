package info6205.virus.simulation.entity.building;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.Direction;
import info6205.virus.simulation.map.SimulationMap;

public class House extends BuildingBase {

    public House(double leftUpX, double leftUpY,double high,double width, double roadWidth,SimulationMap map, Direction direction) {
        super(leftUpX, leftUpY, leftUpX+high, leftUpY-width, map,direction,roadWidth);
    }

    public House(double leftUpX, double leftUpY, double rightDownX, double rightDownY, SimulationMap map, Direction direction, double width) {
        super(leftUpX, leftUpY, rightDownX, rightDownY, map,direction,width);
    }
}
