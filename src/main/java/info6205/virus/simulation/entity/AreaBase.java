package info6205.virus.simulation.entity;

import info6205.virus.simulation.map.GridElement;
import info6205.virus.simulation.map.SimulationMap;

import java.util.*;

public abstract class AreaBase {
    protected String id;
    protected List<List<GridElement>> area;
    protected SimulationMap map;
    protected double leftUpX;
    protected double leftUpY;
    protected double rightDownX;
    protected double rightDownY;
    protected static Random random;

    public abstract void gridElementsInitial(List<List<GridElement>> area);

    public AreaBase(double leftUpX, double leftUpY, double rightDownX, double rightDownY,SimulationMap map) {
        this.leftUpX = leftUpX;
        this.leftUpY = leftUpY;
        this.rightDownX = rightDownX;
        this.rightDownY = rightDownY;
        this.map=map;
        area= map.getSubGridsAndBindArea(leftUpX,leftUpY,rightDownX,rightDownY,this);
        id= UUID.randomUUID().toString();
        gridElementsInitial(area);
        random= new Random();
    }

    public abstract GridElement getRandomWalkableGridElement();
    public GridElement getRandomGridElement(){
        double x=random.nextDouble()*(rightDownX-leftUpX)+leftUpX;
        double y=random.nextDouble()*(leftUpY-rightDownY)+rightDownY;
        GridElement element=null;
        try {
            element=map.getGridElimentByXY(x,y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return element;
    }

    public double getWidth(){
        return rightDownX-leftUpX;
    }
    public double getHight(){
        return leftUpY-rightDownY;
    }

    public String getId() {
        return id;
    }

    public double getCenterX(){
        return (leftUpX+rightDownX)/2;
    }

    public double getCenterY(){
        return (leftUpY+rightDownY)/2;
    }


    public void setId(String id) {
        this.id = id;
    }

    public List<List<GridElement>> getArea() {
        return area;
    }

    public void setArea(List<List<GridElement>> area) {
        this.area = area;
    }

    public double getLeftUpX() {
        return leftUpX;
    }

    public void setLeftUpX(double leftUpX) {
        this.leftUpX = leftUpX;
    }

    public double getLeftUpY() {
        return leftUpY;
    }

    public void setLeftUpY(double leftUpY) {
        this.leftUpY = leftUpY;
    }

    public double getRightDownX() {
        return rightDownX;
    }

    public void setRightDownX(double rightDownX) {
        this.rightDownX = rightDownX;
    }

    public double getRightDownY() {
        return rightDownY;
    }

    public void setRightDownY(double rightDownY) {
        this.rightDownY = rightDownY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaBase areaBase = (AreaBase) o;
        return Objects.equals(id, areaBase.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}