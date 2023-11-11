package com.accountable.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.accountable.core.Report; // Assuming a Report class exists in the core package

public class ReportPanel extends JPanel {

    private JButton generateReportButton;
    private JTextArea reportTextArea;

    public ReportPanel() {
        setLayout(new BorderLayout());
        
        JLabel headerLabel = new JLabel("Financial Reports", JLabel.CENTER);
        add(headerLabel, BorderLayout.NORTH);

        JPanel reportControlPanel = new JPanel();
        reportControlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(new GenerateReportActionListener());
        reportControlPanel.add(generateReportButton);

        add(reportControlPanel, BorderLayout.SOUTH);

        reportTextArea = new JTextArea();
        reportTextArea.setEditable(false);
        add(new JScrollPane(reportTextArea), BorderLayout.CENTER);
    }

    private class GenerateReportActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Simulated report generation logic
            Report report = new Report();
            // Assume Report class has a method to generate report content
            String reportContent = report.generateReportContent();
            reportTextArea.setText(reportContent);

            JOptionPane.showMessageDialog(ReportPanel.this, 
                    "Report Generated", 
                    "Report", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
