import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class ConsultationDetails extends JFrame implements Serializable {



    JLabel label1 = new JLabel();
    JLabel label2 = new JLabel();
    JLabel label3 = new JLabel();
    JLabel label4 = new JLabel();
    JLabel label5 = new JLabel();
    JLabel label6 = new JLabel();
    JLabel label7 = new JLabel();
    JLabel label8 = new JLabel();
    JLabel label9 = new JLabel();
    JLabel label10 = new JLabel();
    JLabel label11 = new JLabel();
    JLabel label12 = new JLabel();
    JLabel label13 = new JLabel();
    JLabel label14 = new JLabel();
    JLabel label15 = new JLabel();
    JLabel label16 = new JLabel();
    JLabel label17 = new JLabel();

    JLabel label18 = new JLabel();
    JLabel label19 = new JLabel();
    JLabel label20 = new JLabel();
    JLabel label21 = new JLabel();

    JLabel label22 = new JLabel();
    JLabel label23= new JLabel();



    public  ConsultationDetails() throws IOException, ClassNotFoundException {

        JLabel background = new JLabel(new ImageIcon("src/Images/doctor_appointment_app_img.png"));
        this.add(background);
        background.setBounds(0, 0, 1200, 700);
        this.setTitle("Consultation Details");
        this.setLayout(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setBounds(320,100,800,600);
        this.setResizable(false);
        label4.setText("Patient Details");
        label4.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        label4.setBounds(25, 10, 200, 30);
        label6.setBounds(0, 100, 200, 30);
        label1.setBounds(90, 100, 200, 30);
        label7.setBounds(0, 150, 200, 30);
        label2.setBounds(90, 150, 200, 30);
        label8.setBounds(0, 200, 200, 30);
        label3.setBounds(90, 200, 200, 30);
        label9.setBounds(0, 250, 200, 30);
        label5.setBounds(90, 250, 200, 30);
        label10.setBounds(0, 300, 200, 30);
        label13.setBounds(90, 300, 200, 30);
        label11.setBounds(0, 350, 200, 30);
        label14.setBounds(90, 350, 200, 30);
        label12.setBounds(0, 400, 200, 30);
        label15.setBounds(90, 400, 200, 30);
        label17.setBounds(0, 450, 200, 30);
        label16.setBounds(90, 450, 200, 30);
        label19.setBounds(0, 500, 200, 30);
        label20.setBounds(90, 500, 200, 30);

        label18.setBounds(0, 500, 200, 30);
        label22.setBounds(0,50,200,30);
        label23.setBounds(90,50,200,30);
        label21.setBounds(150, 500, 200, 30);




        background.add(label1);
        background.add(label2);
        background.add(label3);
        background.add(label4);
        background.add(label5);
        background.add(label6);
        background.add(label7);
        background.add(label8);
        background.add(label9);
        background.add(label10);
        background.add(label11);
        background.add(label12);
        background.add(label13);
        background.add(label14);
        background.add(label15);
        background.add(label16);
        background.add(label17);
        background.add(label19);
        background.add(label20);
        background.add(label21);
        background.add(label22);
        background.add(label23);
        background.add(label18);





    }

}


