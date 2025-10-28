package pack;

import java.awt.*;
import java.sql.*;
import java.util.logging.*;
import javax.swing.*;

public class updateFrame extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(updateFrame.class.getName());

    // UI Components
    private JTextField idField, nameField, emailField, guardianField, addressField, dobField, courseField, enrolledField;
    private JButton loadButton, saveButton, clearButton;
    private JPanel bgPanel;

    public updateFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Update Student Record");
        setSize(600, 550);
        setResizable(false);

        bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 102, 204), getWidth(), getHeight(), new Color(0, 204, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bgPanel.setLayout(null);

        JLabel titleLabel = new JLabel("UPDATE STUDENT DETAILS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(100, 20, 400, 30);
        bgPanel.add(titleLabel);

        // Student ID
        JLabel idLabel = new JLabel("Student ID:");
        idLabel.setForeground(Color.WHITE);
        idLabel.setBounds(50, 70, 100, 25);
        bgPanel.add(idLabel);

        idField = new JTextField();
        idField.setBounds(160, 70, 150, 25);
        idField.setBackground(new Color(255, 255, 204));
        bgPanel.add(idField);

        loadButton = new JButton("Load Data");
        loadButton.setBounds(330, 70, 120, 25);
        loadButton.setBackground(new Color(255, 204, 0));
        loadButton.addActionListener(e -> loadStudent());
        bgPanel.add(loadButton);

        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(50, 110, 100, 25);
        bgPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(160, 110, 290, 25);
        nameField.setBackground(new Color(255, 255, 204));
        bgPanel.add(nameField);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBounds(50, 150, 100, 25);
        bgPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(160, 150, 290, 25);
        emailField.setBackground(new Color(255, 255, 204));
        bgPanel.add(emailField);

        // Guardian
        JLabel guardianLabel = new JLabel("Guardian:");
        guardianLabel.setForeground(Color.WHITE);
        guardianLabel.setBounds(50, 190, 100, 25);
        bgPanel.add(guardianLabel);

        guardianField = new JTextField();
        guardianField.setBounds(160, 190, 290, 25);
        guardianField.setBackground(new Color(255, 255, 204));
        bgPanel.add(guardianField);

        // Address
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setForeground(Color.WHITE);
        addressLabel.setBounds(50, 230, 100, 25);
        bgPanel.add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(160, 230, 290, 25);
        addressField.setBackground(new Color(255, 255, 204));
        bgPanel.add(addressField);

        // DOB
        JLabel dobLabel = new JLabel("DOB:");
        dobLabel.setForeground(Color.WHITE);
        dobLabel.setBounds(50, 270, 100, 25);
        bgPanel.add(dobLabel);

        dobField = new JTextField();
        dobField.setBounds(160, 270, 290, 25);
        dobField.setBackground(new Color(255, 255, 204));
        bgPanel.add(dobField);

        // Course
        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setForeground(Color.WHITE);
        courseLabel.setBounds(50, 310, 100, 25);
        bgPanel.add(courseLabel);

        courseField = new JTextField();
        courseField.setBounds(160, 310, 290, 25);
        courseField.setBackground(new Color(255, 255, 204));
        bgPanel.add(courseField);

        // Enrolled Date
        JLabel enrolledLabel = new JLabel("Enrolled Date:");
        enrolledLabel.setForeground(Color.WHITE);
        enrolledLabel.setBounds(50, 350, 100, 25);
        bgPanel.add(enrolledLabel);

        enrolledField = new JTextField();
        enrolledField.setBounds(160, 350, 290, 25);
        enrolledField.setBackground(new Color(255, 255, 204));
        bgPanel.add(enrolledField);

        // Save Button
        saveButton = new JButton("Save");
        saveButton.setBounds(160, 400, 100, 30);
        saveButton.setBackground(new Color(0, 204, 0));
        saveButton.addActionListener(e -> saveStudent());
        bgPanel.add(saveButton);

        // Clear Button
        clearButton = new JButton("Clear");
        clearButton.setBounds(270, 400, 100, 30);
        clearButton.setBackground(Color.WHITE);
        clearButton.addActionListener(e -> clearFields());
        bgPanel.add(clearButton);

        getContentPane().add(bgPanel);
    }

    private void loadStudent() {
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Student ID!");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID must be a number!");
            return;
        }

        String sql = "SELECT * FROM student WHERE id = ?";
        try (Connection con = DBUtility.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                emailField.setText(rs.getString("email"));
                guardianField.setText(rs.getString("guardian"));
                addressField.setText(rs.getString("address"));
                dobField.setText(rs.getString("dob"));
                courseField.setText(rs.getString("course"));
                enrolledField.setText(rs.getString("enrolled_date"));
            } else {
                JOptionPane.showMessageDialog(this, "No student found with ID: " + id);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void saveStudent() {
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Student ID!");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID must be numeric!");
            return;
        }

        String sql = "UPDATE student SET name=?, email=?, guardian=?, address=?, dob=?, course=?, enrolled_date=? WHERE id=?";

        try (Connection con = DBUtility.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nameField.getText().trim());
            ps.setString(2, emailField.getText().trim());
            ps.setString(3, guardianField.getText().trim());
            ps.setString(4, addressField.getText().trim());
            ps.setString(5, dobField.getText().trim());
            ps.setString(6, courseField.getText().trim());
            ps.setString(7, enrolledField.getText().trim());
            ps.setInt(8, id);

            int result = ps.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Student updated successfully!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "No student found with ID: " + id);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        guardianField.setText("");
        addressField.setText("");
        dobField.setText("");
        courseField.setText("");
        enrolledField.setText("");
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(() -> new updateFrame().setVisible(true));
    }
}
