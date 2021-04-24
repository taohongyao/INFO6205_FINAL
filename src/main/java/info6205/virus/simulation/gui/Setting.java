package info6205.virus.simulation.gui;

import info6205.virus.simulation.console.SimulationApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;

public class Setting {
    private JTextField speedText;
    private JButton applyButton;
    private JTextField distanceText;
    private JTextField keepRateText;
    private JPanel mainPanel;
    private JRadioButton cov19RadioButton;
    private JRadioButton SARSRadioButton;
    private JButton offButton;
    private JButton wareButton;
    private JButton slow60Button;
    private JButton middle600Button;
    private JButton fast1000Button;
    private SimulationApplication simulationApplication;
    private JFrame jFrame;


    public Setting(SimulationApplication simulationApplication) {
        this.simulationApplication = simulationApplication;

        jFrame = new JFrame("Statistic");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setContentPane(mainPanel);
        jFrame.pack();
//        jFrame.setVisible(true);
        jFrame.setLocation(dim.width / 2 - jFrame.getSize().width / 2, dim.height / 2 - jFrame.getSize().height / 2);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        hide();
        applyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int speed = 0;
                try {
                    speed = Integer.parseInt(speedText.getText());
                } catch (Exception exp) {
                    speed = 60;
                    speedText.setText("60");
                }
                simulationApplication.setSimulateSpeed(speed);

                double distance = 0;
                try {
                    distance = Double.parseDouble(distanceText.getText());
                } catch (Exception exp) {
                    distanceText.setText("3");
                    distance = 0;
                }
                double keepRate = 0.8;
                try {
                    keepRate = Double.parseDouble(keepRateText.getText());
                } catch (Exception exp) {
                    keepRateText.setText("0.8");
                    keepRate = 0;
                }
                simulationApplication.getPeopleManger().setSocialDistanceAndKeepRate(distance, keepRate);
            }
        });

        cov19RadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    simulationApplication.setVirusType(0);
                    simulationApplication.reset();
                }
            }
        });

        SARSRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    simulationApplication.setVirusType(1);
                    simulationApplication.reset();
                }
            }
        });
        ButtonGroup buttonGroup2 = new ButtonGroup();
        buttonGroup2.add(cov19RadioButton);
        buttonGroup2.add(SARSRadioButton);

        wareButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                simulationApplication.getPeopleManger().setAllHaveMask();
            }
        });
        offButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                simulationApplication.getPeopleManger().removeAllMask();
            }
        });
        slow60Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                simulationApplication.setSimulateSpeed(60);
                speedText.setText("" + simulationApplication.getSimulateSpeed());
            }
        });

        middle600Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                simulationApplication.setSimulateSpeed(600);
                speedText.setText("" + simulationApplication.getSimulateSpeed());
            }
        });
        fast1000Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                simulationApplication.setSimulateSpeed(3000);
                speedText.setText("" + simulationApplication.getSimulateSpeed());
            }
        });
        jFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                speedText.setText("" + simulationApplication.getSimulateSpeed());
            }
        });
    }

    public void show() {
        jFrame.setVisible(true);
    }

    public void hide() {
        jFrame.setVisible(false);
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
        mainPanel.setLayout(new GridBagLayout());
        final JLabel label1 = new JLabel();
        label1.setText("Ware Mask:");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label1, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(spacer2, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Speed:");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label2, gbc);
        speedText = new JTextField();
        speedText.setText("60");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(speedText, gbc);
        applyButton = new JButton();
        applyButton.setText("Apply");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(applyButton, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Social Distance (m):");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label3, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("Keep Social Distance Rate:");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label4, gbc);
        distanceText = new JTextField();
        distanceText.setText("2");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(distanceText, gbc);
        keepRateText = new JTextField();
        keepRateText.setText("0.8");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 10;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(keepRateText, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 13;
        gbc.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(spacer5, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(spacer6, gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(spacer7, gbc);
        final JPanel spacer8 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(spacer8, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("Virus:");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label5, gbc);
        final JPanel spacer9 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(spacer9, gbc);
        cov19RadioButton = new JRadioButton();
        cov19RadioButton.setSelected(true);
        cov19RadioButton.setText("COV19");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(cov19RadioButton, gbc);
        offButton = new JButton();
        offButton.setText("Remove");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(offButton, gbc);
        slow60Button = new JButton();
        slow60Button.setText("Slow(60)");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(slow60Button, gbc);
        middle600Button = new JButton();
        middle600Button.setText("Middle(600)");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(middle600Button, gbc);
        fast1000Button = new JButton();
        fast1000Button.setText("Fast(3000)");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(fast1000Button, gbc);
        wareButton = new JButton();
        wareButton.setText("Ware");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(wareButton, gbc);
        SARSRadioButton = new JRadioButton();
        SARSRadioButton.setText("SARS");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(SARSRadioButton, gbc);
        final JPanel spacer10 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(spacer10, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
