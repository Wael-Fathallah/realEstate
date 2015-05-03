/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Sprint3;

import Entity.Adresse;
import Handler.AdresseHandler;
import Handler.OffreHandler;
import More.HttpMultipartRequest;
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
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;
import javax.microedition.lcdui.Image;

import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.lcdui.StringItem;

/**
 * @author seif
 */
public class offreAjoutForm extends Form implements CommandListener, Runnable, ItemCommandListener {
    
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
    Command uploadC = new Command("Upload image", Command.ITEM, 0);
    
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
    private Displayable lastDisplayed;
    
    /* special string denotes upper directory */
    private static final String UP_DIRECTORY = "..";
    
    /* special string that denotes upper directory accessible by this browser.
    * this virtual directory contains all roots.
    */
    private static final String MEGA_ROOT = "/";
    
    /* separator string as defined by FC specification */
    private static final String SEP_STR = "/";
    
    /* separator character as defined by FC specification */
    private static final char SEP = '/';
    private String currDirName;
    private Command view = new Command("View", Command.ITEM, 1);
    
    private Image dirIcon;
    private Image fileIcon;
    private Image[] iconList;
    
    private Command backC;
    private String imageName;
    
    //   args (this===>midletCourante,idGerant, displayable target apré validation)
    public offreAjoutForm(realEstateMidlet main, String idGerant, Displayable taget) {
        
        super("ajout");
        this.main = main;
        this.idGerant = idGerant;
        this.target = taget;
        currDirName = MEGA_ROOT;
        
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
        
        StringItem item = new StringItem("", "Upload image ", Item.BUTTON);
        item.setDefaultCommand(uploadC);
        item.setItemCommandListener(this);
        
        f2.append(item);
        
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
        // <editor-fold defaultstate="collapsed" desc=" Files View & upload Command ">
        if (c == view) {
            List curr = (List) d;
            final String currFile = curr.getString(curr.getSelectedIndex());
            new Thread(new Runnable() {
                public void run() {
                    if (currFile.endsWith(SEP_STR) || currFile.equals(UP_DIRECTORY)) {
                        traverseDirectory(currFile);
                    } else {
                        // Show file contents
                        showFile(currFile);
                    }
                }
            }).start();
        }
        // </editor-fold>
        
    }
    
    public void run() {
        
        try {
            
            HttpConnection hc1 = (HttpConnection) Connector.open(url + "?prix=" + tfpayement.getString().trim() + "&nbrpiece=" + tfnbrPiece.getString().trim()
                    + "&surface=" + tfsurface.getString().trim() + "&id_gerant=" + this.idGerant + "&etat=" + etatList.getString(etatList.getSelectedIndex())
                    + "&nature=" + natureList.getString(natureList.getSelectedIndex()) + "&typeimmob=" + typeImmobList.getString(typeImmobList.getSelectedIndex())
                    + "&urlImage=http://localhost/image/" + this.imageName + "&code=" + this.code + "&description=" + tfdescription.getString().trim().replace(' ', '+') + equipSelected
            );
            
            hc1.setRequestMethod("POST");
            
            System.out.println(hc1.getResponseMessage());
            
            Alert alert = new Alert("Success",
                    "Votre offre est ajouté  !",
                    null, AlertType.CONFIRMATION);
            
            main.display.setCurrent(alert);
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
    
    public void commandAction(Command c, Item item) {
        // <editor-fold defaultstate="collapsed" desc=" open Files View & upload Form ">
        if (c == uploadC) {
            try {
                showCurrDir();
            } catch (SecurityException e) {
                Alert alert
                        = new Alert("Error", "You are not authorized to access the restricted API", null,
                                AlertType.ERROR);
                alert.setTimeout(Alert.FOREVER);
                this.main.display.setCurrent(alert);
            } catch (Exception e) {
            }
        }
    }
    // </editor-fold>
    
    void showCurrDir() {
        Enumeration e;
        FileConnection currDir = null;
        List browser;
        
        iconList = new Image[]{fileIcon, dirIcon};
        try {
            if (MEGA_ROOT.equals(currDirName)) {
                e = FileSystemRegistry.listRoots();
                browser = new List(currDirName, List.IMPLICIT);
            } else {
                currDir = (FileConnection) Connector.open("file://localhost/" + currDirName);
                e = currDir.list();
                browser = new List(currDirName, List.IMPLICIT);
                // not root - draw UP_DIRECTORY
                browser.append(UP_DIRECTORY, dirIcon);
            }
            
            while (e.hasMoreElements()) {
                String fileName = (String) e.nextElement();
                
                if (fileName.charAt(fileName.length() - 1) == SEP) {
                    // This is directory
                    browser.append(fileName, dirIcon);
                } else {
                    // this is regular file
                    browser.append(fileName, fileIcon);
                }
            }
            backC = new Command("Back", Command.EXIT, 0);
            browser.setSelectCommand(view);
            browser.addCommand(backC);
            
            browser.setCommandListener(this);
            
            if (currDir != null) {
                currDir.close();
            }
            lastDisplayed = this.main.display.getCurrent();
            this.main.display.setCurrent(browser);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    protected void traverseDirectory(String fileName) {
        /* In case of directory just change the current directory
        * and show it
        */
        if (currDirName.equals(MEGA_ROOT)) {
            if (fileName.equals(UP_DIRECTORY)) {
                // can not go up from MEGA_ROOT
                return;
            }
            
            currDirName = fileName;
        } else if (fileName.equals(UP_DIRECTORY)) {
            // Go up one directory
            int i = currDirName.lastIndexOf(SEP, currDirName.length() - 2);
            
            if (i != -1) {
                currDirName = currDirName.substring(0, i + 1);
            } else {
                currDirName = MEGA_ROOT;
            }
        } else {
            currDirName = currDirName + fileName;
        }
        
        showCurrDir();
    }
    
    void showFile(String fileName) {
        try {
            FileConnection fc
                    = (FileConnection) Connector.open("file://localhost/" + currDirName + fileName);
            
            if (!fc.exists()) {
                throw new IOException("File does not exists");
            }
            
            InputStream fis = fc.openInputStream();
            
            // convert the inpustream to a byte array
            byte[] b = new byte[fis.available()];
            
            int length = fis.read(b, 0, fis.available());
            
            fis.close();
            fc.close();
            
            Hashtable params = new Hashtable();
            params.put("custom_param", "param_value");
            String random = randomString();
            System.out.println(random);
            fileName = randomString() + fileName;
            HttpMultipartRequest req = new HttpMultipartRequest(
                    "http://localhost/image/upM.php",
                    params,
                    "userfile", fileName, "image/jpg", b, length
            );
            imageName = fileName;
            
            if (req.send()) {
                Alert alert
                        = new Alert("Done!",
                                "File uploud with seccess ", null, AlertType.INFO);
                alert.setTimeout(3000);
                this.main.display.setCurrent(this.f2);
                
            }
        } catch (Exception e) {
            Alert alert
                    = new Alert("Error!",
                            "Can not access file " + fileName + " in directory " + currDirName
                                    + "\nException: " + e.getMessage(), null, AlertType.ERROR);
            alert.setTimeout(3000);
            this.main.display.setCurrent(alert);
        }
    }
    
    String randomString() {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        sb = new StringBuffer(8);
        for (int i = 0; i < 8; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }
    
}
