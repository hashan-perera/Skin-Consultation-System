import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

// creating a custom table model
public class DoctorTableModel extends AbstractTableModel {
    private final ArrayList<Doctor> doctors;
    private final String[] columnNames = {"Name", "Surname","DOB","Mobile Number", "Medical licence number", "Specialisation"};

    public DoctorTableModel(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

    @Override
    public int getRowCount() {
        return doctors.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Doctor doctor = doctors.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return doctor.getName();
            case 1:
                return doctor.getSurname();
            case 2:
                return doctor.getDATEOFBIRTH();
            case 3:
                return doctor.getTel();
            case 4:
                return doctor.getMedicalLicenceNo();
            case 5:
                return doctor.getSpecialisation();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}

