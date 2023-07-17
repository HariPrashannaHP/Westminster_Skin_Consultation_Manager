import java.time.LocalDate;

public class Patient extends Person{
    private String patientID;

    public Patient(String name, String surName, LocalDate dateOfBirth, String mobileNumber, String patientID) {
        super(name, surName, dateOfBirth, mobileNumber);
        this.patientID = patientID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
}
