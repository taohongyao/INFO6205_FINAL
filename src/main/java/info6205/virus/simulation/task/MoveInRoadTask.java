package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.Direction;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.entity.building.BuildingBase;
import info6205.virus.simulation.map.GridElement;
import info6205.virus.simulation.map.SimulationMap;
import info6205.virus.simulation.util.RoadAreaUtil;

import java.util.List;
import java.util.Queue;

public class MoveInRoadTask extends MoveTask{
    protected BuildingBase buildingBase;
    protected Queue<RoadArea> path;
    protected boolean start=true;

    public MoveInRoadTask(double speed,BuildingBase des) {
        super(0, speed, 0);
        this.buildingBase=des;
    }

    public MoveInRoadTask(double socialDistance, double speed, double keepSocialDistanceRate,BuildingBase des) {
        super(socialDistance, speed, keepSocialDistanceRate);
        this.buildingBase=des;
    }

    public MoveInRoadTask(double socialDistance, double speed, Long walkSeed, double keepSocialDistanceRate,BuildingBase des) {
        super(socialDistance, speed, walkSeed, keepSocialDistanceRate);
        this.buildingBase=des;
    }

    public Queue<RoadArea> getPath() {
        return path;
    }

    public void setPath(Queue<RoadArea> path) {
        this.path = path;
    }

    public double getRandomSpeed(){
        return speed*(1-0.2*getRandom().nextDouble());
    }

    private BuildingBase getCurrentBuildingBase(PeopleBase peopleBase){
        GridElement gridElement=peopleBase.getLocation();
        for (AreaBase areaBase:gridElement.getAreas()){
            if(areaBase instanceof BuildingBase){
                return (BuildingBase) areaBase;
            }
        }
        return null;
    }

    private RoadArea getPeopleRoadArea(PeopleBase peopleBase){
        GridElement currentLocation=peopleBase.getLocation();
        for(AreaBase areaBase:currentLocation.getAreas()){
            if(areaBase instanceof RoadArea){
                return (RoadArea) areaBase;
            }
        }
        return null;
    }

    public void findPath(PeopleBase peopleBase){
        BuildingBase buildingBase=getCurrentBuildingBase(peopleBase);
        if(buildingBase!=null){
            // leave to roadArea
            GridElement location=buildingBase.getPublicArea().getRandomGridElement();
            try {
                peopleBase.moveToNextLocation(location.getRealX(), location.getRealY());
                buildingBase.decreaseSizeByOne();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        RoadArea src=getPeopleRoadArea(peopleBase);
        if(src!=null){
            try {
                path= (Queue<RoadArea>) RoadAreaUtil.findPath(src,buildingBase.getPublicArea());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void executeTask(PeopleBase peopleBase) {
        if(start){
            findPath(peopleBase);
            start=false;
        }

        RoadArea des=path.peek();
        if(peopleBase.getLocation().isArea(des)){
            path.poll();
            des=path.peek();
            if(des==null){
                finish();
                return;
            }
        }

        SimulationMap map=peopleBase.getMap();

        try {
            int maxFindNextLocationTimes=5;
            while (maxFindNextLocationTimes!=0){

                RoadArea currentArea= peopleBase.getCurrentRoadArea();
                Direction direction=currentArea.getDirectionOfRoadArea(des);
                double xOffset=0;
                double yOffset=0;

                // random walk when go to the destination
                switch (direction){
                    case NORTH:
                        yOffset=getRandomSpeed();
                        break;
                    case SOUTH:
                        yOffset=-getRandomSpeed();
                        break;
                    case WEST:
                        xOffset=-getRandomSpeed();
                        break;
                    case EAST:
                        xOffset=getRandomSpeed();
                        break;
                    default:
                }
                double randomRate=0.5;
                if(direction==Direction.NORTH||direction==Direction.SOUTH){
                    if(peopleBase.getX()>des.getRightDownX()) {
                        xOffset=-getRandomSpeed();
                        yOffset=0;
                    }else if(peopleBase.getX()<des.getLeftUpX()){
                        xOffset=getRandomSpeed();
                        yOffset=0;
                    }else {
                        xOffset=speed*randomRate*getRandom().nextDouble();
                        if(getRandom().nextBoolean()){
                            xOffset=-xOffset;
                        }
                    }
                }else {
                    if(peopleBase.getY()>des.getLeftUpY()) {
                        yOffset=-getRandomSpeed();
                        xOffset=0;
                    }else if(peopleBase.getY()<des.getRightDownY()){
                        yOffset=getRandomSpeed();
                        xOffset=0;
                    }else {
                        yOffset=speed*randomRate*getRandom().nextDouble();
                        if(getRandom().nextBoolean()){
                            yOffset=-yOffset;
                        }

                    }
                }

                // TODO: 4/20/2021 SocialDistance Judge 
                double nextX=peopleBase.getX()+xOffset;
                double nextY=peopleBase.getY()+yOffset;
                GridElement gridElement=null;
                gridElement=map.getGridElimentByXY(nextX,nextY);
                if(gridElement.isWalkAble()){
                    // Simulate keeping social Distance Rate
                    if(getRandom().nextDouble()<getKeepSocialDistanceRate()){
                        double xLeftUp=gridElement.getRealX()-socialDistance;
                        double xRightDown=gridElement.getRealX()+socialDistance;
                        double yLeftUp=gridElement.getRealY()-socialDistance;
                        double yRightDown=gridElement.getRealY()+socialDistance;
                        List<List<GridElement>> subGrids=map.getSubGrids(xLeftUp,yLeftUp,xRightDown,yRightDown);
                        boolean havePeopleInSocialDistance=false;
                        for(List<GridElement> gridElements:subGrids){
                            for (GridElement gridElement1:gridElements){
                                if(gridElement1.havePeople()){
                                    havePeopleInSocialDistance=true;
                                    break;
                                }
                            }
                        }
                        if(!havePeopleInSocialDistance){
                            peopleBase.moveToNextLocation(nextX,nextY);
                            break;
                        }
                    }else {
                        peopleBase.moveToNextLocation(nextX,nextY);
                        break;
                    }
                }
                maxFindNextLocationTimes--;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
