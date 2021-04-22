package info6205.virus.simulation.gui;

import info6205.virus.simulation.console.SimulationApplication;
import info6205.virus.simulation.manager.PeopleManger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimulationApplicationWindows {

    private JPanel canvas;
    private JPanel mainPanel;
    private JButton runButton;
    private JTextField speedText;
    private JButton settingButton;
    private JLabel timeLabel;
    private JLabel timeDayLabel;
    private JLabel timeWeekLabel;
    private JButton resetBtn;
    private JButton statisticButton;
    private SimulationApplication simulationApplication;
    private SimulationRender render;
    private static Logger logger = Logger.getLogger(SimulationApplication.class.getName());
    private Setting settingWindow;
    private Statistic statisticWindow;
    private BufferedImage im;


    public JPanel getCanvas() {
        return canvas;
    }

    public SimulationApplicationWindows(SimulationApplication simulationApplication, double xLTRealWorld, double yLTRealWorld, double zoom) {
        this.simulationApplication = simulationApplication;
        JFrame jFrame = new JFrame("VirusSimulation");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.getContentPane().add(mainPanel);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setLocation(dim.width / 2 - jFrame.getSize().width / 2, dim.height / 2 - jFrame.getSize().height / 2);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.render = new SimulationRender(canvas.getHeight(), canvas.getWidth(), xLTRealWorld, yLTRealWorld, zoom);
        logger.log(Level.INFO, "Start render.");
        settingWindow = new Setting(simulationApplication);
        statisticWindow = new Statistic();

        canvas.setBackground(Color.white);
        MouseEvent mouseEvent = new MouseEvent(render, canvas, this);
        canvas.addMouseListener(mouseEvent);
        canvas.addMouseMotionListener(mouseEvent);
        canvas.addMouseWheelListener(mouseEvent);
//        mainPanel.addMouseListener(mouseEvent);
//        jFrame.addMouseListener(mouseEvent);
//        jFrame.addMouseMotionListener(mouseEvent);
        runButton.addMouseListener(new MouseAdapter() {
            private boolean run = true;

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (run) {
                    simulationApplication.stop();
                    runButton.setText("Run");
                    run = false;
                } else {
                    simulationApplication.run();
                    runButton.setText("Stop");
                    run = true;
                }
            }
        });
        settingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                settingWindow.show();
            }
        });
        resetBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                simulationApplication.reset();
            }
        });
        statisticButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                statisticWindow.show();
            }
        });
        refreshStaticGraph();
    }

    public void windowRender() {
        timeLabel.setText("" + simulationApplication.getTime());
//        timeSecondLabel.setText("" + simulationApplication.getWorldTimeUnit());
        timeDayLabel.setText("" + simulationApplication.getDays());
        timeWeekLabel.setText("" + simulationApplication.getWeek());
    }

    public void render() {
        // Render AreaBase
        windowRender();
//        render.cleanCanvas(canvas.getGraphics());
//        render.renderAreaBase(simulationApplication.getAreaManger().getRoadAreas(), canvas.getGraphics());
//        render.drawRecordLine(canvas.getGraphics());
//        render.renderAreaBase(simulationApplication.getAreaManger().getHouses(), canvas.getGraphics());
//        render.drawRecordLine(canvas.getGraphics());
//        render.renderAreaBase(simulationApplication.getAreaManger().getHospitals(), canvas.getGraphics());
//        render.drawRecordLine(canvas.getGraphics());
//        render.renderAreaBase(simulationApplication.getAreaManger().getMalls(), canvas.getGraphics());
//        render.drawRecordLine(canvas.getGraphics());
//        render.renderAreaBase(simulationApplication.getAreaManger().getOffices(), canvas.getGraphics());
//        render.drawRecordLine(canvas.getGraphics());
//        render.renderAreaBase(simulationApplication.getAreaManger().getParks(), canvas.getGraphics());
//        render.drawRecordLine(canvas.getGraphics());
//        render.renderAreaBase(simulationApplication.getAreaManger().getRestaurants(), canvas.getGraphics());
//        render.drawRecordLine(canvas.getGraphics());
//        render.renderAreaBase(simulationApplication.getAreaManger().getSchools(), canvas.getGraphics());
//        render.drawRecordLine(canvas.getGraphics());
        painStaticGraph(canvas);
        render.renderPeopleList(simulationApplication.getPeopleManger().getAdults(), canvas.getGraphics());
        render.renderPeopleList(simulationApplication.getPeopleManger().getElders(), canvas.getGraphics());
        render.renderPeopleList(simulationApplication.getPeopleManger().getTeens(), canvas.getGraphics());

        PeopleManger peopleManger = simulationApplication.getPeopleManger();
        render.renderInfectedPanel(canvas.getWidth() - 500, 10, 500, peopleManger.getInfectedTeenCount(), peopleManger.getInfectedAdultCount(), peopleManger.getInfectedElderCount(), canvas.getGraphics());
        render.drawRecordLine(canvas.getGraphics());
        render.drawRecordLine(canvas.getGraphics());

    }

    private void painStaticGraph(JPanel canvas) {
        canvas.getGraphics().drawImage(im, 0, 0, canvas);
    }

    public void refreshStaticGraph() {
        Container c = canvas;
        im = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics canvas = im.getGraphics();
        render.cleanCanvas(canvas);
        render.renderAreaBase(simulationApplication.getAreaManger().getRoadAreas(), canvas);
        render.renderAreaBase(simulationApplication.getAreaManger().getHouses(), canvas);
        render.renderAreaBase(simulationApplication.getAreaManger().getHospitals(), canvas);
        render.renderAreaBase(simulationApplication.getAreaManger().getMalls(), canvas);
        render.renderAreaBase(simulationApplication.getAreaManger().getOffices(), canvas);
        render.renderAreaBase(simulationApplication.getAreaManger().getParks(), canvas);
        render.renderAreaBase(simulationApplication.getAreaManger().getRestaurants(), canvas);
        render.renderAreaBase(simulationApplication.getAreaManger().getSchools(), canvas);
        render.drawCoordinate(canvas);
        render.renderMouseOperationInfo(40, 10, canvas);
        canvas.dispose();
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setMinimumSize(new Dimension(1000, 750));
        mainPanel.setPreferredSize(new Dimension(1000, 750));
        canvas = new JPanel();
        canvas.setLayout(new BorderLayout(0, 0));
        canvas.setMinimumSize(new Dimension(1000, 700));
        canvas.setPreferredSize(new Dimension(1000, 700));
        mainPanel.add(canvas, BorderLayout.NORTH);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        mainPanel.add(panel1, BorderLayout.SOUTH);
        runButton = new JButton();
        runButton.setPreferredSize(new Dimension(150, 30));
        runButton.setText("Stop");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 13;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(runButton, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 14;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 13;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer2, gbc);
        settingButton = new JButton();
        settingButton.setPreferredSize(new Dimension(150, 30));
        settingButton.setText("Setting");
        gbc = new GridBagConstraints();
        gbc.gridx = 17;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(settingButton, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 15;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer3, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("Time:");
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(label1, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 12;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer5, gbc);
        timeLabel = new JLabel();
        timeLabel.setMinimumSize(new Dimension(80, 16));
        timeLabel.setPreferredSize(new Dimension(80, 16));
        timeLabel.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 10;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(timeLabel, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 11;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer6, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Days:");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(label2, gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer7, gbc);
        timeDayLabel = new JLabel();
        timeDayLabel.setMaximumSize(new Dimension(50, 16));
        timeDayLabel.setMinimumSize(new Dimension(50, 16));
        timeDayLabel.setPreferredSize(new Dimension(50, 16));
        timeDayLabel.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(timeDayLabel, gbc);
        final JPanel spacer8 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer8, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Week:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(label3, gbc);
        final JPanel spacer9 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer9, gbc);
        final JPanel spacer10 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer10, gbc);
        timeWeekLabel = new JLabel();
        timeWeekLabel.setMinimumSize(new Dimension(50, 16));
        timeWeekLabel.setPreferredSize(new Dimension(50, 16));
        timeWeekLabel.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(timeWeekLabel, gbc);
        resetBtn = new JButton();
        resetBtn.setPreferredSize(new Dimension(150, 30));
        resetBtn.setText("Reset");
        gbc = new GridBagConstraints();
        gbc.gridx = 18;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(resetBtn, gbc);
        statisticButton = new JButton();
        statisticButton.setPreferredSize(new Dimension(150, 30));
        statisticButton.setText("Statistic");
        gbc = new GridBagConstraints();
        gbc.gridx = 16;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(statisticButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
