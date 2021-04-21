package info6205.virus.simulation.entity.virus;

import info6205.virus.simulation.entity.MaskBase;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.VirusBase;
import info6205.virus.simulation.map.GridElement;
import info6205.virus.simulation.util.GridElementUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SARS extends VirusBase{

    public SARS(double kFactor, double rFactor, int potentialDay, int aliveDay) {
        super(kFactor, rFactor, potentialDay,aliveDay);
    }

    @Override
    public void makeCarrierPeopleChangeState() {
        if(peopleBase!=null&&isHaveSymptom()){
            peopleBase.setFeelSick(true);
        }
    }

    public SARS() {
        super(10, 10,7+getRandom().nextInt(7),7);
    }

    @Override
    public VirusBase generate() {
        return new SARS();
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
                            SARS sars= (SARS) this.generate();
                            sars.infectPeople(peopleBase);
                            list.add(sars);
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
                            SARS sars= (SARS) this.generate();
                            sars.infectPeople(people);
                            list.add(sars);
                        }
                    }
                }
            }
        }
        return list;
    }

}
