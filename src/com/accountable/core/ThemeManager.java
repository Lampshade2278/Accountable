package com.accountable.core;

import javax.swing.*;
import java.awt.*;

public class ThemeManager {
    // Define color schemes for light and dark themes
    private static final Color backgroundLight = Color.WHITE;
    private static final Color foregroundLight = Color.BLACK;
    private static final Color backgroundDark = new Color(45, 45, 45); // Dark gray
    private static final Color foregroundDark = Color.WHITE;

    // Method to apply a theme to a container and its child components
    public static void applyTheme(Container container, boolean isDarkMode) {
        Color background = isDarkMode ? backgroundDark : backgroundLight;
        Color foreground = isDarkMode ? foregroundDark : foregroundLight;

        // Apply theme to the container
        container.setBackground(background);
        container.setForeground(foreground);

        // Apply theme to child components
        for (Component comp : container.getComponents()) {
            comp.setBackground(background);
            comp.setForeground(foreground);

            // Recursive call for nested containers
            if (comp instanceof Container) {
                applyTheme((Container) comp, isDarkMode);
            }

            // Additional specific component styling can be added here
        }
    }

    // Example usage in a main method (can be moved to appropriate application startup point)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Theme Manager Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLayout(new FlowLayout());

            // Add some components for demonstration
            frame.add(new JLabel("Hello, World!"));
            frame.add(new JButton("Click Me"));

            // Toggle button to switch themes
            JToggleButton toggleThemeButton = new JToggleButton("Toggle Theme");
            toggleThemeButton.addActionListener(e -> {
                boolean isDarkMode = toggleThemeButton.isSelected();
                applyTheme(frame, isDarkMode);
            });
            frame.add(toggleThemeButton);

            // Initial theme application (can be set based on user preference)
            applyTheme(frame, false); // Start with light mode

            frame.setVisible(true);
        });
    }
}
