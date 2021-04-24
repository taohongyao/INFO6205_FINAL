package info6205.virus.simulation.executor;

import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.manager.PeopleManger;
import info6205.virus.simulation.task.TaskBase;

import java.util.List;


public class PeopleExecutor implements ExecutorBase{
    protected PeopleManger peopleManger;

    public PeopleExecutor(PeopleManger peopleManger) {
        this.peopleManger=peopleManger;
    }

    private void execute( List<PeopleBase> peopleBaseList){
        for (PeopleBase peopleBase:peopleBaseList){
            if(peopleBase.isAlive()){
                TaskBase taskBase=peopleBase.getCurrentTask();
                if(taskBase.isFinished()){
                    peopleBase.deleteCurrentTask();
                }
                taskBase=peopleBase.getCurrentTask();
                taskBase.executeTask(peopleBase);
            }
        }
    }
    private void dailyExecute(List<PeopleBase> peopleBaseList){
        for (PeopleBase peopleBase:peopleBaseList){
            if(peopleBase.isAlive()){
                peopleBase.DailyStatusRefresh();
            }
        }
    }

    private void weekendsExecute(List<PeopleBase> peopleBaseList){
        for (PeopleBase peopleBase:peopleBaseList){
            if(peopleBase.isAlive()){
                peopleBase.weekendDailyRefresh();
            }
        }
    }

    @Override
    public void roundSchedule() {
        execute(peopleManger.getAdults());
        execute(peopleManger.getTeens());
        execute(peopleManger.getElders());
    }

    @Override
    public void daySchedule() {
        dailyExecute(peopleManger.getElders());
        dailyExecute(peopleManger.getTeens());
        dailyExecute(peopleManger.getAdults());
    }

    @Override
    public void weekendsSchedule() {
        weekendsExecute(peopleManger.getElders());
        weekendsExecute(peopleManger.getTeens());
        weekendsExecute(peopleManger.getAdults());
    }
}
