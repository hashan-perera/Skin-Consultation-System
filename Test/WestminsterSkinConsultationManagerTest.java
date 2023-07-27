import org.junit.Test;
import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.Assert.*;

public class WestminsterSkinConsultationManagerTest {

    private final WestminsterSkinConsultationManager wsmTest = new WestminsterSkinConsultationManager();

    @Test
    public void addDoctor() {
        String firstIn = "John\n" + "Smith\n" + "01/12/1990\n" + "5555551212\n" + "ML001\n" + "Medical dermatology\n";
        wsmTest.sc = new Scanner(firstIn);
        wsmTest.addDoctor();
        ArrayList<Doctor> doctorsTest = wsmTest.getDoctors();
        assertTrue((doctorsTest.size() == 1));
        wsmTest.sc = new Scanner(System.in);
    }

    @Test
    public void deleteDoctor() {
        //adding a dummy doctor object
        String firstIn = "John\n" + "Smith\n" + "01/12/1990\n" + "5555551212\n" + "ML001\n" + "Medical dermatology\n";
        wsmTest.sc = new Scanner(firstIn);
        wsmTest.addDoctor();

        //checking for delete operation
        String secondIn = "0";
        wsmTest.sc = new Scanner(secondIn);
        wsmTest.deleteDoctor();
        ArrayList<Doctor> doctorsTest = wsmTest.getDoctors();
        assertTrue((doctorsTest.size() == 0));
        //reset input stream to default keyboard inputs
        wsmTest.sc = new Scanner(System.in);
    }
}