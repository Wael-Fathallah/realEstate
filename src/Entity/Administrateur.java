package Entity;

/**
 *
 * @author Elyes
 */
public class Administrateur {

    //Declcaration des attribus
    protected int Id;
    protected String Nom;
    protected String Prenom;
    protected String Password;
    protected int privilege;
    protected String mail;

    //Constructeur
    public Administrateur() {

    }

    public Administrateur(int Id, String Nom, String Prenom, String Password, String mail, int privilege) {
        this.Id = Id;
        this.Nom = Nom;
        this.Prenom = Prenom;
        this.Password = Password;
        this.privilege = privilege;
        this.mail = mail;
    }

    //Getters and Setters
    public int getId() {
        return Id;
    }

    public String getNom() {
        return Nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public String getPassword() {
        return Password;
    }

    public int getPrivilege() {
        return privilege;
    }

    public String getMail() {
        return mail;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public void setPrenom(String Prenom) {
        this.Prenom = Prenom;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
