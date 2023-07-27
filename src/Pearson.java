import java.io.Serializable;
import java.util.Date;

public class Pearson implements Serializable {

    private String name;
    private String surname;
    private Date DATEOFBIRTH;
    private String tel;

    public Pearson(String name, String surname, Date DATEOFBIRTH, String tel) {
        this.name = name;
        this.surname = surname;
        this.DATEOFBIRTH = DATEOFBIRTH;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDATEOFBIRTH() {
        return DATEOFBIRTH;
    }

    public void setDATEOFBIRTH(Date DATEOFBIRTH) {
        this.DATEOFBIRTH = DATEOFBIRTH;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String displayDetails(){
        return "Name : " + this.name +
                "\nSurname : " + this.surname +
                "\nDOB : " + this.DATEOFBIRTH +
                "\nMobile Number : " + this.tel;
    }
}
