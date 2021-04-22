package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.Direction;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.entity.building.BuildingBase;
import info6205.virus.simulation.manager.AreaManger;
import info6205.virus.simulation.map.GridElement;
import info6205.virus.simulation.map.SimulationMap;
import info6205.virus.simulation.util.GridElementUtil;
import info6205.virus.simulation.util.RoadAreaUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class MoveInRoadTask extends MoveTask{
    protected BuildingBase buildingBase;
    protected Queue<RoadArea> path;
    protected int timesInSameArea;
    protected AreaManger areaManger;
    protected boolean start=true;

    public MoveInRoadTask(double speed,BuildingBase des,AreaManger areaManger) {
//        super(0, speed, 0);
        this.buildingBase=des;
        this.areaManger=areaManger;
    }

    public MoveInRoadTask(double socialDistance, double speed, double keepSocialDistanceRate,BuildingBase des,AreaManger areaManger) {
//        super(socialDistance, speed, keepSocialDistanceRate);
        this.buildingBase=des;
        this.areaManger=areaManger;
    }

    public MoveInRoadTask(double socialDistance, double speed, Long walkSeed, double keepSocialDistanceRate,BuildingBase des,AreaManger areaManger) {
//        super(socialDistance, speed, walkSeed, keepSocialDistanceRate);
        this.buildingBase=des;
        this.areaManger=areaManger;
    }

    public Queue<RoadArea> getPath() {
        return path;
    }

    public void setPath(Queue<RoadArea> path) {
        this.path = path;
    }

    //    private BuildingBase getCurrentBuildingBase(PeopleBase peopleBase){
//        GridElement gridElement=peopleBase.getLocation();
//        for (AreaBase areaBase:gridElement.getAreas()){
//            if(areaBase instanceof BuildingBase){
//                return (BuildingBase) areaBase;
//            }
//        }
//        return null;
//    }
//
    private RoadArea getPeopleRoadArea(PeopleBase peopleBase){
        GridElement currentLocation=peopleBase.getLocation();
        for(AreaBase areaBase:currentLocation.getAreas()){
            if(areaBase instanceof RoadArea){
                return (RoadArea) areaBase;
            }
        }
        return null;
    }

    public void findPath(PeopleBase peopleBase) throws Exception {
//        BuildingBase buildingBase=getCurrentBuildingBase(peopleBase);
//        if(buildingBase!=null){
//            // leave to roadArea
//            GridElement location=buildingBase.getPublicArea().getRandomGridElement();
//            try {
//                peopleBase.moveToNextLocation(location);
//                buildingBase.decreaseSizeByOne();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        RoadArea src=getPeopleRoadArea(peopleBase);
        if(src!=null){
                path= (Queue<RoadArea>) RoadAreaUtil.findPath(src,buildingBase.getPublicArea());

        }else {
            throw new RuntimeException("Not in the walkable place");
        }
    }
    public boolean moveInNotDesOrSrcArea(GridElement nexLocation,PeopleBase peopleBase){
        RoadArea area=getPeopleRoadArea(peopleBase);
        RoadArea des=path.peek();
        RoadArea next=RoadAreaUtil.getRoadAreaByGrid(nexLocation);
        if(next!=null&&(next.equals(area)||next.equals(des))){
            return true;
        }
        return false;
    }

    @Override
    public void executeTask(PeopleBase peopleBase) {
        if(start){
            try {
                findPath(peopleBase);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            start=false;
        }
        timesInSameArea++;
        RoadArea des=path.peek();
        if(peopleBase.getLocation().isArea(des)){
            path.poll();
            des=path.peek();
            timesInSameArea=0;
            if(des==null){
                finish();
                return;
            }
        }
        if(timesInSameArea>60*3){
            reScheduleTask(peopleBase);
            finish();
            return;
        }

        SimulationMap map=peopleBase.getMap();
        int maxFindNextLocationTimes=10;
        boolean avoidTooClose=false;
        double speedScale=1;
        int walkInForbidTimes=0;
        RoadAreaUtil.getRoadAreaByGrid(peopleBase.getLocation());
        while (maxFindNextLocationTimes!=0){
            Double nextX;
            Double nextY;

            if(!avoidTooClose){
                List<Double> xy=generateNextXY(peopleBase,path.peek(),speedScale);
                nextX=xy.get(0);
                nextY=xy.get(1);
            }else {
                List<Double> xy=avoidTooClose(peopleBase,path.peek(),speedScale);
                nextX=xy.get(0);
                nextY=xy.get(1);
                avoidTooClose=false;
            }
            GridElement gridElement=null;
            try {
                gridElement=map.getGridElimentByXY(nextX,nextY);
            } catch (Exception e) {
//                e.printStackTrace();
                continue;
            }
            if(gridElement.isWalkAble()){
                // since the generateNextXY policy which need to just go through N,S,W,E direction.
                if(!moveInNotDesOrSrcArea(gridElement,peopleBase)){
                    walkInForbidTimes++;
                    if(walkInForbidTimes>5){
                        speedScale=speedScale*0.8;
                    }
                    continue;
                }
                // Simulate keeping social Distance Rate
                if(getRandom().nextDouble()<peopleBase.getKeepSocialDistanceRate()){
                    if(GridElementUtil.isKeepSocialDistance(gridElement,peopleBase,peopleBase.getSocialDistance())){
                        peopleBase.moveToNextLocation(nextX,nextY);
                        break;
                    }else {
                        if(maxFindNextLocationTimes<0){
                            peopleBase.moveToNextLocation(nextX,nextY);
                            break;
                        }
                        avoidTooClose=true;
                    }
                }else {
                    peopleBase.moveToNextLocation(nextX,nextY);
                    break;
                }
            }
            maxFindNextLocationTimes--;
        }
    }

    public void reScheduleTask(PeopleBase peopleBase){
        peopleBase.cleanAllTasks();
        peopleBase.addTask(new TasksGenerateTask(areaManger));
    }

    private List<Double> avoidTooClose(PeopleBase peopleBase,RoadArea des,double speedScale){
        double xOffset=0;
        double yOffset=0;
        List<Double> coordination=new ArrayList<>();
        RoadArea currentArea= peopleBase.getCurrentRoadArea();
        Direction direction=currentArea.getDirectionOfRoadArea(des);
        // random walk when go to the destination
        if(getRandom().nextBoolean()){
            xOffset=peopleBase.getWalkSpeed()*2;
            if(getRandom().nextBoolean()){
                xOffset=-xOffset;
            }
        }else {
            yOffset=peopleBase.getWalkSpeed()*2;
            if(getRandom().nextBoolean()){
                yOffset=-yOffset;
            }
        }
        xOffset=xOffset*speedScale;
        yOffset=yOffset*speedScale;
        double nextX=peopleBase.getX()+xOffset;
        double nextY=peopleBase.getY()+yOffset;
        coordination.add(nextX);
        coordination.add(nextY);
        return coordination;
    }

    private List<Double> generateNextXY(PeopleBase peopleBase,RoadArea des,double speedScale){
        double xOffset=0;
        double yOffset=0;
        List<Double> coordination=new ArrayList<>();
        RoadArea currentArea= peopleBase.getCurrentRoadArea();
        Direction direction=currentArea.getDirectionOfRoadArea(des);
        // random walk when go to the destination
        switch (direction){
            case NORTH:
                yOffset=peopleBase.getRandomSpeed();
                break;
            case SOUTH:
                yOffset=-peopleBase.getRandomSpeed();
                break;
            case WEST:
                xOffset=-peopleBase.getRandomSpeed();
                break;
            case EAST:
                xOffset=peopleBase.getRandomSpeed();
                break;
            default:
        }
        double randomRate=0.5;
        if(direction==Direction.NORTH||direction==Direction.SOUTH){
            if(peopleBase.getLocation().getRealX()>=des.getRightDownX()) {
                xOffset=-peopleBase.getRandomSpeed();
                yOffset=0;
            }else if(peopleBase.getLocation().getRealX()<=des.getLeftUpX()){
                xOffset=peopleBase.getRandomSpeed();
                yOffset=0;
            }else {
                xOffset=peopleBase.getWalkSpeed()*randomRate*getRandom().nextDouble();
                if(getRandom().nextBoolean()){
                    xOffset=-xOffset;
                }
            }
        }else {
            if(peopleBase.getLocation().getRealY()>=des.getLeftUpY()) {
                yOffset=-peopleBase.getRandomSpeed();
                xOffset=0;
            }else if(peopleBase.getLocation().getRealY()<=des.getRightDownY()){
                yOffset=peopleBase.getRandomSpeed();
                xOffset=0;
            }else {
                yOffset=peopleBase.getWalkSpeed()*randomRate*getRandom().nextDouble();
                if(getRandom().nextBoolean()){
                    yOffset=-yOffset;
                }
            }
        }
        xOffset=xOffset*speedScale;
        yOffset=yOffset*speedScale;
        double nextX=peopleBase.getX()+xOffset;
        double nextY=peopleBase.getY()+yOffset;
        coordination.add(nextX);
        coordination.add(nextY);
        return coordination;
    }
}
