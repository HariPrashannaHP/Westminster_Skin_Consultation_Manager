import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class WestminsterSkinConsultationManager implements SkinConsultationManager  {

    private static ArrayList<Doctor> doctors;
    private static final int MAX_DOCTORS = 10;

    public WestminsterSkinConsultationManager() {
        doctors = new ArrayList<>();
    }

    public static ArrayList<Doctor> getDoctors() {
        return doctors;
    }


    public void addDoctor(Doctor doctor) {
        if (doctors.size() < MAX_DOCTORS) {
            doctors.add(doctor);
            System.out.println("Doctor added successfully!");
        } else {
            System.out.println("Warning: The maximum number of doctors can be added is 10!");
        }
    }

    public void getDoctorDetails() {
        Scanner scanner = new Scanner(System.in);
        String name = stringValidator("name");
        String surName = stringValidator("surname");
        LocalDate birthDate = dateValidator();
        String phoneNumber = numberValidator("phone number");
        String specialization = stringValidator("specialization");

        // Check if the entered licenseNumber is already exist / unique
        String licenceNumber = null;
        if (doctors.size() > 0) {
            boolean unique = false;
            while (!unique) {
                System.out.print("Enter the doctor's licence number: ");
                licenceNumber = scanner.nextLine();
                boolean exists = false;
                for (Doctor doctor : doctors) {
                    if (doctor.getLicenceNumber().equals(licenceNumber)) {
                        exists = true;
                        System.out.println("A doctor with the same licence number already exists!");
                        break;
                    }
                }
                if (!exists) {
                    unique = true;
                }
            }
        } else {
            System.out.print("Enter  doctor's licence number: ");
            licenceNumber = scanner.nextLine();
        }
        Doctor doctor = new Doctor(name, surName, birthDate, phoneNumber, specialization, licenceNumber);
        addDoctor(doctor);
    }


    public void deleteDoctor() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the doctor's licence number: ");
        String licenceNumber;
        boolean doctorExists = false;
        while (!doctorExists) {
            licenceNumber = scanner.nextLine();
            for (int i = 0; i < doctors.size(); i++) {
                Doctor doctor = doctors.get(i);
                if (doctor.getLicenceNumber().equals(licenceNumber)) {
                    System.out.println("Doctor with  license number " + licenceNumber
                            + " has been successfully deleted!");
                    System.out.println(doctor);
                    doctors.remove(doctor);
                    doctorExists = true;
                }
            }
            if (!doctorExists) {
                System.out.print("The doctor with the medical licence number " + licenceNumber
                        + " does not exist! Please enter a valid medical licence number: ");
            }
        }
    }


    public void printDoctors() {
        /* print all doctors alphabetically ordered by surname without considering the
         case sensitivity temporarily without affecting the original order



         */
        ArrayList<Doctor> sortedDoctors = new ArrayList<>(doctors);
        sortedDoctors.sort(Comparator.comparing(Doctor::getSurName, String.CASE_INSENSITIVE_ORDER));
        System.out
                .println("\nDoctors List : \n(Alphabetically Sorted)\n");
        for (Doctor doctor : sortedDoctors) {
            // print number before each doctor
            System.out.println((sortedDoctors.indexOf(doctor) + 1) + ". " + doctor);
        }
    }




    public void storeDataToFile() {
        try {
            FileOutputStream fos = new FileOutputStream("doctorsList.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Doctor doctor : doctors) {
                oos.writeObject(doctor);
            }
            oos.close();
            fos.close();
            System.out.println("Data saved to file successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loadDataFromFile() {
        try {
            FileInputStream fis = new FileInputStream("doctorsList.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            doctors.clear();
            while (true) {
                try {
                    Doctor doctor = (Doctor) ois.readObject();
                    doctors.add(doctor);
                } catch (EOFException e) {
                    break;
                }
            }
            System.out.println("Data has been loaded to the system/File successfully.");
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(" File Not Found OR  The File Is Empty! Please check the fail and Try Again.");
        }
    }


    // To validate the Name
    public String stringValidator(String prompt) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the doctor's " + prompt + ": ");
            String string = scanner.nextLine();
            if (string.matches("[a-zA-Z\\s]+")) {
                return string;
            } else {
                System.out.println("Invalid input! Please enter a valid input: ");
            }
        }
    }


    // To validate the Mobile Number
    public String numberValidator(String prompt) {
        Scanner scanner1 = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the doctor's " + prompt + ": ");
            String string = scanner1.nextLine();
            if (string.matches("[0-9]+") && string.length() == 10) {
                return string;
            } else {
                System.out.println("Invalid input! Please enter a valid input (phone number 10 digits long)");
            }
        }
    }


    // To validate the Date of Birth
    public LocalDate dateValidator() {
        Scanner scanner = new Scanner(System.in);
        boolean validDate = false;
        LocalDate date = null;
        while (!validDate) {
            try {
                System.out.print("Enter the date (dd/MM/yyyy): ");
                String dateString = scanner.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                date = LocalDate.parse(dateString, formatter);
                validDate = true;
            } catch (DateTimeParseException e) {
                System.out.println("\nInvalid date format or values! Please enter the date in the format dd/MM/yyyy");
            }
        }
        return date;
    }





    private void displayMenu() {
        System.out.println("""

                -------------------------- WESTMINSTER SKIN CONSULTATION MANAGER --------------------------

                HELLO THERE !
                
                CHOOSE A OPTION FROM THE BELOW LIST .

                1. ADD A DOCTOR
                2. REMOVE A DOCTOR
                3. PRINT THE DOCTORS LIST
                4. SAVE THE DATA TO FILE
                5. LOAD THE DATA FROM FILE
                6. TO MAIN APPLICATION
                7. EXIT
                
                IMPORTANT :-
                PLEASE MAKE SURE TO SAVE THE DATA AFTER ADDING THE DOCTORS (OPTION 4).
                PLEASE MAKE SURE TO LOAD THE DATA EVERYTIME WHILE OPENING THE APP (OPTION 5).
                
                HAVE A NICE DAY  ◡̈

                ------------------------------------------------------------------------------------------
                """);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        int option;
        String alert = "ERROR - NO DOCTORS ARE IN THE LIST!";
        do {
            displayMenu();
            try {
                System.out.println("ENTER THE NUMBER OF THE OPTION :");
                option = Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                option = 0;
            }
            switch (option) {
                case 1 -> getDoctorDetails();
                case 2 -> {
                    if (doctors.size() != 0) {
                        deleteDoctor();
                    } else {
                        System.out.println(alert);
                    }
                }
                case 3 -> {
                    if (doctors.size() != 0) {
                        printDoctors();
                    } else {
                        System.out.println(alert);
                    }
                }
                case 4 -> {
                    if (doctors.size() != 0) {
                       storeDataToFile(); ;
                    } else {
                        System.out.println(alert);
                    }
                }
                case 5 -> loadDataFromFile();
                case 6 -> {
                    if (doctors.size() != 0) {
                        new MainApp();
                    } else {
                        System.out.println(alert);
                    }
                }
                case 7 -> {
                    System.out.println("THANK YOU FOR USING WSCM!");
                    System.exit(0);
                }
                default -> System.out.println("INVALID OPTION ! PLEASE TRY AGAIN.");
            }
            System.out.println("\nPRESS ANY KEY TO CONTINUE");
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
        } while (true);
    }

    public static void main(String[] args) {
        new WestminsterSkinConsultationManager().run();
    }
}
