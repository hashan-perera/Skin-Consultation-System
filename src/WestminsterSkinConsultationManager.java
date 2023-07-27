import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

//program is used by either hospital employee or a manager
public class WestminsterSkinConsultationManager implements SkinConsultationManager {

    private ArrayList<Doctor> doctors = new ArrayList<>();
    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayList<Consultation> consultations = new ArrayList<>();

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public ArrayList<Consultation> getConsultations() {
        return consultations;
    }

    public Scanner sc = new Scanner(System.in);

    public void loadSystemData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("doctors.bin"))) {
            doctors = (ArrayList<Doctor>) in.readObject();
            System.out.println("Successfully loaded back1 :)");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error when loading doctors.bin back to the system :(");
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("patients.bin"))) {
            patients = (ArrayList<Patient>) in.readObject();
            System.out.println("Successfully loaded back2 :)");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error when loading patients.bin back to the system :(");
            // handle the exception
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("consultations.bin"))) {
            consultations = (ArrayList<Consultation>) in.readObject();
            System.out.println("Successfully loaded back3 :)");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error when loading consultations.bin back to the system :(");
            System.out.println("\n\n*** If it's your first time opening this application ignore above errors, they wont appear on next time ***\n\n");
            // handle the exception
        }
    }

    //used to get basic info belong to Person class for both Patient and the Doctor
    Date DOB = null;

    private void gettingDOB(String phrase) {
        while (true) {
            try {
                System.out.printf("Enter %s date of birth (Day/Month/Year) : ", phrase);
                SimpleDateFormat dateObject = new SimpleDateFormat("dd/MM/yyyy");
                String dobString = sc.nextLine();
                DOB = dateObject.parse(dobString);
                break;
            } catch (Exception e) {
                System.out.println(DOB);
                System.out.println("invalid date please follow the format" + e);
            }
        }
    }

    private String[] basicInfo() {
        String phrase = "doctor's";
        String[] n = new String[3];
        String name;
        check0:
        while (true) {
            System.out.printf("Enter %s name : ", phrase);
            name = sc.nextLine();
            for (int i = 0; i < name.length(); i++) {
                char c = name.charAt(i);
                if (Character.isDigit(c)) {
                    System.out.println("Name can not contain numbers");
                    continue check0;
                }
            }
            break;
        }

        String surname = "";
        check0:
        while (true) {
            System.out.printf("Enter %s surname : ", phrase);
            surname = sc.nextLine();
            for (int i = 0; i < surname.length(); i++) {
                char c = surname.charAt(i);
                if (Character.isDigit(c)) {
                    System.out.println("Surname can not contain numbers");
                    continue check0;
                }
            }
            break;
        }
        gettingDOB(phrase);
        String tel = "";
        check1:
        while (true) {
            System.out.printf("Enter %s mobile number : ", phrase);
            tel = sc.nextLine();
            if (tel.length() < 10) {
                System.out.println("Not a valid mobile Number !");
                continue;
            }
            for (int i = 0; i < 10; i++) {
                char c = tel.charAt(i);
                if (!Character.isDigit(c)) {
                    System.out.println("Not a valid mobile Number !");
                    continue check1;
                }
            }
            break;
        }
        //add input validation for 10 numbers
        n[0] = name;
        n[1] = surname;
        n[2] = tel;
        return n;
    }

    //add new doctor to the system (max 10)
    public void addDoctor() {
        String[] n = basicInfo();
        System.out.print("Enter doctors medical licence number : ");
        String licenceNo = sc.nextLine();
        System.out.print("Enter specialization : ");
        String specialization = sc.nextLine();
        if (doctors.size() < 10) {
            doctors.add(new Doctor(n[0], n[1], DOB, n[2], licenceNo, specialization));
            System.out.println("Doctor entered to the System successfully");
        } else {
            System.out.println("Sorry Maximum number of doctors are allocated (10)");
        }
    }

    //delete a doctor from system using medical number
    public void deleteDoctor() {
        System.out.println("Process for deleting a doctor!");
        System.out.println("Select the relevant number from below menu according to the doctor's medical number");
        int count = 0;
        for (Doctor d : doctors) {
            System.out.print("Select " + count + " to delete " + d.getMedicalLicenceNo() + '\n');
            count++;
        }
        while (true) {
            try {
                System.out.print("Enter : ");
                String o = sc.nextLine();
                int option =Integer.parseInt(o);
                System.out.println(doctors.get(option).displayDetails());
                doctors.remove(option);
                System.out.println("Doctor is removed !!!");
                System.out.println("Remaining doctors in the system : " + doctors.size());
                break;
            } catch (Exception e) {
                System.out.println("Enter a valid number in rage");
            }
        }
    }

    //print the list of the doctors arrange in alphabetical order according to Surname
    public void listOfTheDoctors() {
        Collections.sort(doctors);
        for (Doctor d : doctors) {
            String docInfo=d.displayDetails();
            System.out.println(docInfo);
            System.out.println();
        }
    }

    //Save to a file
    public void Save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("doctors.bin"))) {
            out.writeObject(doctors);
            System.out.println("\nSuccessfully write to the file1 :)");
        } catch (IOException e) {
            System.out.println("Error couldn't create doctors.bin :(" + e);
            // handle the exception
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("patients.bin"))) {
            out.writeObject(patients);
            System.out.println("Successfully write to the file2 :) ");
        } catch (IOException e) {
            System.out.println("Error couldn't create patients.bin :(" + e);
            // handle the exception
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("consultations.bin"))) {
            out.writeObject(consultations);
            System.out.println("Successfully write to the file3 :)");
        } catch (IOException e) {
            System.out.println("Error couldn't create consultations.bin :(" + e);
            // handle the exception
        }
    }
}