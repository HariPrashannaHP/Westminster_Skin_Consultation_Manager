import javax.crypto.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;

public class AddConsultation extends JFrame {

    private static BufferedImage image;
    byte[] encryptedImageData;
    SecretKey key;
    KeyPair pair;
    JFormattedTextField name;
    JFormattedTextField surName;
    JFormattedTextField birthDate;
    JFormattedTextField MobileNumber;
    JFormattedTextField patientID;
    JFormattedTextField cost;
    JTextArea notes;
    JButton uploadImage;
    JButton costGetter;
    JButton submit;

    static ArrayList<Consultation> consultationDetails = new ArrayList<>();

   ArrayList<Patient> patients = new ArrayList<>();


    static Border border = BorderFactory.createLineBorder(Color.BLACK, 5);

    public AddConsultation() throws IOException, ClassNotFoundException {

        loadDataFromFile();

        this.setTitle("Add Consultation");
        this.setLayout(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setBounds(300,100,850,1200);
        this.setResizable(false);
        JLabel background=new JLabel(new ImageIcon("D:\\IIT\\IIT\\Second Year\\OOP\\OOP_CW_w1899321\\src\\Images\\doctor_appointment_app_img.png"));
        background.setBounds(0,0,1000,1000);
        this.add(background);
        JLabel title = new JLabel("Patient Details");
        title.setBounds(300, 13, 300, 50);
        title.setFont(new Font("Glacial Indifference", Font.PLAIN, 30));
        title.setForeground(new Color(0, 0, 0, 255));
        background.add(title);
        background.add(label("Name *", 120, 100));
        background.add(label("Surname *", 520, 100));
        background.add(label("Date of Birth *", 120, 250));
        background.add(label("Phone Number *", 520, 250));
        background.add(label("Patient ID *", 120, 400));
        background.add(label("Cost", 520, 400));
        background.add(label("Notes", 120, 550));
        background.add(name());
        background.add(surname());
        background.add(birthDate());
        background.add(mobileNumber());
        background.add(patientID());
        background.add(cost());
        background.add(notes());
        background.add(getCostGetter());
        background.add(uploadImage());
        background.add(submit());
    }

    public JLabel label (String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 300, 50);
        label.setFont(new Font("Fira Sans", Font.PLAIN, 18));
        label.setForeground(new Color(0, 0, 0, 255));
        return label;
    }

    public JFormattedTextField name() {
        name = new Placeholder("Hari");
        name.setBounds(120, 150, 300, 50);
        name.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        name.setBorder(border);
      
        return name;
    }

    public JFormattedTextField surname() {
        surName = new Placeholder("Prashanna");
        surName.setBounds(520, 150, 300, 50);
        surName.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        surName.setBorder(border);
        return surName;
    }

    public JFormattedTextField birthDate() {
        birthDate = new Placeholder("23/09/2000");
        birthDate.setBounds(120, 300, 300, 50);
        birthDate.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        birthDate.setBorder(border);
        return birthDate;
    }

    public JFormattedTextField mobileNumber() {
        MobileNumber = new Placeholder("0778699987");
        MobileNumber.setBounds(520, 300, 300, 50);
        MobileNumber.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        MobileNumber.setBorder(border);
        return MobileNumber;
    }

    public JFormattedTextField patientID() {
        patientID = new Placeholder("123456");
        patientID.setBounds(120, 450, 300, 50);
        patientID.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        patientID.setBorder(border);
        return patientID;
    }

    public JFormattedTextField cost() {
        cost = new Placeholder("£15");
        cost.setBounds(520, 450, 300, 50);
        cost.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        cost.setBorder(border);
        cost.setEditable(false);
        return cost;
    }

    public String getCost () {
        String cost1 = cost.getText();
        String[] costs = cost1.split("£");
        return costs[1];
    }

    public JTextArea notes() {
        notes = new JTextArea();
        notes.setBounds(120, 600, 300, 100);
        notes.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        notes.setBorder(border);
        return notes;
    }

    public JButton uploadImage() {
        //https://www.geeksforgeeks.org/encrypt-and-decrypt-image-using-java/
        uploadImage = new JButton("UPLOAD IMAGE");
        uploadImage.setBounds(520, 550, 300, 50);
        uploadImage.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    image = ImageIO.read(selectedFile);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(image, "jpg", baos);
                    baos.flush();
                    byte[] imageData = baos.toByteArray();
                    baos.close();
                    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                    keyGenerator.init(128);
                    key = keyGenerator.generateKey();

                    cipher.init(Cipher.ENCRYPT_MODE, key);
                    encryptedImageData = cipher.doFinal(imageData);

                } catch (IOException | NoSuchPaddingException | IllegalBlockSizeException |
                         NoSuchAlgorithmException | BadPaddingException | InvalidKeyException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return uploadImage;
    }

    public void setTextCost() {
        LocalTime startTime = CheckAvailability.getStartTime();
        LocalTime endTime = CheckAvailability.getEndTime();
        assert startTime != null;
        int duration = (int) Duration.between(startTime, endTime).toHours();
        //loop in consultation array list to count the same patient id
        int count = 0;
        for (Consultation consultation : Consultation.getAllConsultations()) {
            if (consultation.getPatientId().equals(patientID.getText())) {
                count++;

            }
        }
        if (count == 0) {
            cost.setText("£" + (duration * 15));
        } else {
            cost.setText("£" + (duration * 25));
        }
    }

    public JButton getCostGetter () {
        costGetter = new JButton("GET COST");
        costGetter.setBounds(520,620,300,50);
        costGetter.addActionListener(e -> setTextCost());
        return costGetter;
    }

    public boolean isFilled() {
        if (MobileNumber.getText().equals("") || name.getText().equals("") || surName.getText().equals("") || birthDate.getText().equals("") || patientID.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill all the mandatory fields marked by * mark");
            return false;
        }  else {
            return true;
        }
    }

    //check if the entered phone number contains only numbers
    public boolean phNoIsNumeric() {
        return MobileNumber.getText().matches("[0-9]+") && MobileNumber.getText().length() == 10;
    }

    //check if the entered patient id contains only numbers
    public int patientIDIsNumeric() {
        try {
            return Integer.parseInt(patientID.getText());
        }catch (NumberFormatException e) {
            return 0;
        }
    }

    //check if entered name contains only letters
    public boolean nameIsAlpha() {
        for (char c : name.getText().toCharArray()) {
            if (!Character.isLetter(c))
                return false;
        }
        return true;
    }

    //check if entered surname contains only letters
    public boolean surNameIsAlpha() {
        for (char c : surName.getText().toCharArray()) {
            if (!Character.isLetter(c))
                return false;
        }
        return true;
    }

    //check if entered birthdate is in the correct format
    public LocalDate getBirthdate() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(birthDate.getText(), formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    //if note is not filled add a default note
    public String getNotes() {
        if (notes.getText().equals("")) {
            return "No notes";
        } else {
            return notes.getText();
        }
    }

    public byte[] getEncryptedNoteData() {
        byte[] cipherText;
        try {
            //https://www.tutorialspoint.com/java_cryptography/java_cryptography_decrypting_data.htm

            //Creating KeyPair generator object
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

            //Initializing the key pair generator
            keyPairGen.initialize(2048);

            //Generate the pair of keys
            pair = keyPairGen.generateKeyPair();

            //Getting the public key from the key pair
            PublicKey publicKey = pair.getPublic();

            //Creating a Cipher object
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            //Initializing a Cipher object
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            //Add data to the cipher
            byte [] input = getNotes().getBytes();
            cipher.update(input);

            //encrypting the data
            cipherText = cipher.doFinal();
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                 InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return cipherText;
    }
    //show error message if the entered phone number, patient id, name, surname or birthdate is not in the correct format
    public boolean isDetailsValid(){
        String errorMessage = "";
        if (isFilled()) {
            if (!phNoIsNumeric()) {
                errorMessage += "Invalid phone number\n";
            }
            if (patientIDIsNumeric() == 0) {
                errorMessage += "Invalid patient id\n";
            }
            if (!nameIsAlpha()) {
                errorMessage += "Invalid name\n";
            }
            if (!surNameIsAlpha()) {
                errorMessage += "Invalid surname\n";
            }
            if (getBirthdate() == null) {
                errorMessage += "Invalid birthdate (dd/mm/yyyy)\n";
            }
            if (Objects.equals(cost.getText(), "")){
                errorMessage += "get cost by pressing get cost button\n";
            }
            if (!errorMessage.equals("")) {
                JOptionPane.showMessageDialog(null, errorMessage);
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }


    public Doctor getDoc () {
        for (Doctor doctor : WestminsterSkinConsultationManager.getDoctors()) {
            if (doctor.getLicenceNumber().equals(CheckAvailability.getDoctorName())) {
                return doctor;
            }
        }
        return null;
    }
    //create a button to submit details if isDetailsValid() returns true
    public JButton submit() {
        submit = new JButton("SUBMIT");
        submit.setBounds(520, 690, 300, 50);

        LocalDate dob = getBirthdate();
        submit.addActionListener(e -> {
            if (isDetailsValid()) {
                //create a new patient object
                patients.add(new Patient(name.getText(), surName.getText(), getBirthdate(), MobileNumber.getText(), patientID.getText()));
                //create a new consultation object
                Consultation consultation;
                if (encryptedImageData != null) {
                    consultation = new Consultation(getDoc(), patients, CheckAvailability.getDate(), CheckAvailability.getStartTime(), CheckAvailability.getEndTime(), Double.parseDouble(getCost()), getEncryptedNoteData(), encryptedImageData,getPatientId());
                    consultationDetails.add(new Consultation(getDoc(),patients,CheckAvailability.getDate(), CheckAvailability.getStartTime(), CheckAvailability.getEndTime(), Double.parseDouble(getCost()), getEncryptedNoteData(), encryptedImageData,getPatientId()));
                    try {
                        saveDataToFile();

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    encryptedImageData = null;
                } else {
                    consultation = new Consultation(getDoc(), patients, CheckAvailability.getDate(), CheckAvailability.getStartTime(), CheckAvailability.getEndTime(), Double.parseDouble(getCost()), getEncryptedNoteData(),patientID.getText());
                    consultationDetails.add(new Consultation(getDoc(),patients,CheckAvailability.getDate(), CheckAvailability.getStartTime(), CheckAvailability.getEndTime(), Double.parseDouble(getCost()), getEncryptedNoteData(),getPatientId()));
                    try {
                        saveDataToFile();

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                Consultation.getAllConsultations().add(consultation);
                //close the frame
                dispose();

                JOptionPane.showMessageDialog(null, "Consultation added");
                new MainApp();
            }
        });
        return submit;
    }

    public String getPatientId () {

        for (Consultation consultation:consultationDetails) {
            if (consultation.getPatientId().equals(patientID.getText())) {
                JOptionPane.showMessageDialog(null, "PATIENT ID ALREADY EXIST");
            } else {
                return patientID.getText();
            }
        }
        return patientID.getText();
    }

    public static void loadDataFromFile() throws IOException,ClassNotFoundException {

        try {
            FileInputStream fis = new FileInputStream("consultation.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            consultationDetails.clear();
            while (true) {
                try {
                    Consultation consultation = (Consultation) ois.readObject();
                    consultationDetails.add(consultation);
                } catch (EOFException e) {
                    break;
                }
            }
            System.out.println("Data has been loaded to the system/File successfully.");
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(" File not found ! Please check the fail and Try Again.");
        }


    }

    public static void saveDataToFile() throws IOException,ClassNotFoundException{

        try {
            FileOutputStream fos = new FileOutputStream("consultation.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Consultation consultations : consultationDetails) {
                oos.writeObject(consultations);
            }
            oos.close();
            fos.close();
            System.out.println("Data saved successfully.");
        } catch (Exception e1) {
            e1.printStackTrace();
        }


    }

    public static void main(String [] args ) throws IOException, ClassNotFoundException {
        AddConsultation window = new AddConsultation();
        window.setVisible(true);
    }



}

