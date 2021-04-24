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

    protected int initTimeDuration;
    protected int timeDuration;
    protected boolean infinity;
    protected boolean keepDistance;
    protected Long randomWalkSeed;
    protected double xOffset;
    protected double yOffset;
    protected boolean straitWalk;
    protected boolean start;
    protected int stopTime;

    private static Logger logger=Logger.getLogger(RandomWalkTask.class.getName());
    static {
        logger.setLevel(Level.INFO);
    }

    public RandomWalkTask(int timeDuration,boolean keepDistance,boolean straitWalk) {
        this.timeDuration = timeDuration;
        this.initTimeDuration=timeDuration;
        this.infinity=false;
        this.keepDistance=keepDistance;
        name="Random walking";
        this.straitWalk=straitWalk;
        start=true;
    }

    public RandomWalkTask(boolean keepDistance) {
//        super(socialDistance, speed, keepSocialDistanceRate);
        this.keepDistance=keepDistance;
        this.infinity=true;
    }

    public RandomWalkTask(Long walkSeed,int timeDuration,boolean keepDistance) {
        this.timeDuration = timeDuration;
        this.randomWalkSeed=walkSeed;
        this.keepDistance=keepDistance;
    }

    public RandomWalkTask(Long walkSeed,boolean keepDistance) {
//        super(socialDistance, speed, walkSeed, keepSocialDistanceRate);
        this.infinity=true;
        this.randomWalkSeed=walkSeed;
        this.keepDistance=keepDistance;
    }


    @Override
    public void executeTask(PeopleBase peopleBase) {
        double speedScale=1;
        if(start){
            updateStraitWalkDirection(peopleBase,speedScale);
            start=false;
        }

        SimulationMap map=peopleBase.getMap();
        int maxFindNextLocationTimes=5;
        int speedTooMaxTimes=5;

        boolean notWayToWalk=false;
        double distanceKeepRate=peopleBase.getKeepSocialDistanceRate();
//        while (maxFindNextLocationTimes!=0){

        int range=getRandom().nextInt(1000);

        if(range<998&&stopTime<=0){
            while (true){
                Double nextX = null,nextY=null;

                // if there is a way to walk due to social distance
//                straitWalk=false;
                if(straitWalk){
                    List<Double> xy=getNextXYWalkStrait(peopleBase,speedScale);
                    nextX=xy.get(0);
                    nextY=xy.get(1);
                }else if(!notWayToWalk){
                    List<Double> xy=getNextXY(peopleBase,speedScale);
                    nextX=xy.get(0);
                    nextY=xy.get(1);
                }else {
                    List<Double> xy=getNextXYWithRun(peopleBase,speedScale);
                    nextX=xy.get(0);
                    nextY=xy.get(1);
                }

                GridElement gridElement=null;
                try {
                    gridElement=map.getGridElimentByXY(nextX,nextY);
                } catch (Exception e) {
                    // if gridElement is not exist
                    if(speedTooMaxTimes<=0){
                        speedScale*=0.9;
                        speedTooMaxTimes=20;
                    }
                    speedTooMaxTimes--;
                    continue;
                }

                // just allow walk in same area
                if(!GridElementUtil.isConnected(peopleBase.getLocation(),gridElement)){
                    if(straitWalk){
                        updateStraitWalkDirection(peopleBase,speedScale);
                    }
                    if(speedTooMaxTimes<=0){
                        speedScale*=0.9;
                        speedTooMaxTimes=20;
                    }
                    speedTooMaxTimes--;
                    continue;
                }

                if(gridElement.isWalkAble()){
                    // Simulate keeping social Distance Rate
                    if(getRandom().nextDouble()<distanceKeepRate&&keepDistance){
                        if(GridElementUtil.isKeepSocialDistance(gridElement,peopleBase,peopleBase.getSocialDistance())){
                            peopleBase.moveToNextLocation(nextX,nextY);
                            break;
                        }
                    }else {
                        peopleBase.moveToNextLocation(nextX,nextY);
                        break;
                    }
                }

                if(straitWalk){
                    updateStraitWalkDirection(peopleBase,speedScale);
                }

                maxFindNextLocationTimes--;
                if(maxFindNextLocationTimes<-20){
                    distanceKeepRate=distanceKeepRate*0.7;
                    notWayToWalk=true;
                }
            }

            if(maxFindNextLocationTimes==0){
                logger.log(Log.DEBUG,"can't find next point to move.");
            }
        }else {
            if(stopTime==0){
                stopTime=initTimeDuration/35;
            }
            stopTime--;
        }
        // minus duration
        if(!infinity){
            timeDuration--;
            if(timeDuration<=0) finish();
        }
    }

    private void updateStraitWalkDirection(PeopleBase peopleBase,double speedScale){
        double speed=peopleBase.getRandomSpeed()*speedScale;
        xOffset=speed*random.nextDouble();
        yOffset=Math.sqrt(speed*speed-xOffset*xOffset);
        if(getRandom().nextBoolean()){
            xOffset=-xOffset;
        }
        if(getRandom().nextBoolean()){
            yOffset=-yOffset;
        }
//        if(getRandom().nextBoolean()){
//            xOffset=peopleBase.getRandomSpeed()*(1-0.9*random.nextDouble());
//            if(getRandom().nextBoolean()){
//                xOffset=-xOffset;
//            }
//        }
//        if(getRandom().nextBoolean()){
//            yOffset=peopleBase.getRandomSpeed()*(1-0.9*random.nextDouble());
//            if(getRandom().nextBoolean()){
//                yOffset=-yOffset;
//            }
//        }
    }

    private List<Double> getNextXYWalkStrait(PeopleBase peopleBase, double speedScale){
        return getNextXY(peopleBase,speedScale,xOffset,yOffset);
    }

    private List<Double> getNextXYWithRun(PeopleBase peopleBase, double speedScale){
        double xOffset=0;
        double yOffset=0;
        double speed=peopleBase.getRandomSpeed()*speedScale*2;
        xOffset=speed*random.nextDouble();
        yOffset=Math.sqrt(speed*speed-xOffset*xOffset);
        if(getRandom().nextBoolean()){
            xOffset=-xOffset;
        }
        if(getRandom().nextBoolean()){
            yOffset=-yOffset;
        }
        return getNextXY(peopleBase,speedScale,xOffset,yOffset);
    }

    private List<Double> getNextXY(PeopleBase peopleBase,double speedScale){
        double xOffset=0;
        double yOffset=0;
        double speed=peopleBase.getRandomSpeed()*speedScale;
        xOffset=speed*random.nextDouble();
        yOffset=Math.sqrt(speed*speed-xOffset*xOffset);
        if(getRandom().nextBoolean()){
            xOffset=-xOffset;
        }
        if(getRandom().nextBoolean()){
            yOffset=-yOffset;
        }

        return getNextXY(peopleBase,speedScale,xOffset,yOffset);
    }

    private List<Double> getNextXY(PeopleBase peopleBase,double speedScale,double xOffset, double yOffset){
        double nextX=peopleBase.getX()+xOffset*speedScale;
        double nextY=peopleBase.getY()+yOffset*speedScale;
        List<Double> xy=new ArrayList<>();
        xy.add(nextX);
        xy.add(nextY);
        return xy;
    }

    public boolean isKeepDistance() {
        return keepDistance;
    }

    public void setKeepDistance(boolean keepDistance) {
        this.keepDistance = keepDistance;
    }
}
