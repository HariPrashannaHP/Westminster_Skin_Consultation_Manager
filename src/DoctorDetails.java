import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

public class DoctorDetails extends JFrame{
    JButton backBtn;
    JButton sortBtn;
    String[] columnNames = {"First Name", "Surname", "Date of Birth", "Mobile Number", " License Number", "Specialization"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    public DoctorDetails() {
        this.setTitle("Doctor Information");
        this.setLayout(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setBounds(150, 45, 1200, 700);
        this.setResizable(false);
        JLabel background = new JLabel(new ImageIcon("src/Images/doctor_appointment_app_img.png"));
        this.add(background);
        background.setBounds(0, 0, 1200, 700);
        JLabel title = new JLabel("Doctor Information");
        //place the title in the middle of the top of the frame
        title.setBounds(455, 30, 300, 50);
        title.setFont(new Font("Glacial Indifference", Font.PLAIN, 30));
        title.setForeground(new Color(0, 0, 0, 255));
        background.add(title);
        background.add(createTable());
        Icon icon = new ImageIcon("D:\\IIT\\IIT\\Second Year\\OOP\\OOP_CW_w1899321\\src\\Images\\BackButton.png");
        backBtn = new JButton(icon);
        backBtn.setOpaque(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusable(false);
        //place the backBtn in the top left corner
        backBtn.setBounds(15, 20, 70, 70);
        background.add(backBtn);
        sortBtn = new JButton("SORT BY SURNAME");
        //place the sortBtn in the bottom center
        sortBtn.setBounds(500, 560, 200, 50);
        sortBtn.setBackground(new Color(100, 150, 175, 255));
        sortBtn.setForeground(new Color(0, 0, 0, 255));
        sortBtn.setOpaque(true);
        sortBtn.setBorderPainted(true);
        sortBtn.setBorder(null);
        sortBtn.setFont(new Font("Fira Sans", Font.PLAIN, 18));
        background.add(sortBtn);
        backButtonHandler backBtnHandler = new backButtonHandler();
        backBtn.addActionListener(backBtnHandler);
        sortButtonHandler sortBtnHandler = new sortButtonHandler();
        sortBtn.addActionListener(sortBtnHandler);
    }

    private JScrollPane createTable() {
        // Add rows to the model for each doctor
        for (Doctor doctor : WestminsterSkinConsultationManager.getDoctors()) {
            Object[] rowData = {doctor.getName(), doctor.getSurName(), doctor.getDateOfBirth(),
                    doctor.getMobileNumber(), doctor.getLicenceNumber(), doctor.getSpecialization()};
            model.addRow(rowData);
        }
        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setRowHeight(50);
        table.getColumnModel().getColumn(5).setPreferredWidth(200);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.setFocusable(false);

        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 18));
        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        table.getTableHeader().setPreferredSize(new Dimension(100, 50));
        table.getTableHeader().setBackground(new Color(100, 150, 175, 255));
        table.getTableHeader().setForeground(new Color(0, 0, 0, 255));
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255), 2));

        table.setFont(new Font("Arial", Font.PLAIN, 16));
        ((DefaultTableCellRenderer) table.getDefaultRenderer(Object.class)).setHorizontalAlignment(JLabel.CENTER);
        table.setShowGrid(true);
        table.setGridColor(Color.black);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 100, 1000, 400);
        return scrollPane;
    }

    private class backButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == backBtn) {
                new MainApp();
                dispose();
            }
        }
    }

    private class sortButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == sortBtn) {
                ArrayList<Doctor> sortedDoctors = new ArrayList<>(WestminsterSkinConsultationManager.getDoctors());
                sortedDoctors.sort(Comparator.comparing(Doctor::getSurName, String.CASE_INSENSITIVE_ORDER));
                model.setRowCount(0);
                for (Doctor doctor : sortedDoctors) {
                    Object[] rowData = {doctor.getName(), doctor.getSurName(), doctor.getDateOfBirth(),
                            doctor.getMobileNumber(), doctor.getLicenceNumber(), doctor.getSpecialization()};
                    model.addRow(rowData);
                }
            }
        }
    }

}
