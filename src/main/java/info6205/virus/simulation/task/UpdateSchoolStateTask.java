package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.PeopleBase;

public class UpdateSchoolStateTask extends  TaskBase{
    private boolean newStatus;

    public UpdateSchoolStateTask(boolean newStatus) {
        this.newStatus = newStatus;
    }

    @Override
    public void executeTask(PeopleBase peopleBase) {
        peopleBase.setNeedToSchool(newStatus);
        finish();
    }
}
