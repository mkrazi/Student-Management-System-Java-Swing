package pack;

import java.awt.Color;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DeleteStudent extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(DeleteStudent.class.getName());

    public DeleteStudent() {
        initComponents();
        setLocationRelativeTo(null);
        loadStudents();
    }

    // Load all students into the table
    private void loadStudents() {
        try (Connection con = DBUtility.getConnection()) {
            String sql = "SELECT * FROM student";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                Object[] rowData = {
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("guardian"),
                    rs.getString("address"),
                    rs.getDate("dob"),
                    rs.getString("course"),
                    rs.getDate("enrolled_date")
                };
                model.addRow(rowData);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error loading students", ex);
            JOptionPane.showMessageDialog(this, "Error loading students from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Delete selected rows
    private void deleteSelectedRows() {
        int[] selectedRows = jTable1.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "No row selected.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the selected student?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try (Connection con = DBUtility.getConnection()) {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                int row = selectedRows[i];
                int id = (int) model.getValueAt(row, 0);
                PreparedStatement pst = con.prepareStatement("DELETE FROM student WHERE id=?");
                pst.setInt(1, id);
                pst.executeUpdate();
                model.removeRow(row);
            }
            JOptionPane.showMessageDialog(this, "Selected student deleted successfully.");
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error deleting students", ex);
            JOptionPane.showMessageDialog(this, "Error deleting students from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Delete Students");

        jTable1.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "Name", "Email", "Guardian", "Address", "DOB", "Course", "Enrolled Date"
            }
        ) {
            Class[] types = new Class [] {
                Integer.class, String.class, String.class, String.class, String.class, java.sql.Date.class, String.class, java.sql.Date.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        btnDelete.setBackground(new java.awt.Color(255, 51, 51));
        btnDelete.setForeground(Color.BLACK);
        btnDelete.setText("Delete Student");
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnDelete.addActionListener(evt -> deleteSelectedRows());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(300, 300, 300)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
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

        java.awt.EventQueue.invokeLater(() -> new DeleteStudent().setVisible(true));
    }

    // Variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
}
