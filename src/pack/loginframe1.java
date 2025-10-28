package pack;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class loginframe1 extends JFrame {

    public loginframe1() {
        initComponents();
        setLocationRelativeTo(null); // center the frame
        setTitle("Login - Student Management System");
        
        // Customize UI
        customizeUI();
    }

    private void customizeUI() {
        // Background image
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/images/1759334552361~2.png"));
        Image img = bgIcon.getImage().getScaledInstance(500, 410, Image.SCALE_SMOOTH);
        jLabel1.setIcon(new ImageIcon(img));

        // Textfield styles
        jTextField1.setBackground(new Color(255, 255, 204));
        jTextField1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jPasswordField1.setBackground(new Color(255, 255, 204));
        jPasswordField1.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Login button styles
        jButton1.setFont(new Font("Segoe UI", Font.BOLD, 16));
        jButton1.setBackground(new Color(0, 102, 204));
        jButton1.setForeground(Color.WHITE);
        jButton1.setFocusPainted(false);
        jButton1.setBorderPainted(false);
        jButton1.setOpaque(true);
        jButton1.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private boolean checklogin() {
        try (Connection con = DBUtility.getConnection()) {
            String sql = "SELECT * FROM login WHERE uname = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, jTextField1.getText().trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String pass = rs.getString("pwd");
                if (pass.equals(new String(jPasswordField1.getPassword()))) {
                    new DashboardFrame().setVisible(true);
                    dispose();
                    return true;
                }
            }
            JOptionPane.showMessageDialog(this, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPasswordField1 = new JPasswordField();
        jButton1 = new JButton();
        jLabel2 = new JLabel();
        jTextField1 = new JTextField();
        jLabel3 = new JLabel();
        jLabel1 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        getContentPane().add(jPasswordField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 190, -1));

        jButton1.setText("LOGIN");
        jButton1.addActionListener(evt -> checklogin());
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 290, -1, -1));

        jLabel2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        jLabel2.setForeground(new Color(255, 255, 204));
        jLabel2.setText("PASSWORD");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 243, -1, -1));

        jTextField1.setBackground(new Color(255, 255, 204));
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 219, 190, -1));

        jLabel3.setFont(new Font("Segoe UI", Font.BOLD, 12));
        jLabel3.setForeground(new Color(255, 255, 204));
        jLabel3.setText("USERNAME");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, -1, -1));

        jLabel1.setIcon(new ImageIcon(getClass().getResource("/images/1759334552361~2.png")));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -20, 500, 410));

        pack();
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        EventQueue.invokeLater(() -> new loginframe1().setVisible(true));
    }

    // Variables declaration
    private JButton jButton1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPasswordField jPasswordField1;
    private JTextField jTextField1;
}
