package info6205.virus.simulation.gui;

import info6205.virus.simulation.map.Time;
import info6205.virus.simulation.record.DataRecord;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class Statistic {
    private JFrame jFrame;
    private XYSeries series;



    public synchronized void refreshData() {
//        data= new XYSeriesCollection(getData(DataRecord.getkFactor()));
        series.clear();
        synchronized (DataRecord.getkFactor()){
            for (List<Double> d : DataRecord.getkFactor()) {
                double x = d.get(0);
                double y = d.get(1);
                series.add(x, y);
            }
        }
        jFrame.repaint();
    }

    public void show(){
        jFrame.setVisible(true);
        refreshData();
    }
    public void hide(){
        jFrame.setVisible(false);
    }

    public Statistic() {
        jFrame = new JFrame("Statistic");
        series=new XYSeries("K factor");
        XYSeriesCollection data= new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "K factor",
                "time",
                "k",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        jFrame.setContentPane(new ChartPanel(chart));
        jFrame.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation(dim.width / 2 - jFrame.getSize().width, dim.height / 2 - jFrame.getSize().height / 2);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        refreshData();
        hide();

    }

}
