package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.Direction;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.map.GridElement;
import info6205.virus.simulation.map.SimulationMap;

import java.util.List;
import java.util.Queue;

public class MoveInRoadTask extends MoveTask{
    protected Queue<RoadArea> path;

    public MoveInRoadTask(double socialDistance, double speed, double keepSocialDistanceRate, Queue<RoadArea> path) {
        super(socialDistance, speed, keepSocialDistanceRate);
        this.path = path;
    }

    public MoveInRoadTask(double socialDistance, double speed, Long walkSeed, double keepSocialDistanceRate, Queue<RoadArea> path) {
        super(socialDistance, speed, walkSeed, keepSocialDistanceRate);
        this.path = path;
    }

    public Queue<RoadArea> getPath() {
        return path;
    }

    public void setPath(Queue<RoadArea> path) {
        this.path = path;
    }

    @Override
    protected void executeTask(PeopleBase peopleBase) {
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
                double randomRate=0.5;
                if(direction==Direction.NORTH||direction==Direction.SOUTH){
                    if(peopleBase.getX()>des.getRightDownX()) {
                        xOffset=-speed*randomRate*getRandom().nextDouble();
                    }else if(peopleBase.getX()<des.getLeftUpX()){
                        xOffset=speed*randomRate*getRandom().nextDouble();
                    }else {
                        xOffset=speed*randomRate*getRandom().nextDouble();
                        if(getRandom().nextBoolean()){
                            xOffset=-xOffset;
                        }
                    }
                }else {
                    if(peopleBase.getY()>des.getLeftUpY()) {
                        yOffset=-speed*randomRate*getRandom().nextDouble();
                    }else if(peopleBase.getY()<des.getRightDownY()){
                        yOffset=speed*randomRate*getRandom().nextDouble();
                    }else {
                        yOffset=speed*randomRate*getRandom().nextDouble();
                        if(getRandom().nextBoolean()){
                            yOffset=-yOffset;
                        }
                    }
                }

                switch (direction){
                    case NORTH:
                        yOffset=speed;
                        break;
                    case SOUTH:
                        yOffset=-speed;
                        break;
                    case WEST:
                        xOffset=-speed;
                        break;
                    case EAST:
                        xOffset=speed;
                        break;
                    default:
                }

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
