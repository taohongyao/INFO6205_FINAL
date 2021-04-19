package info6205.virus.simulation.gui;

import info6205.virus.simulation.console.SimulationApplication;
import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.map.SimulationMap;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


class SimulationApplicationWindowsTest {

    public static void main(String [] args){
        SimulationApplication simulationApplication=new SimulationApplication(60*60*24,1000,1000);
        SimulationApplicationWindows windows=new SimulationApplicationWindows(simulationApplication);
        JPanel canvas=windows.getCanvas();
        SimulationMap map=new SimulationMap(10,10);
        RoadArea roadArea=new RoadArea(1,2,2,1,map);
        RoadArea roadArea2=new RoadArea(0,2,1,1,map);
        RoadArea roadArea3=new RoadArea(2,2,3,1,map);
        RoadArea roadArea4=new RoadArea(1,3,2,2,map);
        RoadArea roadArea5=new RoadArea(2,3,3,2,map);
        RoadArea roadArea6=new RoadArea(3,3,4,2,map);
        roadArea.linkRodaArea(roadArea2).linkRodaArea(roadArea3).linkRodaArea(roadArea4);
        roadArea3.linkRodaArea(roadArea5);
        roadArea4.linkRodaArea(roadArea5);
        roadArea5.linkRodaArea(roadArea6);
        List<RoadArea> list=new ArrayList<>();
        list.add(roadArea);
        list.add(roadArea2);
        list.add(roadArea3);
        list.add(roadArea4);
        list.add(roadArea5);
        list.add(roadArea6);

        SimulationRender simulationRender=new SimulationRender(canvas.getHeight(),canvas.getWidth(),0,map.getHigh(),0.05);
        simulationRender.renderAreaBase(list,canvas.getGraphics());
    }

    @Test
    public void renderTest(){
        SimulationApplication simulationApplication=new SimulationApplication(60*60*24,1000,1000);
        SimulationApplicationWindows windows=new SimulationApplicationWindows(simulationApplication);
    }


    @Test
    public void hello(){
        List<String> list=null;
        for(String i:list){
            System.out.println(i);
        }
    }

}