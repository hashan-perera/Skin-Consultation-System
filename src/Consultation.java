import java.io.Serializable;
import java.util.Date;

public class Consultation implements Serializable {

//    private int consultationId;
    private Doctor doctor;
    private Patient patient;
    private Date dateTimeConsultation;
    private double cost;
    private String notes;


    public Consultation(Doctor doctor, Patient patient, Date dateTimeConsultation, double cost, String notes) {
        this.doctor = doctor;
        this.patient = patient;
        this.dateTimeConsultation = dateTimeConsultation;
        this.cost = cost;
        this.notes = notes;
    }

    public Date getDateTimeConsultation() {
        return dateTimeConsultation;
    }

    public double getCost() {
        return cost;
    }

    public String getNotes() {
        return notes;
    }


    public String showConsultationInfo(){
       return "----------Report---------\n\n"+doctor.displayDetails() +'\n' + patient.displayDetails() +'\n'+ dateTimeConsultation +" \n"+ cost +"\n" +notes;

    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }
}
