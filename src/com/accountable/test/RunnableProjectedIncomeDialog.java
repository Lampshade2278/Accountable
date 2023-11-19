package com.accountable.test;
import com.accountable.gui.Dialogs.ProjectedIncomeDialog;

import javax.swing.*;

public class RunnableProjectedIncomeDialog {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create an instance of the dialog
                ProjectedIncomeDialog dialog = new ProjectedIncomeDialog(null, true);



                // Display the dialog
                dialog.setVisible(true);

                // Output the results
                System.out.println("Projected Income: " + dialog.getProjectedIncome());
                System.out.println("Income Title: " + dialog.getIncomeTitle());
            }
        });
    }
}
