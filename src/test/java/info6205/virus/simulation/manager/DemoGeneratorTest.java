package info6205.virus.simulation.manager;

import info6205.virus.simulation.console.SimulationApplication;
import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.gui.SimulationApplicationWindows;
import info6205.virus.simulation.util.RoadAreaUtil;
import org.junit.jupiter.api.Test;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class DemoGeneratorTest {

    @Test
    void generateBuilding() throws Exception {
        SimulationApplication simulationApplication=new SimulationApplication(60*60*24,100,80);
        SimulationApplicationWindows windows=new SimulationApplicationWindows(simulationApplication,-20, 50, 0.108);
        AreaManger areaManger=simulationApplication.getAreaManger();
        RoadArea src=areaManger.getRandomHouse().getPublicArea();
        RoadArea des=areaManger.getRandomOffice().getPublicArea();
        Queue<RoadArea> path= (Queue<RoadArea>) RoadAreaUtil.findPath(src,des);
        System.out.println(path);
        assertTrue(path.size()>=0);
    }
}