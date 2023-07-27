import java.util.ArrayList;
import java.util.Date;

public class Patient extends Pearson{

    private String patientId;
    private ArrayList<Consultation> consultationHistory = new ArrayList<>();

    public Patient(String name, String surname, Date dateOfBirth, String tel, String patientId) {
        super(name, surname, dateOfBirth, tel);
        this.patientId = patientId;
    }

    public void setConsultationHistory(Consultation c) {
        this.consultationHistory.add(c);
    }

    public String getPatientId() {
        return patientId;
    }

    public ArrayList<Consultation> getConsultationHistory() {
        return consultationHistory;
    }

    @Override
    public String displayDetails() {
        return super.displayDetails() +'\n' +"Patient id :"+this.patientId;

    }
}
