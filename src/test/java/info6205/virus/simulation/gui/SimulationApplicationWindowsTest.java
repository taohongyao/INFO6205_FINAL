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
        SimulationApplication simulationApplication=new SimulationApplication(60*60*24,70,50);
        SimulationApplicationWindows windows=new SimulationApplicationWindows(simulationApplication,-20, 50, 0.108);
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

        SimulationRender simulationRender=new SimulationRender(windows,0,map.getHigh(),0.05,simulationApplication);
        while (true){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            simulationRender.renderAreaBase(list,canvas.getGraphics());
        }
    }


    @Test
    public void applicationStart2(){
        SimulationApplication simulationApplication=new SimulationApplication(60*60*24,100,80);
        SimulationApplicationWindows windows=new SimulationApplicationWindows(simulationApplication,-8, 50, 0.088);
//        simulationApplication.setWindows(windows);
        simulationApplication.start();

    }


    @Test
    public void renderTest(){
        SimulationApplication simulationApplication=new SimulationApplication(60*60*24,100,80);
        SimulationApplicationWindows windows=new SimulationApplicationWindows(simulationApplication,-20, 50, 0.108);
        while (true){
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            windows.render();
        }
    }


    @Test
    public void hello(){
        List<String> list=null;
        for(String i:list){
            System.out.println(i);
        }
    }

}