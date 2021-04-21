package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.logger.Log;
import info6205.virus.simulation.map.GridElement;
import info6205.virus.simulation.map.SimulationMap;
import info6205.virus.simulation.util.GridElementUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RandomWalkTask extends MoveTask{

    protected int timeDuration;
    protected boolean infinity;
    private static Logger logger=Logger.getLogger(RandomWalkTask.class.getName());
    static {
        logger.setLevel(Level.INFO);
    }

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
    public void executeTask(PeopleBase peopleBase) {
        SimulationMap map=peopleBase.getMap();
        int maxFindNextLocationTimes=5;

        boolean notWayToWalk=false;
//        while (maxFindNextLocationTimes!=0){
        while (true){
            Double nextX = null,nextY=null;
            if(!notWayToWalk){
                List<Double> xy=getNextXY(peopleBase);
                nextX=xy.get(0);
                nextY=xy.get(1);
            }else {
                List<Double> xy=getNextXYWithRun(peopleBase);
                nextX=xy.get(0);
                nextY=xy.get(1);
            }

            GridElement gridElement=null;
            try {
                gridElement=map.getGridElimentByXY(nextX,nextY);
            } catch (Exception e) {
                continue;
            }
            if(!GridElementUtil.isConnected(peopleBase.getLocation(),gridElement)){
                continue;
            }

            if(gridElement.isWalkAble()){
                // Simulate keeping social Distance Rate
                if(getRandom().nextDouble()<getKeepSocialDistanceRate()){
                    if(GridElementUtil.isKeepSocialDistance(gridElement,peopleBase,peopleBase.getSocialDistance())){
                        peopleBase.moveToNextLocation(nextX,nextY);
                        break;
                    }
                }else {
                    peopleBase.moveToNextLocation(nextX,nextY);
                    break;
                }
            }
            maxFindNextLocationTimes--;
            if(maxFindNextLocationTimes<-20){
                setKeepSocialDistanceRate(getKeepSocialDistanceRate()*0.7);
                notWayToWalk=true;
            }
        }

        if(maxFindNextLocationTimes==0){
            logger.log(Log.DEBUG,"can't find next point to move.");
        }

        // minus duration
        if(!infinity){
            timeDuration--;
            if(timeDuration<=0) finish();
        }
    }

    private List<Double> getNextXYWithRun(PeopleBase peopleBase){
        double xOffset=0;
        double yOffset=0;
        if(getRandom().nextBoolean()){
            xOffset=getSpeed()*2;
            if(getRandom().nextBoolean()){
                xOffset=-xOffset;
            }
        }else {
            yOffset=getSpeed()*2;
            if(getRandom().nextBoolean()){
                yOffset=-yOffset;
            }
        }
        double nextX=peopleBase.getX()+xOffset;
        double nextY=peopleBase.getY()+yOffset;
        List<Double> xy=new ArrayList<>();
        xy.add(nextX);
        xy.add(nextY);
        return xy;
    }

    private List<Double> getNextXY(PeopleBase peopleBase){
        double xOffset=0;
        double yOffset=0;
        if(getRandom().nextBoolean()){
            xOffset=getRandomSpeed();
            if(getRandom().nextBoolean()){
                xOffset=-xOffset;
            }
        }else {
            yOffset=getRandomSpeed();
            if(getRandom().nextBoolean()){
                yOffset=-yOffset;
            }
        }

        double nextX=peopleBase.getX()+xOffset;
        double nextY=peopleBase.getY()+yOffset;
        List<Double> xy=new ArrayList<>();
        xy.add(nextX);
        xy.add(nextY);
        return xy;
    }
}
