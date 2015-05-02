/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sprint3;

import Entity.Adresse;
import Handler.AdresseHandler;
import Handler.OffreHandler;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import java.util.*;

import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.lcdui.StringItem;

/**
 * @author seif
 */
public class offreAjoutForm extends Form implements CommandListener, Runnable {

    realEstateMidlet main;

    //les Listes
    List etatList = new List("Select etat", List.IMPLICIT);
    List typeImmobList = new List("Select type immobliler", List.IMPLICIT);
    List natureList = new List("Select nature ", List.IMPLICIT);
    List lstAdresse = new List("Select code postale", List.IMPLICIT);

    //textFieald
    TextField tfpayement = new TextField("payement", null, 100, TextField.NUMERIC);
    TextField tfsurface = new TextField("surface", null, 100, TextField.NUMERIC);
    TextField tfnbrPiece = new TextField("nombre piece", null, 100, TextField.NUMERIC);
    TextField tfdescription = new TextField("Description", null, 500, TextField.ANY);

    Command cmdValider = new Command("valider", Command.SCREEN, 0);
    Command cmdBack = new Command("cmdBack", Command.BACK, 0);
    Command cmdNextEtat = new Command("etat", Command.BACK, 0);
    Command cmdNextimmob = new Command("type Immob", Command.BACK, 0);
    Command cmdNextnature = new Command("nature", Command.BACK, 0);
    Command cmdNextEquipement = new Command("Next", Command.BACK, 0);
    Command cmdNextCode = new Command("Code postale", Command.BACK, 0);

    Alert alerta = new Alert("Error", "Sorry", null, AlertType.ERROR);
    //adresses
    Adresse[] adresses;
    //codePostal of an offre
    String code = "2081";

    ChoiceGroup equipementGp = new ChoiceGroup("Select Equipement ", Choice.MULTIPLE);
    String equipSelected = "";

    Form f2 = new Form("Welcome");
    Form f3 = new Form("Erreur");
    
    //Connexion
    HttpConnection hc;
    DataInputStream dis;
    String url = "http://localhost/pidev_sprint2/web/app_dev.php/v1/offres.xml";
    StringBuffer sb = new StringBuffer();
    int ch;
    private Object IOUtils;
    String idGerant;
    Displayable target;

    //   args (this===>midletCourante,idGerant, displayable target apré validation)
        
    public offreAjoutForm(realEstateMidlet main,String idGerant,Displayable taget) {

        super("ajout");
        this.main = main;
        this.idGerant=idGerant;
        this.target=target;

        //affectation des Etats
        etatList.append("Bonne", null);
        etatList.append("mauvaise", null);
        etatList.append("mediocre", null);
        etatList.append("nouvelle", null);

        typeImmobList.append("Villa", null);
        typeImmobList.append("Appartement", null);
        typeImmobList.append("Entrepot", null);
        typeImmobList.append("terrain", null);
        
         //getListe code Postale
        try {

            this.getAdresses();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        }
        
         //ChoiceGroupe
        
        equipementGp.append("Ascenseur", null);
        equipementGp.append("Cuisine", null);
        equipementGp.append("Jardin", null);
        equipementGp.append("entreeIndep", null);
        equipementGp.append("gazDeVille", null);
        equipementGp.append("Chauffage", null);
        equipementGp.append("Meuble", null);
        equipementGp.append("climatisation", null);

        natureList.append("Louer", null);
        natureList.append("Vente", null);

        etatList.addCommand(cmdNextimmob);
        etatList.setCommandListener(this);

        typeImmobList.addCommand(cmdNextnature);
        typeImmobList.setCommandListener(this);

        natureList.addCommand(cmdNextEquipement);
        natureList.setCommandListener(this);
        
        lstAdresse.addCommand(cmdBack);
        lstAdresse.addCommand(cmdValider);
        lstAdresse.setCommandListener(this);

        this.append(tfpayement);
        this.append(tfsurface);
        this.append(tfnbrPiece);
        this.append(tfdescription);
        
        f2.append(equipementGp);
        f2.addCommand(cmdNextCode);
        f2.setCommandListener(this);

        //affectation des commande
        this.addCommand(cmdNextEtat);
        this.setCommandListener(this);
        

    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {

        if (c == cmdNextEtat) {
            main.display.setCurrent(etatList);
        }
        if (c == cmdNextimmob) {
            main.display.setCurrent(typeImmobList);
        }
        if (c == cmdNextnature) {
            main.display.setCurrent(natureList);
        }
        if (c == cmdBack) {
            main.display.setCurrent(this);

        }
        if (c == cmdNextEquipement) {
            main.display.setCurrent(f2);

        }
        if (c == cmdNextCode) {

            main.display.setCurrent(lstAdresse);
            boolean selected[] = new boolean[equipementGp.size()];
            equipSelected = "";
            equipementGp.getSelectedFlags(selected);
            for (int i = 0; i < equipementGp.size(); i++) {
                equipSelected += "&" + equipementGp.getString(i) + (selected[i] ? "=1" : "=0");
            }

        }
        if (c == cmdValider) {
            Thread th = new Thread(this);
            th.start();
        }

        if (c == this.lstAdresse.SELECT_COMMAND) {

            this.code = lstAdresse.getString(lstAdresse.getSelectedIndex());
        }

    }

    public void run() {
      
            try {
          
           
          HttpConnection hc1 = (HttpConnection) Connector.open(url + "?prix=" + tfpayement.getString().trim() + "&nbrpiece=" + tfnbrPiece.getString().trim()
                    + "&surface=" + tfsurface.getString().trim() + "&id_gerant=" +this.idGerant+ "&etat=" + etatList.getString(etatList.getSelectedIndex())
                    + "&nature=" + natureList.getString(natureList.getSelectedIndex()) + "&typeimmob=" + typeImmobList.getString(typeImmobList.getSelectedIndex())
                    + "&urlImage=http://url" + "&code=" + this.code + "&description=" + tfdescription.getString().trim()+equipSelected
            );   
          
            hc1.setRequestMethod("POST");
            
            System.out.println(hc1.getResponseMessage());
           
             Alert alert = new Alert("Success",
                "Votre offre est ajouté  !",
                null, AlertType.CONFIRMATION);
             
               //main.display.setCurrent(alert);
               main.display.setCurrent(target);
             
            sb = new StringBuffer();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    
   }

    public void getAdresses() throws IOException, ParserConfigurationException, SAXException {

        AdresseHandler adresseHandler = new AdresseHandler();
        hc = (HttpConnection) Connector.open("http://localhost/pidev_sprint2/web/app_dev.php/v1/adresse.xml");
        dis = new DataInputStream(hc.openDataInputStream());
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();

        parser.parse(dis, adresseHandler);
        // display the result
        adresses = adresseHandler.getAdresses();

        if (adresses.length > 0) {
            for (int i = 0; i < adresses.length; i++) {

                this.lstAdresse.append(Integer.toString(adresses[i].getCode_pos()), null);

            }
        }

    }

}
