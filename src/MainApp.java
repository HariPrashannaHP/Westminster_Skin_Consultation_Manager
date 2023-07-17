import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainApp extends JFrame  {
    JButton viewDoctorBtn;
    JButton addConsultationBtn;
    JButton viewConsultationBtn;

    static Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
    public MainApp (){
        this.setTitle("Westminster Skin Consultation Manger");
        this.setLayout(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setBounds(320,100,800,600);
        this.setResizable(false);
        JLabel background=new JLabel(new ImageIcon("D:\\IIT\\IIT\\Second Year\\OOP\\OOP_CW_w1899321\\src\\Images\\doctor_appointment_app_img.png"));
        this.add(background);
        background.setBounds(0,0,800,600);
        viewDoctorBtn = new JButton("View Doctors List");
        viewDoctorBtn.setBounds(520, 200, 200, 50);
        viewDoctorBtn.setBackground(new Color(100, 150, 175, 255));
        viewDoctorBtn.setForeground(new Color(0, 0, 0, 255));
        viewDoctorBtn.setOpaque(true);
        viewDoctorBtn.setBorderPainted(true);
        viewDoctorBtn.setBorder(null);
        viewDoctorBtn.setFont(new Font("Fira Sans", Font.PLAIN, 18));
        viewDoctorBtn.setBorder(border);
        background.add(viewDoctorBtn);
        viewDoctorsBtnHandler handler = new viewDoctorsBtnHandler();
        viewDoctorBtn.addActionListener(handler);
        addConsultationBtn = new JButton("Add A Consultation");
        addConsultationBtn.setBounds(520, 300, 200, 50);
        addConsultationBtn.setBackground(new Color(100, 150, 175, 255));
        addConsultationBtn.setForeground(new Color(0, 0, 0, 255));
        addConsultationBtn.setOpaque(true);
        addConsultationBtn.setBorderPainted(true);
        addConsultationBtn.setBorder(null);
        addConsultationBtn.setFont(new Font("Fira Sans", Font.PLAIN, 18));
        addConsultationBtn.setBorder(border);
        background.add(addConsultationBtn);
        addConsultationBtnHandler handler1 = new addConsultationBtnHandler();
        addConsultationBtn.addActionListener(handler1);
        viewConsultationBtn = new JButton("View All Consultations");
        viewConsultationBtn.setBounds(520, 400, 200, 50);
        viewConsultationBtn.setBackground(new Color(100, 150, 175, 255));
        viewConsultationBtn.setForeground(new Color(0, 0, 0, 255));
        viewConsultationBtn.setOpaque(true);
        viewConsultationBtn.setBorderPainted(true);
        viewConsultationBtn.setBorder(null);
        viewConsultationBtn.setFont(new Font("Fira Sans", Font.PLAIN, 18));
        viewConsultationBtn.setBorder(border);
        background.add(viewConsultationBtn);


        viewConsultationBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConsultationTable window = new ConsultationTable();
                window.setVisible(true);

            }
        });
    }
    private class viewDoctorsBtnHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == viewDoctorBtn) {
                dispose();
                new DoctorDetails();
            }
        }
    }

    private class addConsultationBtnHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addConsultationBtn) {
                dispose();
                try {
                    new CheckAvailability();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }


}

