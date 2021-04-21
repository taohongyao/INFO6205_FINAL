package info6205.virus.simulation.map;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.logger.Debug;
import info6205.virus.simulation.logger.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SimulationMap {
    private double width;
    private double high;
    private double divEveryMeter;
    private Time currentTime;
    private List<List<GridElement>> grids;


    private static Logger logger=Logger.getLogger(SimulationMap.class.getName());

    public SimulationMap(double width, double high) {
        this.width = width;
        this.high = high;
        divEveryMeter = 2;
        grids=new ArrayList<>();
        for(int y=0;y<high*divEveryMeter;y++){
            List<GridElement> list=new ArrayList<>();
            for(int x=0;x<width*divEveryMeter;x++){
                list.add(new GridElement(x,y,x/divEveryMeter,y/divEveryMeter,false,this));
            }
            grids.add(list);
        }
        logger.log(Log.DEBUG,String.join(" ","High:",""+grids.size(),"Width:",""+grids.get(0).size()));
    }

    public GridElement getGridElimentByXY(double x,double y) throws Exception {
        if(x<0||x>width||y<0||y>high) throw new Exception("Out of Map");
        return grids.get((int)(Math.round(y*divEveryMeter))).get((int)(Math.round(x*divEveryMeter)));
    }

    public List<List<GridElement>> getSubGrids(double xLeftUp,double yLeftUp, double xRightDown,double yRightDown){
        List<List<GridElement>> subGrids=new ArrayList<>();
        int xLeftUpInt=(int)Math.round(xLeftUp*divEveryMeter);
        int yLeftUpInt=(int)Math.round(yLeftUp*divEveryMeter);
        int xRightDownInt=(int)Math.round(xRightDown*divEveryMeter);
        int yRightDownInt=(int)Math.round(yRightDown*divEveryMeter);
        for(int y=yRightDownInt;y<yLeftUpInt;y++){
            List<GridElement> list=new ArrayList<>();
            for(int x=xLeftUpInt;x<xRightDownInt;x++){
                GridElement element=grids.get(y).get(x);
                list.add(element);
            }
            subGrids.add(list);
        }
        return subGrids;
    }

    public List<GridElement> getAdjacentElements(GridElement gridElement){
        List<GridElement> list=new ArrayList<>();
        int x=gridElement.getMapXIndex();
        int y=gridElement.getMapYIndex();
        int elementsWidth=grids.get(0).size();
        int elementsHigh=grids.size();
        int leftX=x-1;
        int rightX=x+1;
        int upY=y+1;
        int downY=y-1;
        if(leftX>=0){
            list.add(grids.get(y).get(leftX));
        }
        if(rightX<elementsWidth){
            list.add(grids.get(y).get(rightX));
        }
        if(upY<elementsHigh){
            list.add(grids.get(upY).get(x));
        }
        if(downY>=0){
            list.add(grids.get(downY).get(x));
        }
        return  list;
    }

    public List<List<GridElement>> getSubGridsAndBindArea(double xLeftUp, double yLeftUp, double xRightDown, double yRightDown, AreaBase areaBase){
        logger.log(Log.DEBUG,String.join(" ","xLU:"+xLeftUp,", yLU:"+yLeftUp,", xRD:"+xRightDown,", yRightDown"+yRightDown));
        List<List<GridElement>> subGrids=new ArrayList<>();
        int xLeftUpInt=(int)Math.round(xLeftUp*divEveryMeter);
        int yLeftUpInt=(int)Math.round(yLeftUp*divEveryMeter);
        int xRightDownInt=(int)Math.round(xRightDown*divEveryMeter);
        int yRightDownInt=(int)Math.round(yRightDown*divEveryMeter);
        for(int y=yRightDownInt;y<yLeftUpInt;y++){
            List<GridElement> list=new ArrayList<>();
            for(int x=xLeftUpInt;x<xRightDownInt;x++){
                GridElement element=grids.get(y).get(x);
                element.bindArea(areaBase);
                list.add(element);
            }
            subGrids.add(list);
        }
        logger.log(Log.DEBUG,"SubGrides size: "+subGrids.size());
        return subGrids;
    }

    public Time getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Time currentTime) {
        this.currentTime = currentTime;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getDivEveryMeter() {
        return divEveryMeter;
    }

    public void setDivEveryMeter(double divEveryMeter) {
        this.divEveryMeter = divEveryMeter;
    }
}
