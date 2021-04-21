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
    private JPanel mainPanel;
    private JFrame jFrame;
    private Timer timer;

    private XYSeries series = new XYSeries("R factor");

    public synchronized void addData(List<List<Double>> data) {
        for (List<Double> d : data) {
            double x = d.get(0);
            double y = d.get(1);
            series.add(x, y);
        }
    }

    public void refreshData() {
        final XYSeriesCollection data = new XYSeriesCollection(series);
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "R factor",
                "X",
                "Y",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        addData(DataRecord.getkFactor());
        jFrame.setContentPane(new ChartPanel(chart));
    }

    public Statistic() {

//        chartCanvas.setPreferredSize(new Dimension(500, 270));
        jFrame = new JFrame("Statistic");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        refreshData();
//        jFrame.getContentPane().add(graph);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setLocation(dim.width / 2 - jFrame.getSize().width / 2, dim.height / 2 - jFrame.getSize().height / 2);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

}
