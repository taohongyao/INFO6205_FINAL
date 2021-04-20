package info6205.virus.simulation.entity.virus;

import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.VirusBase;
import info6205.virus.simulation.map.GridElement;

import java.util.ArrayList;
import java.util.List;

public class Covid19 extends VirusBase {

    public Covid19(double kFactor, double rFactor, int potentialDay, int aliveDay) {
        super(kFactor, rFactor, potentialDay,aliveDay);
    }

    public Covid19() {
        super(10, 20,7+getRandom().nextInt(7),7);
    }

    @Override
    public VirusBase generate() {
        return new Covid19();
    }

    @Override
    public List<VirusBase> findCarrierAndInfect() {
        List<VirusBase> list=new ArrayList<>();
        if(haveAttachPlace()){
            GridElement currentPosition=getLocation();
            // TODO: 4/19/2021  

        }else {
            PeopleBase peopleBase=getPeopleBase();
            GridElement currentPosition=peopleBase.getLocation();
            // TODO: 4/19/2021

        }
        return list;
    }

}
