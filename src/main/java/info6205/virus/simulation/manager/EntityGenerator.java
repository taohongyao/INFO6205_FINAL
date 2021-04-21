package info6205.virus.simulation.manager;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.entity.VirusBase;
import info6205.virus.simulation.map.SimulationMap;

import java.util.List;

public abstract class EntityGenerator {
    protected SimulationMap map;
    public EntityGenerator(SimulationMap map) {
        this.map = map;
    }
    public abstract  List<AreaBase> generateBuilding();
    public abstract  List<PeopleBase> generatePeople(AreaManger areaManger);
    public abstract  List<VirusBase> generateVirus(PeopleManger peopleManger);
}
