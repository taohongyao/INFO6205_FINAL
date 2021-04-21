package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.building.BuildingBase;
import info6205.virus.simulation.map.GridElement;
import info6205.virus.simulation.util.GridElementUtil;

import java.util.ArrayList;
import java.util.List;

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

            int findNextPointTimes=5;
            GridElement location=null;
            boolean findValidLocation=false;
            while (findNextPointTimes>0){
                location=buildingBase.getPublicArea().getRandomGridElement();
                findNextPointTimes--;
                if(GridElementUtil.isKeepSocialDistance(location,peopleBase,peopleBase.getSocialDistance())){
                    findValidLocation=true;
                    break;
                }
            }

            if(!findValidLocation){
                location=buildingBase.getPublicArea().getRandomGridElement();
//                List<TaskBase> tasks=new ArrayList<>();
//                tasks.add(new WaitTask(60*5));
//                tasks.add(new LeaveBuildingTask());
//                peopleBase.exchangeCurrentTask(tasks);
//                return;
            }

            try {
                peopleBase.moveToNextLocation(location);
                buildingBase.decreaseSizeByOne();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        finish();
    }
}
