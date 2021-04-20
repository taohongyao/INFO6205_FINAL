package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.building.BuildingBase;
import info6205.virus.simulation.map.GridElement;

public class LeaveBuildingTask extends TaskBase{

    private BuildingBase getCurrentBuildingBase(PeopleBase peopleBase){
        GridElement gridElement=peopleBase.getLocation();
        for (AreaBase areaBase:gridElement.getAreas()){
            if(areaBase instanceof BuildingBase){
                return (BuildingBase) areaBase;
            }
        }
        return null;
    }
    @Override
    public void executeTask(PeopleBase peopleBase) {
        BuildingBase buildingBase=getCurrentBuildingBase(peopleBase);
        if(buildingBase!=null){
            GridElement location=buildingBase.getPublicArea().getRandomGridElement();
            try {
                peopleBase.moveToNextLocation(location.getRealX(), location.getRealY());
                buildingBase.decreaseSizeByOne();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        finish();
    }
}
