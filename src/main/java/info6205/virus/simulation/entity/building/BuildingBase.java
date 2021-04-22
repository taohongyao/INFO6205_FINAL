package info6205.virus.simulation.entity.building;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.Direction;
import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.map.GridElement;
import info6205.virus.simulation.map.SimulationMap;

import java.util.List;

public class BuildingBase extends AreaBase {
    protected RoadArea publicArea;
    protected List<List<GridElement>> buildingPrivateArea;
    protected double publicAreaWidth;
    protected Direction direction;

    protected int capacity;
    protected int buildingSize;
    protected static double timeSpeedZoom=1;
    protected int taskTime=60*60*30;



    protected double leftUpXPublicArea;
    protected double leftUpYPublicArea;
    protected double rightDownXPublicArea;
    protected double rightDownYPublicArea;
    protected double leftUpXBuildingWall;
    protected double leftUpYBuildingWall;
    protected double rightDownXBuildingWall;
    protected double rightDownYBuildingWall;



    @Override
    public void gridElementsInitial(List<List<GridElement>> area) {
        publicArea=new RoadArea(leftUpXPublicArea,leftUpYPublicArea,rightDownXPublicArea,rightDownYPublicArea,map);
        buildingPrivateArea=map.getSubGrids(leftUpXBuildingWall,leftUpYBuildingWall,rightDownXBuildingWall,rightDownYBuildingWall);
        int high=buildingPrivateArea.size();
        int width=buildingPrivateArea.get(0).size();
        for (int i= 0;i<high;i++){
            //set building north and south not walkable
            if(i!=0&&i!=high-1){
                int j=0;
                for(GridElement gridElement:buildingPrivateArea.get(i)){
                    if (j!=0&&j!=width){
                        gridElement.setWalkAble(true);
                        gridElement.setConnectedId(getId());
                    }
                    j++;
                }
            }else {
                // set building left and right not walkable
                //buildingPrivateArea.get(i).get(0).setWalkAble(false);
                //buildingPrivateArea.get(i).get(width-1).setWalkAble(false);
            }
        }
    }

    @Override
    public GridElement getRandomWalkableGridElement() {
        int high=buildingPrivateArea.size();
        int width=buildingPrivateArea.get(0).size();
        int y=random.nextInt(high-2)+1;
        int x=random.nextInt(width-2)+1;
        return getBuildingPrivateArea().get(y).get(x);
    }

    public void initial(){
        leftUpXPublicArea=leftUpX;
        leftUpYPublicArea=leftUpY;
        rightDownXPublicArea=rightDownX;
        rightDownYPublicArea=rightDownY;

        leftUpXBuildingWall=leftUpX;
        leftUpYBuildingWall=leftUpY;
        rightDownXBuildingWall=rightDownX;
        rightDownYBuildingWall=rightDownY;
        switch (direction){
            case SOUTH:
                leftUpYPublicArea=rightDownYPublicArea+publicAreaWidth;
                rightDownYBuildingWall=leftUpYPublicArea;
                break;
            case NORTH:
                rightDownYPublicArea=leftUpYPublicArea-publicAreaWidth;
                leftUpYBuildingWall=rightDownYPublicArea;
                break;
            case WEST:
                rightDownXPublicArea=leftUpXPublicArea+publicAreaWidth;
                leftUpXBuildingWall=rightDownXPublicArea;
                break;
            case EAST:
                leftUpXPublicArea=rightDownXPublicArea-publicAreaWidth;
                rightDownXBuildingWall=leftUpXPublicArea;
                break;
            default:
        }
        gridElementsInitial(getArea());
    }

    public BuildingBase(double leftUpX, double leftUpY, double rightDownX, double rightDownY, SimulationMap map, Direction direction,double width, int taskTime) {
        super(leftUpX, leftUpY, rightDownX, rightDownY, map);
        this.direction=direction;
        this.publicAreaWidth=width;
        this.taskTime = taskTime;
        initial();
    }

//    public BuildingBase(double leftUpX, double leftUpY, double rightDownX, double rightDownY, SimulationMap map, Direction direction,double width) {
//        super(leftUpX, leftUpY, rightDownX, rightDownY, map);
//        this.direction=direction;
//        this.publicAreaWidth=width;
////        this.taskTime = taskTime;
//        initial();
//    }


    public double getPrivateWallWidth(){
        return rightDownXBuildingWall-leftUpXBuildingWall;
    }
    public double getPrivateWallHigh(){
        return leftUpYBuildingWall-rightDownYBuildingWall;
    }

    public double getPublicRoadWidth(){
        return rightDownXPublicArea-leftUpXPublicArea;
    }
    public double getPublicRoadHigh(){
        return leftUpYPublicArea-rightDownYPublicArea;
    }


    public static double getTimeSpeedZoom() {
        return timeSpeedZoom;
    }

    public static void setTimeSpeedZoom(double timeSpeedZoom) {
        BuildingBase.timeSpeedZoom = timeSpeedZoom;
    }

    public double getPublicAreaWidth() {
        return publicAreaWidth;
    }

    public void setPublicAreaWidth(double publicAreaWidth) {
        this.publicAreaWidth = publicAreaWidth;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public RoadArea getPublicArea() {
        return publicArea;
    }

    public void setPublicArea(RoadArea publicArea) {
        this.publicArea = publicArea;
    }

    public List<List<GridElement>> getBuildingPrivateArea() {
        return buildingPrivateArea;
    }

    public void setBuildingPrivateArea(List<List<GridElement>> buildingPrivateArea) {
        this.buildingPrivateArea = buildingPrivateArea;
    }

    public double getLeftUpXPublicArea() {
        return leftUpXPublicArea;
    }

    public void setLeftUpXPublicArea(double leftUpXPublicArea) {
        this.leftUpXPublicArea = leftUpXPublicArea;
    }

    public double getLeftUpYPublicArea() {
        return leftUpYPublicArea;
    }

    public void setLeftUpYPublicArea(double leftUpYPublicArea) {
        this.leftUpYPublicArea = leftUpYPublicArea;
    }

    public double getRightDownXPublicArea() {
        return rightDownXPublicArea;
    }

    public void setRightDownXPublicArea(double rightDownXPublicArea) {
        this.rightDownXPublicArea = rightDownXPublicArea;
    }

    public double getRightDownYPublicArea() {
        return rightDownYPublicArea;
    }

    public void setRightDownYPublicArea(double rightDownYPublicArea) {
        this.rightDownYPublicArea = rightDownYPublicArea;
    }

    public double getLeftUpXBuildingWall() {
        return leftUpXBuildingWall;
    }

    public void setLeftUpXBuildingWall(double leftUpXBuildingWall) {
        this.leftUpXBuildingWall = leftUpXBuildingWall;
    }

    public double getLeftUpYBuildingWall() {
        return leftUpYBuildingWall;
    }

    public void setLeftUpYBuildingWall(double leftUpYBuildingWall) {
        this.leftUpYBuildingWall = leftUpYBuildingWall;
    }

    public double getRightDownXBuildingWall() {
        return rightDownXBuildingWall;
    }

    public void setRightDownXBuildingWall(double rightDownXBuildingWall) {
        this.rightDownXBuildingWall = rightDownXBuildingWall;
    }

    public int getCapacity() {
        return capacity;
    }

    public void decreaseSizeByOne(){
        buildingSize--;
    }

    public void increaseSizeByOne(){
        buildingSize++;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getBuildingSize() {
        return buildingSize;
    }

    public void setBuildingSize(int buildingSize) {
        this.buildingSize = buildingSize;
    }

    public int getTaskTime() {
        return (int) (taskTime*timeSpeedZoom);
    }

    public void setTaskTime(int taskTime) {
        this.taskTime = taskTime;
    }

    public double getRightDownYBuildingWall() {
        return rightDownYBuildingWall;
    }

    public void setRightDownYBuildingWall(double rightDownYBuildingWall) {
        this.rightDownYBuildingWall = rightDownYBuildingWall;
    }
}
