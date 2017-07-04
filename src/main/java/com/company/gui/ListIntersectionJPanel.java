package com.company.gui;

import com.company.ListIntersectionBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.Instant;

/**
 * Created by igoru on 04-Jul-17.
 */
public class ListIntersectionJPanel extends JFrame implements ActionListener {

    private JLabel firstSizeLbl = new JLabel("First list size: ");
    private JLabel secondSizeLbl = new JLabel("Second list size: ");
    private JTextField firstList = new JTextField(50);
    private JTextField secondList = new JTextField(50);
    private JButton findBtn = new JButton("Find intersection");
    private final JRadioButton hashsetSelectorOneBtn;
    private final JRadioButton hashsetSelectorTwoBtn;
    private final JRadioButton hashsetSelectorDefBtn;
    private JLabel resultSetLbl = new JLabel("Result Set Size: ");
    private JLabel resultSet = new JLabel();
    private JLabel executedTimeLbl = new JLabel("Execution time: ");
    private JLabel executedTime = new JLabel();
    private JTextArea status = new JTextArea(5, 50);

    public ListIntersectionJPanel() throws HeadlessException {
        super("List intersection utility");

        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(firstSizeLbl, constraints);

        constraints.gridx = 1;
        mainPanel.add(firstList, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        mainPanel.add(secondSizeLbl, constraints);

        constraints.gridx = 1;
        mainPanel.add(secondList, constraints);

        hashsetSelectorOneBtn = new JRadioButton("Use first list");
        hashsetSelectorOneBtn.setActionCommand("hashsetone");

        hashsetSelectorTwoBtn = new JRadioButton("Use second list");
        hashsetSelectorTwoBtn.setActionCommand("hashsettwo");

        hashsetSelectorDefBtn = new JRadioButton("Use default (smallest) list");
        hashsetSelectorDefBtn.setActionCommand("hashsetdef");
        hashsetSelectorDefBtn.setSelected(true);

        ButtonGroup hashGroup = new ButtonGroup();
        hashGroup.add(hashsetSelectorOneBtn);
        hashGroup.add(hashsetSelectorTwoBtn);
        hashGroup.add(hashsetSelectorDefBtn);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.add(hashsetSelectorDefBtn);
        radioPanel.add(hashsetSelectorOneBtn);
        radioPanel.add(hashsetSelectorTwoBtn);
        mainPanel.add(radioPanel, constraints);


        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        findBtn.addActionListener(this);
        mainPanel.add(findBtn, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.WEST;
        mainPanel.add(resultSetLbl, constraints);
        constraints.gridx = 1;
        constraints.gridy = 4;
        mainPanel.add(resultSet, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        mainPanel.add(executedTimeLbl, constraints);

        constraints.gridx = 1;
        mainPanel.add(executedTime, constraints);

        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        mainPanel.add(status, constraints);

        mainPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Enter intersection data:"));

        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        status.setText("Ready\n");
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new ListIntersectionJPanel().setVisible(true));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        findBtn.setEnabled(false);
        status.setText(null);
        status.append("Generating lists...\n");
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                ListIntersectionBuilder bld = new ListIntersectionBuilder();
                bld.sizeOne(Integer.parseInt(firstList.getText()));
                bld.sizeTwo(Integer.parseInt(secondList.getText()));
                if (hashsetSelectorOneBtn.isSelected()) {
                    bld.listToUseForHashset(ListIntersectionBuilder.ListToUseForHashSet.FIRST);
                } else if (hashsetSelectorTwoBtn.isSelected()) {
                    bld.listToUseForHashset(ListIntersectionBuilder.ListToUseForHashSet.SECOND);
                } else {
                    bld.listToUseForHashset(ListIntersectionBuilder.ListToUseForHashSet.DEFAULT);
                }
                status.append("Computing intersection...\n");
                Instant start = Instant.now();
                int counter = 0;
                for (Long aLong : bld.build()) {
                    counter++;
                }
                Instant finish = Instant.now();
                final Duration between = Duration.between(start, finish);
                resultSet.setText(String.valueOf(counter));
                executedTime.setText(between.toString());
                return null;
            }

            @Override
            protected void done() {
                super.done();
                status.append("Ready\n");
                findBtn.setEnabled(true);
            }
        };
        worker.execute();
//        ListIntersectionBuilder bld = new ListIntersectionBuilder();
//        bld.sizeOne(Integer.parseInt(firstList.getText()));
//        bld.sizeTwo(Integer.parseInt(secondList.getText()));
//
//        if (hashsetSelectorOneBtn.isSelected()) {
//            bld.listToUseForHashset(ListIntersectionBuilder.ListToUseForHashSet.FIRST);
//        } else if (hashsetSelectorTwoBtn.isSelected()) {
//            bld.listToUseForHashset(ListIntersectionBuilder.ListToUseForHashSet.SECOND);
//        } else {
//            bld.listToUseForHashset(ListIntersectionBuilder.ListToUseForHashSet.DEFAULT);
//        }
//
//        status.append("Computing intersection...");
//        Instant start = Instant.now();
//        int counter = 0;
//        for (Long aLong : bld.build()) {
//            counter++;
//        }
//        Instant finish = Instant.now();
//        final Duration between = Duration.between(start, finish);
//        resultSet.setText(String.valueOf(counter));
//        executedTime.setText(between.toString());
//        status.append("Ready");
//        findBtn.setEnabled(true);
    }
}
