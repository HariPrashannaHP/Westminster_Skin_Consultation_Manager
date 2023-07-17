import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class JUnitTesting {

    static ArrayList<Doctor> doctorsArrayList = new ArrayList<>();
    static ArrayList<Doctor> loadedDoctorsArrayList = new ArrayList<>();


    public static void main(String[] args) {


    }

    @Test
    void addDoctors() {


        String dateString = "23/09/2000";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(dateString, formatter);
        Doctor doctor = new Doctor("Hari", "Prashanna", date, "0778699987", "Skin", "0001");
        doctorsArrayList.add(doctor);

        // To check if the size equals ...
        Assertions.assertEquals(1, doctorsArrayList.size());

        // To check if the doctor added is added to the doctors ArrayList....
        Assertions.assertEquals(doctor, doctorsArrayList.get(0));


    }


    String deleteDoctor(String licenseNumber) {

        ArrayList<Doctor> emptyDoctorList = new ArrayList<>();
        String licenceNumber = "0001";
        for (int i = 0; i < doctorsArrayList.size(); i++) {
            Doctor doctor = doctorsArrayList.get(i);
            if (doctor.getLicenceNumber().equals(licenceNumber)) {
                System.out.println("Doctor with  license number " + licenceNumber
                        + " has been successfully deleted!");
                System.out.println(doctor);
                doctorsArrayList.remove(doctor);

            }
        }
        // To check if it returns the correct number of doctors after deleting .
        Assertions.assertEquals(0, doctorsArrayList.size());
        // To check if the removed doctor is removed from the doctor arrayList ....
        Assertions.assertEquals(0, doctorsArrayList.size());
        return "Doctor with  license number " + licenceNumber
                + " has been successfully deleted!";

    }


    @Test
    void checkDelete() {

        String licenseNumber = "0001";

        Assertions.assertEquals("Doctor with  license number " + licenseNumber
                + " has been successfully deleted!", deleteDoctor("0001"));
    }


   @Test void printDoctors() {


        doctorsArrayList.sort(Comparator.comparing(Doctor::getSurName, String.CASE_INSENSITIVE_ORDER));
        System.out
                .println("\nDoctors List : \n(Alphabetically Sorted)\n");
        for (Doctor doctor : doctorsArrayList) {
            // print number before each doctor
            System.out.println((doctorsArrayList.indexOf(doctor) + 1) + ". " + doctor);
            Assertions.assertEquals("First name: Hari\n" +
                    "\tSurname: Prashanna\n" +
                    "\tDate of Birth: 23/09/2000\n" +
                    "\tPhone number: 0778699987\n" +
                    "\tSpecialization: Skin\n" +
                    "\tLicence number: 0001",(doctorsArrayList.indexOf(doctor) + 1) + ". " + doctor);
        }


    }

    void saveDataToFile(){
        try {
            FileOutputStream fos = new FileOutputStream("doctorsListTesting.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Doctor doctor :doctorsArrayList) {
                oos.writeObject(doctor);
            }
            oos.close();
            fos.close();
            System.out.println("Data saved to file successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    void loadDataFromFile(){
        try {
            FileInputStream fis = new FileInputStream("doctorsListTesting.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            loadedDoctorsArrayList.clear();
            while (true) {
                try {
                    Doctor doctor = (Doctor) ois.readObject();
                    loadedDoctorsArrayList.add(doctor);
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

    @Test
    void checkSaveAndLoad(){

        saveDataToFile();
        loadDataFromFile();

        Assertions.assertEquals(loadedDoctorsArrayList,doctorsArrayList);

    }
}





