package info6205.virus.simulation.entity;

import info6205.virus.simulation.entity.building.BuildingBase;
import info6205.virus.simulation.map.GridElement;
import info6205.virus.simulation.map.SimulationMap;

import java.util.*;

public class RoadArea extends AreaBase{
    protected Set<RoadArea> adjacentRoad;

    @Override
    public void gridElementsInitial(List<List<GridElement>> area) {
        for(List<GridElement> gridElements:getArea()){
            for (GridElement gridElement:gridElements){
                gridElement.setWalkAble(true);
            }
        }
    }

    public RoadArea(double leftUpX, double leftUpY, double rightDownX, double rightDownY, SimulationMap map) {
        super(leftUpX, leftUpY, rightDownX, rightDownY,map);
        adjacentRoad=new HashSet<>();
        gridElementsInitial(getArea());
    }

    @Override
    public GridElement getRandomWalkableGridElement() {
        return getRandomGridElement();
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

    public RoadArea addAdjacentBuilding(BuildingBase buildingBase){
        adjacentRoad.add(buildingBase.getPublicArea());
        return this;
    }

    public RoadArea linkBuilding(BuildingBase buildingBase){
        addAdjacentRoadArea(buildingBase.getPublicArea());
        buildingBase.getPublicArea().addAdjacentRoadArea(this);
        return this;
    }

    public Direction getDirectionOfRoadArea(RoadArea roadArea){
        double desXLU=roadArea.getLeftUpX();
        double desYLU=roadArea.getLeftUpY();
        double desXRD=roadArea.getRightDownX();
        double desYRD=roadArea.getRightDownY();
        if(leftUpX<=desXLU&&rightDownX>=desXRD||leftUpX>=desXLU&&rightDownX<=desXRD){
            if(desYLU>leftUpY){
                return Direction.NORTH;
            }else {
                return Direction.SOUTH;
            }
        }else if(rightDownY<=desYRD&&leftUpY>=desYLU||rightDownY>=desYRD&&leftUpY<=desYLU){
            if(desXLU<leftUpX){
                return Direction.WEST;
            }else {
                return Direction.EAST;
            }
        }else{
            if(desXRD<=leftUpX&&desYRD>=leftUpY){
                return Direction.NORTHWEST;
            }else if(desXLU<=rightDownX&&desYLU>=rightDownY){
                return Direction.SOUTHEAST;
            }else if(desXLU>=rightDownX&&desYRD>=leftUpY){
                return Direction.NORTHEAST;
            }else {
                return Direction.SOUTHWEST;
            }
        }

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
