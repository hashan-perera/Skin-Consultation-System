import javax.crypto.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


//Application will automatically load the data when executed and application will automatically save the data when quit button clicked!
public class Driver extends JFrame{

    // main class object
    private static final WestminsterSkinConsultationManager wsm = new WestminsterSkinConsultationManager();

    //gui operations

    //main frame object reference variable
    Driver mainFrame = null;

    //to store the encryptedNotes
    String encryptedNote = "";
    //exiting the system
    public void quitSystem(Driver m){
        mainFrame = m;
    }
    public Driver(){

        //panel 1
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(4,1,2,2));
        JButton viewCon = new JButton("View Consultations");
        JButton addCon = new JButton("Add Consultations");
        JButton sortDoctors = new JButton("Sort Doctors");
        JButton viewPatients = new JButton("View all Patients");
        p1.add(viewPatients);
        p1.add(viewCon);
        p1.add(addCon);
        p1.add(sortDoctors);

        //panel 2
        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout(FlowLayout.LEFT,10,2));
        JLabel l1=new JLabel("Consultation Management System");
        l1.setBorder(new EmptyBorder(30, 40, 30, 0));
        //save button
        JButton quit = new JButton("Quit");

        JLabel l2=new JLabel("System supports auto save at Quit 😁");
        l2.setForeground(new Color(0, 140, 145));
        l2.setBorder(new EmptyBorder(30, 40, 30, 0));

        // Load the image file and resize it
        ImageIcon icon = new ImageIcon("Logo.png");
        Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel imageContain = new JLabel(new ImageIcon(image));
        imageContain.setPreferredSize(new Dimension(100, 100));
        p2.add(imageContain);
        p2.add(l1);
        p2.add(l2);
        p2.add(quit);


        //panel 3
        JPanel p3 = new JPanel();
        p3.setLayout(new BorderLayout());


        // Create the table using the column names and data
        JTable table = new JTable(new DoctorTableModel(wsm.getDoctors()));
        table.setRowHeight(50);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(60);
        table.getColumnModel().getColumn(2).setPreferredWidth(130);
        table.getColumnModel().getColumn(3).setPreferredWidth(70);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(130);
        // Create a TableRowSorter for the table
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel p4 = new JPanel();
        p4.setLayout(new FlowLayout(FlowLayout.LEFT,10,2));
        JLabel footer1 = new JLabel("Address : IIT, Ramakrishna Road, Col 06");
        footer1.setBorder(new EmptyBorder(30,0,30,30));
        JLabel footer2 = new JLabel("Contact no: 070 1994949");
        footer2.setBorder(new EmptyBorder(30,20,30,20));
        JLabel footer3 = new JLabel("Email : BestSkinConsultation@gamail.com");
        footer3.setBorder(new EmptyBorder(30,20,30,0));

        p4.setBackground(new Color(122, 227, 225));

        p4.add(footer1);
        p4.add(footer2);
        p4.add(footer3);

        JLabel heading = new JLabel("Available Doctors in the System");
        Color customColor = new Color(122, 227, 225);
        heading.setBackground(customColor);
        heading.setOpaque(true);
        p3.add(heading,BorderLayout.NORTH);
        p3.add(scrollPane,BorderLayout.CENTER);
        p3.add(p4,BorderLayout.SOUTH);

        //adding to the frame
        add(p1,BorderLayout.EAST);
        add(p2,BorderLayout.NORTH);
        add(p3,BorderLayout.CENTER);

        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wsm.Save();
                mainFrame.dispose();
            }
        });

        //sorting button
        sortDoctors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sort the table by the "Name" column
                sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(1, SortOrder.ASCENDING)));
                sorter.sort();
            }
        });

        //open up the frame for adding a consultation (Frame 2)
        addCon.addActionListener(e -> {

                JPanel secondHeader = new JPanel();
                secondHeader.setLayout(new GridLayout(6,1,1,3));
                JLabel info1 = new JLabel("If its a existing patient only enter the Patient Id number and other Consultation details.");
                JLabel info2 = new JLabel("Other details and information will be auto filled.");
                JLabel info3 = new JLabel("Select the number according to medical number of the doctor you can see it at right side of the window. ");
                Font font1 = info1.getFont();
                Font font2 = info2.getFont();
                Font font3 = info3.getFont();
                float size1 = font1.getSize() + 2.0f;
                float size2 = font2.getSize() + 2.0f;
                float size3 = font3.getSize() + 2.0f;
                info1.setFont(font1.deriveFont(size1));
                info2.setFont(font2.deriveFont(size2));
                info3.setFont(font3.deriveFont(size3));
                JLabel info0 = new JLabel("First Time Here ? Then Please Read Below Notes Before You Make a Consultation ⚙");
                Font font0 = info1.getFont();
                float size0 = font0.getSize() + 2.0f;
                info0.setFont(font0.deriveFont(size0));
                info0.setForeground(new Color(0, 140, 145));
                secondHeader.add(info0);
                secondHeader.add(info1);
                secondHeader.add(info2);
                secondHeader.add(info3);
                secondHeader.add(new JLabel("Make sure to check the availability of the doctor once you select the date and time - Click Check Availability Button."));
                secondHeader.add(new JLabel(" "));

                JFrame frame2 = new JFrame("Add Consultations with a doctor");
                JPanel panel1 = new JPanel();
                panel1.setLayout(new GridLayout(11,2,1,1));



                panel1.add(new JLabel("Enter your choice for the doctor : "));
                JTextField t1 = new JTextField();
                panel1.add(t1);


                //till blank line is needed
                panel1.add(new JLabel("Select a consultation date (Date/Month/Year): "));
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                JFormattedTextField dateField = new JFormattedTextField(dateFormat);
                panel1.add(dateField);
                panel1.add(new JLabel("Select a consultation time (Hours:Minutes AM or PM): "));
                DateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                JFormattedTextField timeField = new JFormattedTextField(timeFormat);
                panel1.add(timeField);


                panel1.add(new JLabel("Please Check the availability of the doctor before proceeding : "));
                JButton availability = new JButton("Check Availability");
                panel1.add(availability);


                //below code is allow user to check availability of the select doctor before proceeding
                availability.addActionListener(r -> {
                    try {
                        //get the selected doctor
                        String text1 = t1.getText();
                        int choice = Integer.parseInt(text1);
                        Doctor selectedDoctor = wsm.getDoctors().get(choice);
                        //date time stuff
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime((Date) dateField.getValue());
                        Date d = (Date) dateField.getValue();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        calendar.setTime((Date) timeField.getValue());
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        Calendar dateTimeCalendar = Calendar.getInstance();
                        dateTimeCalendar.set(year, month, day, hour, minute, 00);
                        Date dateAndTime = dateTimeCalendar.getTime();
                        boolean havePreviousConsultation = true;
                        Date currentDate = new Date();
                        if (!(dateAndTime.after(currentDate))) {
                            JOptionPane.showMessageDialog(null, "Consultation Date or time can not be after current date and time", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                                //check either this is a possible date for the doctor (DoctorsConsultation history)
                                for (Consultation bc : selectedDoctor.getBookedConsultations()) {
                                    String appointment = String.valueOf(bc.getDateTimeConsultation());
                                    String aN = String.valueOf(dateAndTime);
                                    if (appointment.equals(aN)) {
                                        JOptionPane.showMessageDialog(null, "Dr."+bc.getDoctor().getName() +" is not available in the date and time you have chosen please enter a different time or date.\n IF YOU PROCEED WITH SELECTED DATE AND TIME DIFFERENT DOCTOR FROM THE SYSTEM WILL BE ASSIGNED TO YOU !", "Sorry",
                                                JOptionPane.INFORMATION_MESSAGE);
                                        havePreviousConsultation = false;
                                        break;
                                    }
                                }
                                if(havePreviousConsultation){
                                    JOptionPane.showMessageDialog(null, "Doctor is Available", "Great !",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                    }catch (Exception z){
                        JOptionPane.showMessageDialog(null, "Invalid input 💀 ", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        System.out.println("Error was : "+z);
                    }
                });



                panel1.add(new JLabel("Enter patient id number : "));
                JTextField t2 = new JTextField();
                panel1.add(t2);

                panel1.add(new JLabel("Enter patients Name : "));
                JTextField t3 = new JTextField();
                panel1.add(t3);

                panel1.add(new JLabel("Enter patients Surname : "));
                JTextField t4 = new JTextField();
                panel1.add(t4);



                panel1.add(new JLabel("Enter patients Date of Birth (Date/Month/Year): "));
                DateFormat dobDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                JFormattedTextField dobDateField = new JFormattedTextField(dobDateFormat);
                panel1.add(dobDateField);



                panel1.add(new JLabel("Enter patient Mobile number (No spaces) : "));
                JTextField t5 = new JTextField();
                panel1.add(t5);

                panel1.add(new JLabel("Enter the needed consultation time : "));
                JTextField t6 = new JTextField();
                panel1.add(t6);

                panel1.add(new JLabel("Enter a note for the doctor : "));
                JTextArea a1 = new JTextArea();
                a1.setBorder(new LineBorder(new Color(95, 243, 241)));
                panel1.add(a1);


                JPanel panel2 = new JPanel();
                panel2.setLayout(new GridLayout(wsm.getDoctors().size(),2,3,3));

                int count = 0;
                for(Doctor Do : wsm.getDoctors()){
                    panel2.add(new JLabel(Do.getMedicalLicenceNo()));
                    panel2.add(new JLabel(String.valueOf(count)));
                    count++;
                }

                frame2.add(secondHeader,BorderLayout.NORTH);
                frame2.add(panel1,BorderLayout.CENTER);
                frame2.add(panel2,BorderLayout.EAST);
                //button for operation add
                JButton addAndSave = new JButton("Add");
                frame2.add(addAndSave,BorderLayout.SOUTH);

                frame2.setSize(900, 700);
                frame2.setVisible(true);

                addAndSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean finalAction = true;
                    try {
                        String text1 = t1.getText();
                        int choice = Integer.parseInt(text1);
                        Doctor selectedDoctor= wsm.getDoctors().get(choice);
                        //date time stuff
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime((Date) dateField.getValue());
                        Date d = (Date)dateField.getValue();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        calendar.setTime((Date) timeField.getValue());
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        Calendar dateTimeCalendar = Calendar.getInstance();
                        dateTimeCalendar.set(year, month, day, hour, minute,00);
                        Date dateAndTime = dateTimeCalendar.getTime();



                        Date currentDate = new Date();
                        if(!(dateAndTime.after(currentDate))){
                            JOptionPane.showMessageDialog(null, "Consultation Date or time can not be after current date and time", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            finalAction = false;
                        }else {

                            //check either this is a possible date for the doctor (DoctorsConsultation history)
                            boolean havePreviousConsultation = false;
                            if (selectedDoctor.getBookedConsultations() != null) { // add here
                                for (Consultation bc : selectedDoctor.getBookedConsultations()) {
                                    String appointment = String.valueOf(bc.getDateTimeConsultation());
                                    String aN = String.valueOf(dateAndTime);
                                    if (appointment.equals(aN)) {
                                        havePreviousConsultation = true;
                                        break;
                                    }
                                }
                            }
                            //randomly assign a doctor
                            if (havePreviousConsultation) {
                                // initializing random class
                                Random rand = new Random();
                                while (true) {
                                    // generating random index with the help of
                                    // nextInt() method
                                    int index = rand.nextInt(wsm.getDoctors().size());
                                    Doctor newSelectedDoctor = wsm.getDoctors().get(index);
                                    if (selectedDoctor.equals(newSelectedDoctor)) {
                                        continue;
                                    }
                                    JOptionPane.showMessageDialog(null, selectedDoctor.getMedicalLicenceNo() + "  Dr." + selectedDoctor.getName() + " not available at the date and time you selected so " + newSelectedDoctor.getMedicalLicenceNo() + " Dr." + newSelectedDoctor.getName() + " assigned to you!");
                                    selectedDoctor = newSelectedDoctor;
                                    break;
                                }
                            }

                            String patientId = t2.getText();
                            String text2 = t6.getText();
                            double consultationTimeNeeded = Double.parseDouble(text2);
                            if (!Files.exists(Paths.get("data/keystore.jceks"))) {
                                Key secretKey =null;
                                try {
                                    // Generate a secret key for the encryption algorithm
                                    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                                    keyGenerator.init(128); // Use a 128-bit key
                                    secretKey = keyGenerator.generateKey();

                                    // Store the key in a keystore
                                    KeyStore keyStore = KeyStore.getInstance("JCEKS");
                                    keyStore.load(null, null); // Load an empty keystore
                                    // Store the key with the alias "secretKey" and use password as Keystore-password array
                                    keyStore.setKeyEntry("secretKey", secretKey, "keystore-password".toCharArray(), null);

                                    //Save the keystore to a file
                                    keyStore.store(new FileOutputStream("data/keystore.jceks"), "keystore-password".toCharArray());

                                    // Set up the encryptionCipher for encryption
                                }catch (Exception v){
                                    System.out.println("Error when creating and storing the secret key needed for encryption !" + v);
                                }
                            }
                            KeyStore keyStore = KeyStore.getInstance("JCEKS");
                            keyStore.load(new FileInputStream("data/keystore.jceks"),"keystore-password".toCharArray());

                            // Get the secret key using its alias
                            Key secretKey = keyStore.getKey("secretKey","keystore-password".toCharArray());
                            Cipher encryptionCipher = Cipher.getInstance("AES");
                            encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKey);
                            // Encrypt the text when it is entered into the text area
                            String text = a1.getText();
                            // Encrypt the text
                            byte[] encryptedBytes = encryptionCipher.doFinal(text.getBytes());
                            // Encode the encrypted text as a base64 string
                            encryptedNote = Base64.getEncoder().encodeToString(encryptedBytes);
                            //check whether this is an existing patient or a new one
                            boolean existing = false;
                            Patient p = null;
                            double cost = 0;
                            for (Patient i : wsm.getPatients()) {
                                if (i.getPatientId().equals(patientId)) {
                                    cost = consultationTimeNeeded * 25;
                                    existing = true;
                                    p = i;
                                    t3.setText("");
                                    t3.setEditable(false);
                                    t3.setText(p.getName());
                                    t4.setText("");
                                    t4.setEditable(false);
                                    t4.setText(p.getSurname());
                                    dobDateField.setText("");
                                    dobDateField.setEditable(false);
                                    dobDateField.setText(String.valueOf(p.getDATEOFBIRTH()));
                                    t5.setText("");
                                    t5.setEditable(false);
                                    t5.setText(p.getTel());
                                    break;
                                }
                            }
                            String patientName = t3.getText();
                            for (int i = 0; i < patientName.length(); i++) {
                                char c = patientName.charAt(i);
                                if (Character.isDigit(c)) {
                                    JOptionPane.showMessageDialog(null, "Name can not contain numbers", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    finalAction = false;
                                    break;
                                }
                            }
                            String patientSurname = t4.getText();
                            for (int i = 0; i < patientSurname.length(); i++) {
                                char c = patientSurname.charAt(i);
                                if (Character.isDigit(c)) {
                                    JOptionPane.showMessageDialog(null, "Surname can not contain numbers", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    finalAction = false;
                                    break;
                                }
                            }

                            Date dobDate = (Date)dobDateField.getValue();
                            String dDate = dobDateField.getText();
                            if(Objects.equals(dDate, "")){
                                JOptionPane.showMessageDialog(null, "Date of Birth can not be empty", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                finalAction = false;
                            }

                            String mobileNumber = t5.getText();
                            if (mobileNumber.length() > 10) {
                                JOptionPane.showMessageDialog(null, "Not a valid mobile number", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                finalAction = false;
                            } else {
                                for (int i = 0; i < 10; i++) {
                                    char c = mobileNumber.charAt(i);
                                    if (!Character.isDigit(c)) {
                                        JOptionPane.showMessageDialog(null, "Not a valid mobile number", "Error",
                                                JOptionPane.ERROR_MESSAGE);
                                        finalAction = false;
                                        break;
                                    }
                                }
                            }
                            if (finalAction) {
                                if (!(existing)) {
                                    cost = consultationTimeNeeded * 15;
                                    p = new Patient(patientName, patientSurname, dobDate, mobileNumber, patientId);
                                    wsm.getPatients().add(p);
                                }

                                Consultation c = new Consultation(selectedDoctor, p, dateAndTime, cost, encryptedNote);
                                wsm.getConsultations().add(c);
                                p.setConsultationHistory(c);
                                selectedDoctor.setBookedConsultations(c);
                                JOptionPane.showMessageDialog(null, "Consultation successfully added with" + selectedDoctor.getMedicalLicenceNo());
                                frame2.dispose();
                            }
                        }
                    } catch (Exception ev) {
                        JOptionPane.showMessageDialog(null, "Invalid input 💀 ", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        System.out.println("Error was : "+ev);
                    }

                    // Do something with the text
                }
            });

        });



        //open up the frame for viewing the consultation (Frame 3)
        viewCon.addActionListener(e -> {
            JFrame frame3 = new JFrame("View Consultations of a specific patient");
            frame3.setSize(900, 600);
            frame3.setVisible(true);


            JPanel frame3p1 = new JPanel();
            frame3p1.setLayout(new FlowLayout());
            JLabel inform = new JLabel("Enter patient's Id :");
            JTextField searchField = new JTextField(20);



            frame3p1.add(inform);
            frame3p1.add(searchField);
            JButton searchButton = new JButton("Search");
            frame3p1.add(searchButton);

            JPanel frame3p2 = new JPanel();
            frame3p2.setLayout(new BorderLayout());
            JTextArea a2 = new JTextArea();
            // Set the preferred size of the text area
            a2.setPreferredSize(new Dimension(800,120 ));
            LineBorder border = new LineBorder(Color.BLACK, 2);
            a2.setBorder(border);

            JTextArea full = new JTextArea();
            full.setPreferredSize(new Dimension(800,600));
            // Create a Color object with red, green, and blue values of 128
            Color backgroundColor = new Color(122, 227, 225);

            // Set the background color of the text area to the Color object
            full.setBackground(backgroundColor);

            JScrollPane scrollPane1 = new JScrollPane(full);
            frame3p2.add(a2,BorderLayout.NORTH);
            frame3p2.add(scrollPane1,BorderLayout.CENTER);


            JPanel frame3p3 = new JPanel();
            frame3p3.setLayout(new FlowLayout());
            JLabel label0 = new JLabel("Enter which consultation you want cancel :");
            JTextField field0 = new JTextField(10);
            JButton Cancel = new JButton("Cancel");
            frame3p3.add(label0);
            frame3p3.add(field0);
            frame3p3.add(Cancel);

            searchButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Get the patient ID from the search bar
                    String id = searchField.getText();
                    Patient selectedPatient =null;
                    // Search for the patient in the list of patients
                    for (Patient p : wsm.getPatients()) {
                        if (p.getPatientId().equals(id)) {
                            selectedPatient = p;
                            a2.setText("");
                            a2.setEditable(false);
                            a2.setText(p.displayDetails());
                            break;
                        }
                    }
                    if (selectedPatient != null) {
                        int i = 0;
                        String totalText ="";
                        full.setEditable(false);
                        for(Consultation myCon : wsm.getConsultations()) {
                            if(myCon.getPatient().getPatientId().equals(selectedPatient.getPatientId())) {
                                String decryptedNote ="";
                                Key secretKey = null;
                                //decryption for notes happening through below code
                                try {
                                    KeyStore keystore = KeyStore.getInstance("JCEKS");
                                    char[] password = "keystore-password".toCharArray();
                                    try (InputStream inputStream = new FileInputStream("data/keystore.jceks")) {
                                        keystore.load(inputStream, password);
                                    }
                                    secretKey = keystore.getKey("secretKey", password);
                                    Cipher decryptionCipher = null;
                                    decryptionCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                                    decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey);
                                    byte[] encryptedBytes = Base64.getDecoder().decode(myCon.getNotes());
                                    byte[] decryptedBytes = new byte[0];
                                    decryptedBytes = decryptionCipher.doFinal(encryptedBytes);
                                    decryptedNote = new String(decryptedBytes, StandardCharsets.UTF_8);
                                }catch(Exception ev){
                                    System.out.println("Error when decrypting data entered :"+ev);
                                    JOptionPane.showMessageDialog(null, "Data about patient notes are corrupted can't display !");
                                }


                                totalText = totalText + "\n" + "Consultation Number : " + i + "\n" +
                                        "Dr. " + myCon.getDoctor().getName() +
                                        " " + myCon.getDoctor().getSurname() + "\n" +
                                        "Medical Licence Number : " + myCon.getDoctor().getMedicalLicenceNo() +
                                        "\n" + "Specialisation : " + myCon.getDoctor().getSpecialisation() +
                                        "\n" + "Date and Time of the Consultation : " + myCon.getDateTimeConsultation() + "\n" +
                                        "Cost : $ " + myCon.getCost() + "\n" +
                                        "Notes : " + decryptedNote +
                                        "\n-------------------------------------------------------------------\n";
                                full.setText(totalText);
                            }
                            i++;
                        }
                    } else {
                        // If the patient was not found, display a message indicating that the patient was not found
                        JOptionPane.showMessageDialog(null, "Patient not found");
                    }
                }
            });

            Cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent k) {
                    try {
                        int index = Integer.parseInt(field0.getText());
                        wsm.getConsultations().remove(index);
                        JOptionPane.showMessageDialog(null, "Consultation canceled");
                        frame3.dispose();
                    }catch (Exception e){
                        JOptionPane.showMessageDialog(null, "Please enter a valid consultation number");
                    }
                }
            });

            frame3.add(frame3p1,BorderLayout.NORTH);
            frame3.add(frame3p2,BorderLayout.CENTER);
            frame3.add(frame3p3,BorderLayout.SOUTH);
        });

        //open up a new frame and view patient table
        viewPatients.addActionListener(e -> {
            JFrame frame4 = new JFrame("View all patients currently in the system");
            frame4.setSize(1150, 600);
            frame4.setVisible(true);

            JLabel j10 = new JLabel("Below Table shows the list of patient's that entered into the System. To make future operation with the patient easier inform the patient about his/her patient id number from below table. 👨‍💻");

            j10.setBorder(new EmptyBorder(30, 40, 30, 0));

            // Create the table using the column names and data
            JTable tablePatient = new JTable(new PatientTableModel(wsm.getPatients()));
            tablePatient.setRowHeight(50);
            tablePatient.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablePatient.getColumnModel().getColumn(1).setPreferredWidth(60);
            tablePatient.getColumnModel().getColumn(2).setPreferredWidth(130);
            tablePatient.getColumnModel().getColumn(3).setPreferredWidth(70);
            tablePatient.getColumnModel().getColumn(4).setPreferredWidth(100);
            // Create a TableRowSorter for the table
            TableRowSorter<TableModel> sorterPatient = new TableRowSorter<>(tablePatient.getModel());
            tablePatient.setRowSorter(sorterPatient);
            JScrollPane scrollPaneP = new JScrollPane(tablePatient);

            frame4.add(j10,BorderLayout.NORTH);
            frame4.add(scrollPaneP,BorderLayout.CENTER);
        });
    }
    //console operations
    public static void main(String[] args) {
        System.out.println("----------------------- WestminsterSkinConsultationManager -----------------------\n\n");
        Scanner s = new Scanner(System.in);

        System.out.println("Select a option from below menu\n\n");
        System.out.println("Press 1 to add a doctor to the System");
        System.out.println("Press 2 to delete a doctor from the System");
        System.out.println("Press 3 to show a list of doctors");
        System.out.println("Press 4 to save System details");
        System.out.println("Press 9 to open the GUI for General User");
        System.out.println("Press 0 to exit the Manager Interface\n\n");

        // data is automatically loaded

        wsm.loadSystemData();

        loop1:
        while (true) {
            try {
                System.out.print("Enter operation number : ");
                int input = s.nextInt();
                s.nextLine();
                switch (input) {
                    case 1:
                        wsm.addDoctor();
                        break;
                    case 2:
                        wsm.deleteDoctor();
                        break;
                    case 3:
                        System.out.println();
                        wsm.listOfTheDoctors();
                        break;
                    case 4:
                        wsm.Save();
                        break;
                    case 11:
                        //below option will load system data if needed
                        wsm.loadSystemData();
                        break;
                    case 9:
                        //gui opens up
                        Driver mainFrame = new Driver();
                        mainFrame.setTitle("CMS");
                        mainFrame.quitSystem(mainFrame);
                        mainFrame.setSize(1000, 600);
                        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        mainFrame.setVisible(true);
                        break;
                    case 0:
                        break loop1;
                    default:
                        //default
                        System.out.println("out of range");
                }
            } catch (Exception e) {
                System.out.println("Invalid input  operation");
                s.nextLine();
            }
        }
    }

}
