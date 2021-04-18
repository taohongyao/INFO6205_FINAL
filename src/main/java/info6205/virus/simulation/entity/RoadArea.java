package info6205.virus.simulation.entity;

import java.util.*;

public class RoadArea extends AreaBase{
    protected Set<RoadArea> adjacentRoad;

    public RoadArea(double leftUpX, double leftUpY, double rightDownX, double rightDownY) {
        super(leftUpX, leftUpY, rightDownX, rightDownY);
        adjacentRoad=new HashSet<>();
    }

    public RoadArea addAdjacentRoadArea(RoadArea area){
        adjacentRoad.add(area);
        return this;
    }

    public RoadArea linkRodaArea(RoadArea area){
        addAdjacentRoadArea(area);
        area.addAdjacentRoadArea(this);
        return this;
    }


    public List<RoadArea> getAdjacentRoad() {
        return new ArrayList<>(adjacentRoad);
    }

    @Override
    public String toString() {
        return "RoadArea{" +
                "id='" + id + '\'' +
                ", leftUpX=" + leftUpX +
                ", leftUpY=" + leftUpY +
                ", rightDownX=" + rightDownX +
                ", rightDownY=" + rightDownY +
                '}';
    }
}
