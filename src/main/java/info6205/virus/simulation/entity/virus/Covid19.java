package info6205.virus.simulation.entity.virus;

import info6205.virus.simulation.entity.MaskBase;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.VirusBase;
import info6205.virus.simulation.map.GridElement;
import info6205.virus.simulation.util.GridElementUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Covid19 extends VirusBase {

    public Covid19(double kFactor, double rFactor, int potentialDay, int aliveDay) {
        super(kFactor, rFactor, potentialDay,aliveDay);
    }

    @Override
    public void makeCarrierPeopleChangeState() {
        if(peopleBase!=null&&isHaveSymptom()){
            peopleBase.setFeelSick(true);
        }
    }

    public Covid19() {
        super(10, 1,7+getRandom().nextInt(7),7);
    }

    @Override
    public VirusBase generate() {

        return new Covid19();
    }

    @Override
    public List<VirusBase> findCarrierAndInfect() {
        List<VirusBase> list=new ArrayList<>();
        if(this.isAlive()) {
            if(haveAttachPlace()){
                GridElement currentPosition=getLocation();
                Set<PeopleBase> peopleBases= GridElementUtil.getBFSAreaGridsPeople(currentPosition,1.0);
                for (PeopleBase people:peopleBases){
                    MaskBase maskBase=people.getMaskBase();
                    recordContactPeople(people);
                    if((!maskBase.isWare()||maskBase.getEffective()<1)
                            &&!people.isInfected(this)
                            &&!people.isVaccine(this)){
                        int range=random.nextInt(1000000);
                        if(range<rFactor*0.5){
                            Covid19 covid19= (Covid19) this.generate();
                            covid19.infectPeople(peopleBase);
                            list.add(covid19);
                        }
                    }
                }
            }else {
                PeopleBase peopleBase=getPeopleBase();
                GridElement currentPosition=peopleBase.getLocation();
                //find 5 meter people
                Set<PeopleBase> peopleBases= GridElementUtil.getBFSAreaGridsPeople(currentPosition,3.0);
                peopleBases.remove(this.peopleBase);
                for (PeopleBase people:peopleBases){
                    MaskBase maskBase=people.getMaskBase();
                    recordContactPeople(people);
                    if((maskBase==null||!maskBase.isWare()||maskBase.getEffective()<1)
                            &&!people.isInfected()
                            &&!people.isVaccine()){
                        int range=random.nextInt(1000);
                        if(range<rFactor){
                            Covid19 covid19= (Covid19) this.generate();
                            covid19.infectPeople(people);
                            list.add(covid19);
                        }
                    }
                }
            }
        }
        return list;
    }
}

