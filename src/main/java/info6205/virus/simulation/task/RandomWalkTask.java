package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.map.GridElement;
import info6205.virus.simulation.map.SimulationMap;

import java.util.List;

public class RandomWalkTask extends MoveTask{

    protected int timeDuration;
    protected boolean infinity;

    public RandomWalkTask(double socialDistance, double speed, double keepSocialDistanceRate, int timeDuration) {
        super(socialDistance, speed, keepSocialDistanceRate);
        this.timeDuration = timeDuration;
        this.infinity=false;
    }

    public RandomWalkTask(double socialDistance, double speed, double keepSocialDistanceRate) {
        super(socialDistance, speed, keepSocialDistanceRate);
        this.infinity=true;
    }

    public RandomWalkTask(double socialDistance, double speed, Long walkSeed, double keepSocialDistanceRate, int timeDuration) {
        super(socialDistance, speed, walkSeed, keepSocialDistanceRate);
        this.timeDuration = timeDuration;
    }

    public RandomWalkTask(double socialDistance, double speed, Long walkSeed, double keepSocialDistanceRate) {
        super(socialDistance, speed, walkSeed, keepSocialDistanceRate);
        this.infinity=true;
    }

    @Override
    protected void executeTask(PeopleBase peopleBase) {
        SimulationMap map=peopleBase.getMap();
        int maxFindNextLocationTimes=5;
        try {
            while (maxFindNextLocationTimes!=0){
                double xOffset=speed;
                if(getRandom().nextBoolean()){
                    xOffset=-xOffset;
                }
                double yOffset=speed;
                if(getRandom().nextBoolean()){
                    yOffset=-yOffset;
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
        // minus duration
        if(!infinity){
            timeDuration--;
            if(timeDuration==0) finish();
        }
    }
}
