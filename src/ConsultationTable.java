import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ConsultationTable extends JFrame {

    static ArrayList<Consultation> consultations = new ArrayList<>();
    JLabel notesImg = new JLabel();
    JPanel panel = new JPanel();
    JButton btn1 = new JButton("View Details");
    JButton backBtn;

    static Border border = BorderFactory.createLineBorder(Color.BLACK, 5);


    public ConsultationTable(){


        this.setTitle("Consultation Details");
        this.setLayout(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setBounds(150, 60, 1200, 700);
        this.setResizable(false);
        JLabel background = new JLabel(new ImageIcon("src/Images/doctor_appointment_app_img.png"));
        this.add(background);
        background.setBounds(0, 0, 1200, 700);
        JLabel title = new JLabel("Consultation Details");
        //place the title in the middle of the top of the frame
        title.setBounds(455, 30, 300, 50);
        title.setFont(new Font("Glacial Indifference", Font.PLAIN, 30));
        title.setForeground(new Color(0, 0, 0, 255));
        background.add(title);
        Icon icon = new ImageIcon("D:\\IIT\\IIT\\Second Year\\OOP\\OOP_CW_w1899321\\src\\Images\\BackButton.png");
        backBtn = new JButton(icon);
        backBtn.setOpaque(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusable(false);
        //place the backBtn in the top left corner
        backBtn.setBounds(15, 20, 70, 70);


        btn1.setBounds(500, 540, 200, 50);
        btn1.setBackground(new Color(100, 150, 175, 255));
        btn1.setForeground(new Color(0, 0, 0, 255));
        btn1.setBorder(border);
        btn1.setFont(new Font("Fira Sans", Font.PLAIN, 18) );


        try {
            FileInputStream fis = new FileInputStream("consultation.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            consultations.clear();
            while (true) {
                try {
                    Consultation consultation = (Consultation) ois.readObject();
                    consultations.add(consultation);
                } catch (EOFException e) {
                    break;
                }
            }
            System.out.println("Data has been loaded to the system successfully.");
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ThERE IS NO CONSULTATION RIGHT NOW");
        }



        String[] column = {"NAME", "SURNAME","DATE","TIME"};
        JTable jt = new JTable();
        DefaultTableModel tblModel = new DefaultTableModel(column, 0);


            for (int i = 0; i < consultations.size(); i++) {
                String name = consultations.get(i).getPatient().get(0).getName();
                String surname = consultations.get(i).getPatient().get(0).getSurName();
                String date= String.valueOf(consultations.get(i).getDate());
                String time= String.valueOf(consultations.get(i).getStartTime());

                Object[] objs = {name, surname,date,time};
                tblModel.addRow(objs);

            }

        jt.setPreferredScrollableViewportSize(new Dimension(500, 70));
        jt.setFillsViewportHeight(true);
        jt.setRowHeight(50);
        jt.setFocusable(false);

        jt.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 16));
        ((DefaultTableCellRenderer) jt.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        jt.getTableHeader().setPreferredSize(new Dimension(100, 50));
        jt.getTableHeader().setBackground(new Color(100, 150, 175, 255));
        jt.getTableHeader().setForeground(new Color(0, 0, 0, 255));
        jt.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 255), 2));

        jt.setFont(new Font("Arial", Font.BOLD, 14));
        ((DefaultTableCellRenderer) jt.getDefaultRenderer(Object.class)).setHorizontalAlignment(JLabel.CENTER);
        jt.setShowGrid(true);
        jt.setGridColor(Color.black);


        jt.setModel(tblModel);
        jt.setPreferredScrollableViewportSize(new Dimension(500, 70));
        jt.setFillsViewportHeight(true);
        jt.setRowHeight(50);



        jt.setBounds(30, 40, 200, 300);

        JScrollPane sp = new JScrollPane(jt);



        sp.setSize(450, 200);
        sp.setBounds(100, 100, 1000, 400);
        background.add(btn1);
        background.add(sp);
        background.add(backBtn);


        backBtn.addActionListener(e -> {
            MainApp window = new MainApp();
            window.setVisible(true);
            dispose();
        });


        btn1.addActionListener(e -> {

            ConsultationDetails window = null;
            try {
                window = new ConsultationDetails();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            try{
            int row;
            for(row=jt.getSelectedRow();row< jt.getRowCount();row++) {
                DefaultTableModel model = (DefaultTableModel) jt.getModel();
                String name = (String) model.getValueAt(row, 0);
                String surname = (String) model.getValueAt(row, 1);
                String dateOfBirth = String.valueOf(consultations.get(row).getPatient().get(0).getDateOfBirth());
                String mobileNo = String.valueOf(consultations.get(row).getPatient().get(0).getMobileNumber());
                String patientID = String.valueOf(consultations.get(row).getPatient().get(0).getPatientID());
                String cost = String.valueOf(consultations.get(row).getCost());
                String bookingDate = String.valueOf(consultations.get(row).getDate());
                String bookingTime = String.valueOf(consultations.get(row).getStartTime());
                String notes = Arrays.toString(consultations.get(row).getEncryptedNote());
                String doctorName = consultations.get(row).getDoctor().getName();






                if(row== jt.getSelectedRow()) {
                    window.label6.setText("Name          : ");
                    window.label1.setText(name);
                    window.label7.setText("Surname       :");
                    window.label2.setText(surname);
                    window.label8.setText("Mobile No     :");
                    window.label3.setText(mobileNo);
                    window.label9.setText("Date Of Birth :");
                    window.label5.setText(dateOfBirth);
                    window.label10.setText("Patient ID    :");
                    window.label13.setText(patientID);
                    window.label11.setText("Cost          :");
                    window.label14.setText(cost);
                    window.label19.setText("Notes         :");
                    window.label20.setText(notes);
                    window.label12.setText("Booking Date  :");
                    window.label15.setText(bookingDate);
                    window.label17.setText("Booking Time  :");
                    window.label16.setText(bookingTime);
                    window.label22.setText("Appt Doctor   :");
                    window.label23.setText(doctorName);

                    window.setSize(500, 600);
                    window.setResizable(false);

                    window.setVisible(true);
                }




            }}
             catch(Exception error){
                JOptionPane.showMessageDialog(null,"Please Select A Consultation And Click Me!!");

                }


        });
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ConsultationTable frame= new ConsultationTable();


    }
}
