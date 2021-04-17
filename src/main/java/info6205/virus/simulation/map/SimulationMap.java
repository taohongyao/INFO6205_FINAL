package info6205.virus.simulation.map;

import java.util.ArrayList;
import java.util.List;

public class SimulationMap {
    private double width;
    private double high;
    private int divEveryMeter;
    private List<List<GridElement>> grids;

    public SimulationMap(double width, double high) {
        this.width = width;
        this.high = high;
        divEveryMeter = 2;
        grids=new ArrayList<>();
        for(int y=0;y<=high*divEveryMeter;y++){
            List<GridElement> list=new ArrayList<>();
            for(int x=0;x<=width*divEveryMeter;x++){
                list.set(x,new GridElement(x,y,x/divEveryMeter,y/divEveryMeter));
            }
            grids.set(y,list);
        }
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
        for(int y=yLeftUpInt;y<=yRightDownInt;y++){
            List<GridElement> list=new ArrayList<>();
            for(int x=xLeftUpInt;x<=xRightDownInt;x++){
                list.set(x,grids.get(y).get(x));
            }
            grids.set(y,list);
        }
        return subGrids;
    }
}
