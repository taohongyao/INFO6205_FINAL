package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.building.BuildingBase;
import info6205.virus.simulation.manager.AreaManger;
import info6205.virus.simulation.map.GridElement;
import info6205.virus.simulation.util.GridElementUtil;

import java.util.ArrayList;
import java.util.List;

public class MoveInAreaTask extends MoveTask{
    protected AreaBase areaBase;
    private AreaManger areaManger;
    protected int times;

    public void setTimes(int times) {
        this.times = times;
    }

    public MoveInAreaTask(AreaBase areaBase,AreaManger areaManger) {
//        super(0, 0, 0);
        this.areaBase = areaBase;
        this.areaManger=areaManger;
    }

    @Override
    public void executeTask(PeopleBase peopleBase) {
        GridElement nextLocation=null;
        int findNextPointTimes=5;
        boolean findValidLocation=false;
        while (findNextPointTimes>0){
            nextLocation=areaBase.getRandomWalkableGridElement();
            findNextPointTimes--;
            if(GridElementUtil.isKeepSocialDistance(nextLocation,peopleBase,peopleBase.getSocialDistance())){
                findValidLocation=true;
                break;
            }
        }
        if(!findValidLocation){
            if(times<3){
                List<TaskBase> tasks=new ArrayList<>();
                tasks.add(new WaitTask(60*5));
                MoveInAreaTask moveInAreaTask=new MoveInAreaTask(areaBase,areaManger);
                moveInAreaTask.setTimes(times+1);
                tasks.add(moveInAreaTask);
                peopleBase.exchangeCurrentTask(tasks);
                return;
            }else {
                peopleBase.cleanAllTasks();
                peopleBase.addTask(new TasksGenerateTask(areaManger));
            }
        }

        try {
            if(areaBase instanceof BuildingBase){
                ((BuildingBase) areaBase).increaseSizeByOne();
            }
            peopleBase.moveToNextLocation(nextLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }
}
