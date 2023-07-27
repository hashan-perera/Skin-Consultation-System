import java.util.ArrayList;
import java.util.Date;

public class Doctor extends Pearson implements Comparable<Doctor>{

    private String medicalLicenceNo;
    private String specialisation;
    private ArrayList<Consultation> bookedConsultations = new ArrayList<>();

    public Doctor(String name, String surname, Date dateOfBirth, String tel, String medicalLicenceNo, String specialisation) {
        super(name, surname, dateOfBirth, tel);
        this.medicalLicenceNo = medicalLicenceNo;
        this.specialisation = specialisation;
    }

    public String getMedicalLicenceNo() {
        return medicalLicenceNo;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public ArrayList<Consultation> getBookedConsultations() {
        return bookedConsultations;
    }

    public void setBookedConsultations(Consultation bc) {
        this.bookedConsultations.add(bc);
    }

    @Override
    public String displayDetails() {
        return  super.displayDetails() +'\n' + "Medical Licence number :"+this.medicalLicenceNo +'\n'+ "Specialisation :"+this.specialisation;
    }
    //override compareTo method from interface Comparable
    @Override
    public int compareTo(Doctor other) {
        // Compare the surnames of the two Doctor objects
        return this.getSurname().compareTo(other.getSurname());
    }
}
