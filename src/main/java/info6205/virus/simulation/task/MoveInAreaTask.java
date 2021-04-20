package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.building.BuildingBase;
import info6205.virus.simulation.map.GridElement;

public class MoveInAreaTask extends MoveTask{
    protected AreaBase areaBase;

    public MoveInAreaTask(AreaBase areaBase) {
        super(0, 0, 0);
        this.areaBase = areaBase;
    }

    @Override
    public void executeTask(PeopleBase peopleBase) {
        GridElement nextLocation=areaBase.getRandomWalkableGridElement();
        try {
            if(areaBase instanceof BuildingBase){
                ((BuildingBase) areaBase).increaseSizeByOne();
            }
            peopleBase.moveToNextLocation(nextLocation.getRealX(),nextLocation.getRealY());
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }
}
