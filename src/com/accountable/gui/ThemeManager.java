package com.accountable.gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ThemeManager {

    public enum Theme {
        LIGHT, DARK
    }

    private static Theme currentTheme = Theme.LIGHT;

    public static Theme getCurrentTheme() {
        return currentTheme;
    }


    public static void applyTheme(JFrame frame, Theme theme) {
        currentTheme = theme;
        applyThemeToComponent(frame.getContentPane(), theme);
        SwingUtilities.updateComponentTreeUI(frame);
        frame.repaint();
    }

    private static void applyThemeToComponent(Component component, Theme theme) {
        if (theme == Theme.LIGHT) {
            setLightTheme(component);
        } else {
            setDarkTheme(component);
        }

        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                applyThemeToComponent(child, theme);
            }
        }
    }

    private static void setLightTheme(Component component) {
        Color backgroundColor = new Color(220, 220, 220); // Light gray
        Color foregroundColor = Color.BLACK; // Black
        component.setBackground(backgroundColor);
        component.setForeground(foregroundColor);

        applyToJComponent(component, foregroundColor);
    }

    private static void setDarkTheme(Component component) {
        Color backgroundColor = new Color(64, 64, 64); // Dark gray
        Color foregroundColor = Color.WHITE; // White
        component.setBackground(backgroundColor);
        component.setForeground(foregroundColor);

        applyToJComponent(component, foregroundColor);
    }

    private static void applyToJComponent(Component component, Color foregroundColor) {
        if (component instanceof JComponent) {
            JComponent jComponent = (JComponent) component;
            Border border = jComponent.getBorder();
            if (border instanceof TitledBorder) {
                ((TitledBorder) border).setTitleColor(foregroundColor);
            }
        }
    }
}
