package info6205.virus.simulation.entity;

import info6205.virus.simulation.map.GridElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AreaBase {
    protected String id;
    protected List<List<GridElement>> area;
    protected double leftUpX;
    protected double leftUpY;
    protected double rightDownX;
    protected double rightDownY;

    public AreaBase(double leftUpX, double leftUpY, double rightDownX, double rightDownY) {
        this.leftUpX = leftUpX;
        this.leftUpY = leftUpY;
        this.rightDownX = rightDownX;
        this.rightDownY = rightDownY;
        area=new ArrayList<>();
        id= UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<List<GridElement>> getArea() {
        return new ArrayList<>(area);
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