package info6205.virus.simulation.gui;

import java.awt.*;
import java.util.List;

public class StatisticRender {
    protected int chartWidth;
    protected int chartHigh;
    protected int x;
    protected int y;


    public void drawCoordinate(Graphics g){
//        int xLabel=10;
//        int yLabel=10;
//        int canvasWidth=chartHigh/xLabel;
//        int canvasHigh=chartWidth/yLabel;
//        double width=chartHigh/(1.0*xLabel)*zoom;
//        double high=chartWidth/(1.0*yLabel)*zoom;
//        for(int i=0;i<xLabel;i++){
//            g.drawString(String.format("|%.2f",xLeftTopRealWorld+i*width), (int) (i*canvasWidth),chartHigh-10);
//        }
//        for(int i=0;i<yLabel;i++){
//            g.drawString("-", 0,(int) (i*canvasHigh));
//            g.drawString(String.format("%.2f",yLeftTopRealWorld-i*high), 0,(int) (i*canvasHigh)+10);
//        }
    }

    public void drawLines(List<List<Double>> points, Graphics g){

    }
}
