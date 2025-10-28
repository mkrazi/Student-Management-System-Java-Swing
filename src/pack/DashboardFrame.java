package pack;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class DashboardFrame extends JFrame {

    private JLabel backgroundLabel;

    public DashboardFrame() {
        initComponents();
        setTitle("Dashboard - Student Management System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // Layered pane for background and buttons
        JLayeredPane layeredPane = new JLayeredPane();

        // Background label with scaled image
        backgroundLabel = new JLabel();
        setBackgroundImage(); // initial scale
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        // Sidebar panel
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 0, 15));
        buttonPanel.setOpaque(false); // keep transparent for background
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(150, 60, 150, 60));

     

        // Menu bar (solid color)
        setJMenuBar(createMenuBar());

        setContentPane(layeredPane);

        // Handle window resize to scale background
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
                setBackgroundImage();
            }
        });
    }

    // Scale the background image to fit the frame
    private void setBackgroundImage() {
        ImageIcon icon = new ImageIcon(getClass().getResource("../images/img1.png"));
        Image img = icon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(img));
    }
    // Create solid dashboard buttons


    // Menu bar (solid, non-transparent)
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(0, 51, 102)); // solid blue
        menuBar.setForeground(Color.BLACK);

        JMenu menuFile = new JMenu("File");
        menuFile.setForeground(Color.BLACK);
        menuFile.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JMenuItem itemExit = new JMenuItem("Exit");
        itemExit.addActionListener(e -> System.exit(0));
        menuFile.add(itemExit);

        JMenu menuStudent = new JMenu("Student");
        menuStudent.setForeground(Color.BLACK);
        menuStudent.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JMenuItem itemAdd = new JMenuItem("Add Student");
        JMenuItem itemView = new JMenuItem("View Students");
        JMenuItem itemUpdate = new JMenuItem("Update Student");
        JMenuItem itemDelete = new JMenuItem("Delete Student");

        itemAdd.addActionListener(e -> new addstudent().setVisible(true));
        itemView.addActionListener(e -> new viewstudent().setVisible(true));
        itemUpdate.addActionListener(e -> new updateFrame().setVisible(true));
        itemDelete.addActionListener(e -> new DeleteStudent().setVisible(true));

        menuStudent.add(itemAdd);
        menuStudent.add(itemView);
        menuStudent.add(itemUpdate);
        menuStudent.add(itemDelete);

      

        JMenu menuLogout = new JMenu("Logout");
        menuLogout.setForeground(Color.BLACK);
        menuLogout.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JMenuItem itemLogout = new JMenuItem("Logout");
        itemLogout.addActionListener(e -> {
            new loginframe1().setVisible(true);
            dispose();
        });
        menuLogout.add(itemLogout);

        menuBar.add(menuFile);
        menuBar.add(menuStudent);
     
        menuBar.add(menuLogout);

        return menuBar;
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1200, 700);
    }

    public static void main(String[] args) {
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) { 
        }
        EventQueue.invokeLater(() -> new DashboardFrame().setVisible(true));
    }
}
