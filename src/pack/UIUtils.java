package pack;

import java.awt.*;
import javax.swing.*;

/**
 * UI utility class to apply consistent modern styles
 * to buttons, frames, and panels across the application.
 */
public class UIUtils {

    private static final Color PRIMARY_COLOR = new Color(21, 101, 192); // Medium blue
    private static final Font DEFAULT_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    // Style the entire frame (background and font)
    public static void styleFrame(JFrame frame) {
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setFont(DEFAULT_FONT);
    }

    // Style any JButton or AbstractButton (flat, modern)
    public static void styleButton(AbstractButton button) {
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
    }

    // Optional — style panels with consistent color
    public static void stylePanel(JPanel panel) {
        panel.setBackground(Color.WHITE);
    }

    // Optional — create a white “card” with subtle shadow (if needed)
    public static JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        return panel;
    }
}
