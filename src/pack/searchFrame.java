package pack;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class searchFrame extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(searchFrame.class.getName());

    public searchFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Search student by ID or Name
    private void searchStudent() {
        String data = jTextField1.getText().trim();
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter ID or Name to search!");
            return;
        }

        try (Connection con = DBUtility.getConnection()) {
            PreparedStatement ps;
            if (data.matches("\\d+")) {
                // Search by ID
                ps = con.prepareStatement("SELECT * FROM student WHERE id = ?");
                ps.setInt(1, Integer.parseInt(data));
            } else {
                // Search by Name (partial match)
                ps = con.prepareStatement("SELECT * FROM student WHERE name LIKE ?");
                ps.setString(1, "%" + data + "%");
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String info = "<html>"
                        + "<b>ID:</b> " + rs.getInt("id") + "<br>"
                        + "<b>Name:</b> " + rs.getString("name") + "<br>"
                        + "<b>Email:</b> " + rs.getString("email") + "<br>"
                        + "<b>Guardian:</b> " + rs.getString("guardian") + "<br>"
                        + "<b>Address:</b> " + rs.getString("address") + "<br>"
                        + "<b>DOB:</b> " + rs.getDate("dob") + "<br>"
                        + "<b>Course:</b> " + rs.getString("course") + "<br>"
                        + "<b>Enrolled Date:</b> " + rs.getDate("enrolled_date")
                        + "</html>";
                jLabel2.setText(info);
            } else {
                JOptionPane.showMessageDialog(this, "No student found!");
                jLabel2.setText("");
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Database error while searching student", ex);
            JOptionPane.showMessageDialog(this, "Database error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Search Student");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jLabel1.setText("Enter ID or Name:");

        jTextField1.setBackground(new java.awt.Color(255, 255, 204));

        jButton1.setBackground(new java.awt.Color(153, 204, 255));
        jButton1.setText("Search");
        jButton1.addActionListener(evt -> searchStudent());

        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 12));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new searchFrame().setVisible(true));
    }

    // Variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
}
