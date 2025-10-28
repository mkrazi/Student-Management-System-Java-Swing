package pack;

import java.awt.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.*;

public class viewstudent extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(viewstudent.class.getName());

    public viewstudent() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("View Students - Student Management System");
        loadStudentData();
    }

    // --- Load student details from database ---
    private void loadStudentData() {
        try (Connection con = DBUtility.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM student");
             ResultSet rs = ps.executeQuery()) {

            DefaultTableModel model = (DefaultTableModel) jTableStudents.getModel();
            model.setRowCount(0); // clear existing rows

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("guardian"),
                    rs.getString("address"),
                    rs.getDate("dob"),
                    rs.getString("course"),
                    rs.getDate("enrolled_date")
                };
                model.addRow(row);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error loading student data", ex);
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- UI Components Initialization ---
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableStudents = new javax.swing.JTable();
        jLabelTitle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(Color.WHITE);

        // --- Title Label ---
        jLabelTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        jLabelTitle.setForeground(Color.BLACK);
        jLabelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelTitle.setText("All Registered Students");

        // --- Table Model ---
        jTableStudents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Student ID", "Name", "Email", "Guardian", "Address", "Date of Birth", "Course", "Enrolled Date"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // make non-editable
            }
        });

        // --- Table Styling ---
        jTableStudents.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTableStudents.setRowHeight(28);
        jTableStudents.setGridColor(new Color(230, 230, 230));
        jTableStudents.setSelectionBackground(new Color(0, 102, 204));
        jTableStudents.setSelectionForeground(Color.BLACK);
        jTableStudents.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTableStudents.setFillsViewportHeight(true);

        // --- Header Styling ---
        JTableHeader header = jTableStudents.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(0, 51, 102));
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);

        // --- Center Alignment ---
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < jTableStudents.getColumnCount(); i++) {
            if (!jTableStudents.getColumnName(i).equals("Address")) {
                jTableStudents.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        jScrollPane1.setViewportView(jTableStudents);

        // --- Layout ---
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
                .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(20)
                    .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                    .addContainerGap())
        );

        pack();
    }

    // --- Main for testing standalone ---
    public static void main(String args[]) {
        try {
    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
            UIManager.setLookAndFeel(info.getClassName());
            break;
        }
    }
} catch (Exception ex) {
    Logger.getLogger(viewstudent.class.getName()).log(Level.SEVERE, null, ex);
}

    }

    // --- Variables ---
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableStudents;
}
