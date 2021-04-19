package info6205.virus.simulation.entity.building;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.Direction;
import info6205.virus.simulation.map.SimulationMap;

public class Mall extends BuildingBase {
    public Mall(int i, int i1, int i2, int i3, int i4, SimulationMap map, Direction north) {
        super(leftUpX, leftUpY, leftUpX+high, leftUpY-with, map,direction,roadWidth);
    }
//    public Mall(double leftUpX, double leftUpY, double rightDownX, double rightDownY, SimulationMap map, Direction direction, double width) {
//        super(leftUpX, leftUpY, rightDownX, rightDownY, map,direction,width);
//    }
}
