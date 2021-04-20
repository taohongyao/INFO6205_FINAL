package info6205.virus.simulation.executor;

import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.manager.PeopleManger;
import info6205.virus.simulation.task.TaskBase;


public class PeopleExecutor implements ExecutorBase{
    protected PeopleManger peopleManger;

    public PeopleExecutor(PeopleManger peopleManger) {
        this.peopleManger=peopleManger;
    }

    @Override
    public void roundSchedule() {
        for (PeopleBase peopleBase:peopleManger.getAdults()){
            TaskBase taskBase=peopleBase.getCurrentTask();
            if(taskBase.isFinished()){
                peopleBase.deleteCurrentTask();
            }
            taskBase=peopleBase.getCurrentTask();
            taskBase.executeTask(peopleBase);
        }
        for (PeopleBase peopleBase:peopleManger.getElders()){
            TaskBase taskBase=peopleBase.getCurrentTask();
            if(taskBase.isFinished()){
                peopleBase.deleteCurrentTask();
            }
            taskBase=peopleBase.getCurrentTask();
            taskBase.executeTask(peopleBase);
        }
        for (PeopleBase peopleBase:peopleManger.getTeens()){
            TaskBase taskBase=peopleBase.getCurrentTask();
            if(taskBase.isFinished()){
                peopleBase.deleteCurrentTask();
            }
            taskBase=peopleBase.getCurrentTask();
            taskBase.executeTask(peopleBase);
        }
    }

    @Override
    public void daySchedule() {

    }
}
