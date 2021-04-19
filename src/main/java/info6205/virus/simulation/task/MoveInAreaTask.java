package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.map.GridElement;

public class MoveInAreaTask extends MoveTask{
    protected AreaBase areaBase;

    public MoveInAreaTask(double socialDistance, double speed, double keepSocialDistanceRate, AreaBase areaBase) {
        super(socialDistance, speed, keepSocialDistanceRate);
        this.areaBase = areaBase;
    }

    public MoveInAreaTask(double socialDistance, double speed, Long walkSeed, double keepSocialDistanceRate, AreaBase areaBase) {
        super(socialDistance, speed, walkSeed, keepSocialDistanceRate);
        this.areaBase = areaBase;
    }

    @Override
    protected void executeTask(PeopleBase peopleBase) {
        GridElement nextLocation=areaBase.getRandomWalkableGridElement();
        try {
            peopleBase.moveToNextLocation(nextLocation.getRealX(),nextLocation.getRealY());
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }
}
