package More;

import Entity.Administrateur;
import Handler.AdministrateurHandler;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author Elyes
 */
public class ListeAdmin extends List implements CommandListener, Runnable {

    private Display disp;
    //Commandes
    private Command back = new Command("Back", Command.BACK, 1);
    private Command details = new Command("Details", Command.SCREEN, 1);
    private Command delete = new Command("Supprimer", Command.SCREEN, 1);
    private Command add = new Command("Ajouter", Command.SCREEN, 1);
    private Command YesCommand = new Command("Oui", Command.SCREEN, 1);
    private Command NoCommand = new Command("Non", Command.BACK, 1);
    private Command modify = new Command("Modifier", Command.SCREEN, 1);
    //Screens
    private Displayable last;
    private Form details_form;
    private Form add_form;
    //Alerts
    private Alert alert;
    private Alert confirm;
    //Connexion
    HttpConnection httpconnection;
    DataInputStream datainputstream;
    StringBuffer buffer = new StringBuffer();
    int C;
    //Other
    Administrateur[] adminArray;
    int operation = 0;
    boolean empty = true;

    private ListeAdmin() {
        super("Liste des Admins", List.EXCLUSIVE);
        ////////////////////////////////////////////
        getAdminsList();
        ////////////////////////////////////////////
        this.addCommand(back);
        this.addCommand(details);
        this.addCommand(delete);
        this.addCommand(add);
        this.setCommandListener(this);
    }

    public ListeAdmin(MIDlet m) {
        this();
        disp = Display.getDisplay(m);
        last = disp.getCurrent();
    }

    public void commandAction(Command c, Displayable d) {
        if (c == back && d == this) {
            disp.setCurrent(last);
        }
        if (c == back && d == add_form) {
            disp.setCurrent(this);
        }
        if (c == back && d == details_form) {
            disp.setCurrent(this);
        }
        if (c == details && d == this) {
            details_form = getDetailsForm(this.getSelectedIndex());
            disp.setCurrent(details_form);
        }
        if (c == add && d == this) {
            add_form = addAdminForm();
            disp.setCurrent(add_form);
        }
        if (c == add && d == add_form) {
            addAdmin();
            if (empty == false) {
                getAdminsList();
                disp.setCurrent(this);
            } else {
                disp.setCurrent(new Alert("Erreur","champ vide",null,AlertType.WARNING),add_form);
            }
        }
        if (c == delete && d == this) {
            confirm = new Alert("Confirmation", "Supprimer " + adminArray[this.getSelectedIndex()].getPrenom() + " " + adminArray[this.getSelectedIndex()].getNom() + " ?", null, AlertType.WARNING);
            confirm.addCommand(YesCommand);
            confirm.addCommand(NoCommand);
            confirm.setTimeout(Alert.FOREVER);
            confirm.setCommandListener(this);
            disp.setCurrent(confirm);
        }
        if (c == delete && d == details_form) {
            confirm = new Alert("Confirmation", "Supprimer " + adminArray[this.getSelectedIndex()].getPrenom() + " " + adminArray[this.getSelectedIndex()].getNom() + " ?", null, AlertType.WARNING);
            confirm.addCommand(YesCommand);
            confirm.addCommand(NoCommand);
            confirm.setTimeout(Alert.FOREVER);
            confirm.setCommandListener(this);
            disp.setCurrent(confirm);
        }
        if (c == NoCommand && d == confirm) {
            disp.setCurrent(this);
        }
        if (c == YesCommand && d == confirm) {
            deleteAdmin();
            getAdminsList();
            disp.setCurrent(this);
        }
        if (c == modify && d == details_form) {
            modifierAdmin();
            getAdminsList();
            disp.setCurrent(new Alert("Info", "Modification effectué", null, AlertType.INFO), this);
        }
    }

    public void getAdminsList() {
        try {
            operation = 1; // affichage
            Thread t = new Thread(this);
            t.start();
            t.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        Image icon;
        try {
            icon = Image.createImage("More/admin.png");
            this.deleteAll();
            for (int i = 0; i < adminArray.length; i++) {
                this.append(adminArray[i].getPrenom() + " " + adminArray[i].getNom(), icon);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Form getDetailsForm(int index) {
        Form f = new Form("Details");
        try {
            ImageItem img = new ImageItem(null, Image.createImage("More/admin_info.png"), Item.LAYOUT_CENTER, null);
            f.append(img);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        f.append(new StringItem("#ID:", "" + adminArray[index].getId()));
        f.append(new StringItem("Nom:", adminArray[index].getNom()));
        f.append(new StringItem("Prenom:", adminArray[index].getPrenom()));
        f.append(new StringItem("Mail:", adminArray[index].getMail()));
        f.append(new TextField("Password:", adminArray[index].getPassword(), 100, TextField.ANY));
        f.append(new StringItem("Privileges", ""));
        ChoiceGroup grp = new ChoiceGroup(null, List.MULTIPLE);
        grp.append("Ajouter", null);
        grp.append("Modifier", null);
        grp.append("Supprimer", null);
        boolean[] flags = new boolean[3];
        if ((adminArray[index].getPrivilege() & 1) == 1) {
            flags[0] = true;
        } else {
            flags[0] = false;
        }
        if ((adminArray[index].getPrivilege() & 2) == 2) {
            flags[1] = true;
        } else {
            flags[1] = false;
        }
        if ((adminArray[index].getPrivilege() & 4) == 4) {
            flags[2] = true;
        } else {
            flags[2] = false;
        }
        grp.setSelectedFlags(flags);
        f.append(grp);
        f.addCommand(back);
        f.addCommand(modify);
        f.addCommand(delete);
        f.setCommandListener(this);
        return f;
    }

    public void deleteAdmin() {
        try {
            operation = 3; // suppression
            Thread t = new Thread(this);
            t.start();
            t.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public Form addAdminForm() {
        Form f = new Form("Ajout");
        f.append(new TextField("Nom:", null, 500, TextField.ANY));
        f.append(new TextField("Prenom:", null, 500, TextField.ANY));
        f.append(new TextField("Mail:", null, 500, TextField.EMAILADDR));
        f.append(new TextField("Mot de passe:", null, 500, TextField.PASSWORD));
        f.append(new StringItem("Privileges", ""));
        ChoiceGroup grp = new ChoiceGroup(null, List.MULTIPLE);
        grp.append("Ajouter", null);
        grp.append("Modifier", null);
        grp.append("Supprimer", null);
        f.append(grp);
        f.addCommand(back);
        f.addCommand(add);
        f.setCommandListener(this);
        return f;
    }

    public void addAdmin() {
        try {
            operation = 2; // ajout
            Thread t = new Thread(this);
            t.start();
            t.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void modifierAdmin() {
        try {
            operation = 4; // modification
            Thread t = new Thread(this);
            t.start();
            t.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        try {
            if (operation == 1) {
                String url = "http://localhost/Pi_MOB_DAO/Administrateur/list.php";
                httpconnection = (HttpConnection) Connector.open(url);
                datainputstream = httpconnection.openDataInputStream();
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                AdministrateurHandler adminHandler = new AdministrateurHandler();
                saxParser.parse(datainputstream, adminHandler);
                adminArray = adminHandler.getAdminsList();
                httpconnection.close();
            } else if (operation == 2) {
                TextField nom = (TextField) add_form.get(0);
                TextField prenom = (TextField) add_form.get(1);
                TextField mail = (TextField) add_form.get(2);
                TextField password = (TextField) add_form.get(3);
                ChoiceGroup grp = (ChoiceGroup) add_form.get(5);
                if (nom.getString().length() > 0 && prenom.getString().length() > 0 && mail.getString().length() > 0 && password.getString().length() > 0) {
                    empty = false;
                    String url = "http://localhost/Pi_MOB_DAO/Administrateur/add.php?nom=" + nom.getString() + "&prenom=" + prenom.getString() + "&mail=" + mail.getString() + "&password=" + password.getString() + "&priv=" + getPriv(grp);
                    httpconnection = (HttpConnection) Connector.open(url);
                    datainputstream = httpconnection.openDataInputStream();
                    httpconnection.close();
                }
                else{
                    empty = true;
                }
            } else if (operation == 3) {
                String url = "http://localhost/Pi_MOB_DAO/Administrateur/supprimer.php?id=" + adminArray[this.getSelectedIndex()].getId();
                httpconnection = (HttpConnection) Connector.open(url);
                datainputstream = httpconnection.openDataInputStream();
                httpconnection.close();
            } else if (operation == 4) {
                TextField password = (TextField) details_form.get(5);
                ChoiceGroup grp = (ChoiceGroup) details_form.get(7);
                String url = "http://localhost/Pi_MOB_DAO/Administrateur/modify.php?id=" + adminArray[this.getSelectedIndex()].getId() + "&password=" + password.getString() + "&priv=" + getPriv(grp);
                httpconnection = (HttpConnection) Connector.open(url);
                datainputstream = httpconnection.openDataInputStream();
                httpconnection.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        }
    }

    public int getPriv(ChoiceGroup grp) {
        int res = 0;
        boolean[] flags = new boolean[3];
        grp.getSelectedFlags(flags);
        if (flags[0] == true) {
            res += 1;
        }
        if (flags[1] == true) {
            res += 2;
        }
        if (flags[2] == true) {
            res += 4;
        }
        return res;
    }
}
