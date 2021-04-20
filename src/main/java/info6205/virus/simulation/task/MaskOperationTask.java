package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.MaskBase;
import info6205.virus.simulation.entity.PeopleBase;

public class MaskOperationTask extends TaskBase {
    protected boolean ware;

    public MaskOperationTask(boolean ware) {
        this.ware = ware;
    }

    @Override
    public void executeTask(PeopleBase peopleBase) {
        MaskBase maskBase=peopleBase.getMaskBase();
        if(maskBase!=null){
            if(ware==true){
                maskBase.wareMask();
            }else {
                maskBase.unWareMask();
            }
        }
        finish();
    }
}
