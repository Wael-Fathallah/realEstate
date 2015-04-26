/* 
 * Copyright 2015.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package Sprint3;

import Entity.*;
import Handler.*;
import More.*;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.io.Connector;
import javax.microedition.io.ContentConnection;
import javax.microedition.io.HttpConnection;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 * @author Stack overFlow TEAM
 */
public class realEstateMidlet extends MIDlet implements CommandListener, ItemCommandListener  {

    // <editor-fold defaultstate="collapsed" desc=" Global variable">
    private Display display;
    private Alert   errorAlert;
    private Form    XForm;
    private Image   appIcon;
    private String  runState;
    private Form    connectForm;
    private StringItem messageLabel;
    private String  myID = null;   //have to saved
    private String  myName = null; //have to saved
    private String  userName;      //have to saved
    private String  userPass;      //have to saved
    private int     userType;      //have to saved
    private int     CorG;
    private Displayable lastDisplayed;
    
     //Connexion
    HttpConnection hc;
    DataInputStream dis;
    String url = "http://localhost/Pi_MOB_DAO/";
    String urlX = "";
    StringBuffer sb = new StringBuffer();
    int ch;
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc=" Login Screen ">
    private TextField   email;
    private TextField   password;
    private ChoiceGroup adminTorF;
    private Form        loginForm;
    private Command     next;
    private Command     exit;
    private Command     reg1;
    private Command     reg2;
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc=" inscrire Screen">
    private TextField   nom;
    private TextField   prenom;
    private Form        inscrireForm;
    private TextField   numFix;
    private TextField   numMob;
    private TextField   statM;
    private Command     uploadC;
    private String      imageName;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" MailInbox Screen">
    private Boitemessages[] boitemessages;
    private List        lstB;
    private Command     back;
    private Command     inbox;
    private Command     sent;
    private Command     Compose;
    private Command     send;
    private TextBox     bodyM;
    private TextBox     oneMail;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Stat Screen">
    private Displayable statDis;
    private Command     statCom;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Offres Screen">
    private List        lstO;
    private Command     listOffreCom;
    private Offre [] offres;
    Form formO = new Form("Infos offre");
    private Command     mapC;
     
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Client Screen">
    private List        lstC;
    private Command listC;
    private Utilisateur[] utilisateur;
     
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Archive Screen">
    private List        lstA;
    private Command     archiveCom;
    private Archive [] archives;
    Form formA = new Form("Infos offre");
     
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Stat Screen">
    
    private Stat1 [] stat1;
     
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Am not here so leave me alone">
    public realEstateMidlet() {
        currDirName = MEGA_ROOT;
        //for loading images
        try {
            dirIcon = Image.createImage("/icons/dir.png");
        } catch (IOException e) {
            dirIcon = null;
        }

        try {
            fileIcon = Image.createImage("/icons/file.png");
        } catch (IOException e) {
            fileIcon = null;
        }
        try {
            appIcon = Image.createImage("/logo.png");
        } catch (IOException e) {
            appIcon = null;
        }
    }
    
    public void startApp() {
        
        display = Display.getDisplay(this);
        if(myID!=null){
        display.setCurrent(wellcomeSegment(myName));   
        }else{
        display.setCurrent(loginSegment());}

    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    // </editor-fold> 
    
    public void commandAction(Command c, Displayable d) {
        
        // <editor-fold defaultstate="collapsed" desc=" Exit & Back to homeScrean Command ">
        if (c == exit) {
            destroyApp(false);
            notifyDestroyed();
        }
        //Back
        if (c == back && d != inscrireForm && d != formA && d != formO && d != oneMail) {
            display.setCurrent(wellcomeSegment(myName));
        }
        //Back
        if (c == back && d == inscrireForm) {
            display.setCurrent(loginSegment());
        }
        //Back
        if (c == back && d == formA) {
            display.setCurrent(lstA);
        }
        //Back
        if (c == back && d == formO) {
            display.setCurrent(lstO);
        }
        //Back
        if (c == back && d == oneMail) {
            display.setCurrent(lstB);
        }
        // </editor-fold> 
        
        // <editor-fold defaultstate="collapsed" desc=" Login Command ">
        if (c == next && d == loginForm) {
            runState = "Login";
            userType = 1;
            if(adminTorF.isSelected(0)){
                userType = 0;
            }
            userName = email.getString().trim();
            userPass = password.getString().trim();
            urlX="AUTH/login.php?mail="+userName+"&pass="+userPass+"&user="+userType;
            lastDisplayed = display.getCurrent();
            display.setCurrent(connectingSegment());
            new Thread(new Runnable() {
                public void run() {
                    
                    if(setData(lastDisplayed)){
                        String tmp = null;
                        tmp = sb.toString().trim();
                        StringTokenizer tok;
                        tok = new StringTokenizer(tmp,"|");
                        tmp = tok.nextToken();
                        if ("OK2".equals(tmp)) {
                            CorG = 2;
                            myID = tok.nextToken();
                            myName = tok.nextToken();
                            display.setCurrent(wellcomeSegment(myName));
                        }else if ("OK1".equals(tmp)) {
                            CorG = 1;
                            myID = tok.nextToken();
                            myName = tok.nextToken();
                            display.setCurrent(wellcomeSegment(myName));
                        }else if ("OKA".equals(tmp)) {
                            CorG = 0;
                            myID = "0";
                            myName = "Admin";
                            display.setCurrent(wellcomeSegment(myName));
                        }else{
                            display.setCurrent(loginSegment());
                            errorAlert = new Alert("Error", sb.toString().trim(), null,AlertType.ERROR);
                            errorAlert.setTimeout(3000);
                            display.setCurrent(errorAlert);
                        }
                    }
                }
            }).start();
            
        }
        // </editor-fold> 
        
        // <editor-fold defaultstate="collapsed" desc=" Inscrire Command ">
        if (c == next && d == inscrireForm) {
            runState = "Inscrire";
            userName = email.getString().trim();
            userPass = password.getString().trim();
            myName = nom.getString().trim() + " " + prenom.getString().trim();
            userType = 1;
            lastDisplayed = display.getCurrent();
            if(CorG==2){
                urlX="AUTH/regC.php?mail="+userName+"&pass="+userPass+"&nom="+nom.getString().trim()+"&prenom="+prenom.getString().trim()+"&stat="+"&imgurl="+imageName;
            }else{
                urlX="AUTH/regC.php?mail="+userName+"&pass="+userPass+"&nom="+nom.getString().trim()+"&prenom="+prenom.getString().trim()+"&stat="+"&imgurl="+imageName;
            }
            display.setCurrent(connectingSegment());
            new Thread(new Runnable() {
                public void run() {
                    
                    
                    if(setData(lastDisplayed)){
                        String tmp = null;
                        tmp = sb.toString().trim();
                        StringTokenizer tok;
                        tok = new StringTokenizer(tmp,"|");
                        tmp = tok.nextToken();
                        if ("OKR".equals(tmp)) {
                            tok = new StringTokenizer(sb.toString().trim(),"|");
                            tmp = tok.nextToken();
                            tmp = tok.nextToken();
                            display.setCurrent(wellcomeSegment(myName));
                            myID =tmp;
                            errorAlert = new Alert("Done", "Registration done with success", null,AlertType.INFO);
                            errorAlert.setTimeout(3000);
                            display.setCurrent(errorAlert);
                            
                        }else{
                            
                            display.setCurrent(inscrireForm);
                            
                            errorAlert = new Alert("Error", sb.toString().trim(), null,AlertType.ERROR);
                            errorAlert.setTimeout(3000);
                            display.setCurrent(errorAlert);
                        }
                    }
                }
            }).start();
        }
        if (c == reg1) {
            
            display.setCurrent(inscrireCSegment());
            
        }
        if (c == reg2) {
            
            display.setCurrent(inscrireGSegment());
        }
        // </editor-fold> 
        
        // <editor-fold defaultstate="collapsed" desc=" ListClients Command ">
         if (c == listC) {
            
            runState = "ListC";
            lastDisplayed = display.getCurrent();
            urlX="Utilisateur/getXmlClientsC.php";
            new Thread(new Runnable() {
                    public void run() {
                        try {              
                            UtilisateurHandler(lastDisplayed);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();       

            display.setCurrent(connectingSegment());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc=" MailBox Commands ">
        if (c == inbox) {
            
            runState = "Inbox";
            lastDisplayed = display.getCurrent();
            urlX="Boitemessages/getXmlMessage.php?myID="+myID+"&IorS=I&mail="+userName+"&pass="+userPass+"&user="+userType;
            display.setCurrent(connectingSegment());
            new Thread(new Runnable() {
                    public void run() {
                        try {              
                            BoitemessagesHandler(lastDisplayed);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();
        }
        if (c == sent) {
            
            runState = "Sent";
            lastDisplayed = display.getCurrent();
            urlX="Boitemessages/getXmlMessage.php?myID="+myID+"&IorS=S&mail="+userName+"&pass="+userPass+"&user="+userType;
            display.setCurrent(connectingSegment());
            new Thread(new Runnable() {
                    public void run() {
                        try {              
                            BoitemessagesHandler(lastDisplayed);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();       
        }
        if (c == Compose) {
            
            display.setCurrent(ComposeSegment());
        }
        if (c == send) {

            urlX="Boitemessages/sendMessage.php?myID="+myID+"&mail="+userName+"&pass="+userPass+"&user="+userType+"&body="+bodyM.getString().trim()+"&sendMail="+"wael.fathallah@esprit.tn";
            lastDisplayed = display.getCurrent();
            display.setCurrent(connectingSegment());
            new Thread(new Runnable() {
                    public void run() {
                        
                            
                        if(setData(lastDisplayed)){
                            String tmp = null;
                            tmp = sb.toString().trim();
                            StringTokenizer tok;
                            tok = new StringTokenizer(tmp,"|");
                            tmp = tok.nextToken();
                            if ("OKS".equals(tmp)) {
                                display.setCurrent(wellcomeSegment(myName));
                                errorAlert = new Alert("Done", "Message send with success", null,AlertType.INFO);
                                errorAlert.setTimeout(3000);
                                display.setCurrent(errorAlert);
                            
                            }else{
                                errorAlert = new Alert("Error", sb.toString().trim(), null,AlertType.ERROR);
                                errorAlert.setTimeout(3000);
                                display.setCurrent(errorAlert);
                            }
                        }                 
                    }
                }).start();

        }
        if (c == List.SELECT_COMMAND && d == lstB) {
           if("Inbox".equals(runState)){
               
                oneMail = new TextBox("From "+boitemessages[lstB.getSelectedIndex()].getNom_expediteur()+" "+   
                                        boitemessages[lstB.getSelectedIndex()].getPrenom_expediteur(), 
                                        boitemessages[lstB.getSelectedIndex()].getContenu(), 
                                        200, TextField.ANY|TextField.UNEDITABLE);
           }else if("Sent".equals(runState)){
                oneMail = new TextBox("To "+boitemessages[lstB.getSelectedIndex()].getNom_destinataire()+" "+   
                                        boitemessages[lstB.getSelectedIndex()].getPrenom_destinataire(), 
                                        boitemessages[lstB.getSelectedIndex()].getContenu(), 
                                        200, TextField.ANY|TextField.UNEDITABLE);
           }
           oneMail.setCommandListener(this);
           back = new Command("Back", Command.EXIT, 0);
           oneMail.addCommand(back);
           display.setCurrent(oneMail);
        }
        // </editor-fold> 
        
        // <editor-fold defaultstate="collapsed" desc=" Files View & upload Command ">
        if (c == view) {
            List curr = (List)d;
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
        
        // <editor-fold defaultstate="collapsed" desc=" Stat Command ">
        if (c == statCom) {
            lastDisplayed = display.getCurrent();
            new Thread(new Runnable() {
                    public void run() {
                        try {              
                            stat1 = Stat1Handler(lastDisplayed);
                            Integer.parseInt(stat1[0].getV5());
                            System.err.println(stat1[0].getV4());
                            int[] data = { Integer.parseInt(stat1[0].getV5()), Integer.parseInt(stat1[0].getV4()), 
                                            Integer.parseInt(stat1[0].getV3()), Integer.parseInt(stat1[0].getV2()), 
                                            Integer.parseInt(stat1[0].getV1()) };
                            display.setCurrent(statSegment(data));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();   
            
        }
        // </editor-fold> 
        
        // <editor-fold defaultstate="collapsed" desc=" offre Command ">
        if (c == listOffreCom) {
            lastDisplayed = display.getCurrent();
            display.setCurrent(connectingSegment());
            new Thread(new Runnable() {
                    public void run() {
                        try {              
                            OffreHandler(lastDisplayed);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();   
        }
         if (c == List.SELECT_COMMAND && d == lstO) {
           
            formO.append("Informations Offre: \n");
            formO.append(showOffre(lstO.getSelectedIndex()));
            try {
                Image im=this.getImage(lstO.getSelectedIndex());
                ImageItem ii = new ImageItem(null, im, ImageItem.LAYOUT_DEFAULT, null);
                formO.append(ii);
                mapC= new Command("Show MAP", Command.ITEM, 0);
                StringItem item = new StringItem("", "Show MAP ", Item.BUTTON);
                item.setItemCommandListener(this);
                item.setDefaultCommand(mapC);
                
                formO.append(item);
                formO.setCommandListener(this);
                back = new Command("Back", Command.EXIT, 0);
                formO.addCommand(back);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            display.setCurrent(formO);
        }
        // </editor-fold>
         
        // <editor-fold defaultstate="collapsed" desc=" archive Command ">
        if (c == archiveCom) {
            lastDisplayed = display.getCurrent();
            display.setCurrent(connectingSegment());
            new Thread(new Runnable() {
                    public void run() {
                        try {              
                            ArchiveHandler(lastDisplayed);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();   
        }
         if (c == List.SELECT_COMMAND && d == lstA) {
           
            formA.append("Informations Archive: \n");
            formA.append(showArchive(lstA.getSelectedIndex()));
            try {
                Image im=this.getImage(lstA.getSelectedIndex());
                ImageItem ii = new ImageItem(null, im, ImageItem.LAYOUT_DEFAULT, null);
                formA.append(ii);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            display.setCurrent(formA);
        }
        // </editor-fold>
    }
    public void commandAction(Command c, Item item) {
        
        // <editor-fold defaultstate="collapsed" desc=" open Files View & upload Form ">
        if (c == uploadC) {
            try {
                showCurrDir();
            } catch (SecurityException e) {
                Alert alert =
                        new Alert("Error", "You are not authorized to access the restricted API", null,
                                AlertType.ERROR);
                alert.setTimeout(Alert.FOREVER);
                Display.getDisplay(this).setCurrent(alert);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // </editor-fold> 
        
        // <editor-fold defaultstate="collapsed" desc=" open MAP ">
        if (c == mapC) {
            display.setCurrent(connectingSegment());
            
            float lon = (float) 37.212487;
            float lalt = (float) 10.125656;
            display.setCurrent(new GoogleMapsMarkerCanvas(this, formO, lon, lalt));  
        }
        // </editor-fold> 
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Segments Block ">
    
    // <editor-fold defaultstate="collapsed" desc=" loginSegment ">
    private Form loginSegment() {
        
        email = new TextField("Email :", "", 50, TextField.ANY);
        password = new TextField("Password :", "", 50, TextField.PASSWORD);
        adminTorF = new ChoiceGroup("", Choice.MULTIPLE);
        adminTorF.append("Login Like Admin", null);
        loginForm = new Form("Login");
        next= new Command("Next", Command.EXIT, 0);
        
        reg1= new Command("S'inscrire like Client", Command.SCREEN, 0);
        reg2= new Command("S'inscrire like Gerant", Command.SCREEN, 0);
        exit= new Command("Exit", Command.SCREEN, 0);
        
        loginForm.append(new ImageItem(null, appIcon, ImageItem.LAYOUT_CENTER|ImageItem.LAYOUT_NEWLINE_AFTER, null));
        loginForm.append(email);
        loginForm.append(password);
        loginForm.append(adminTorF);
        loginForm.addCommand(next);
        
        loginForm.addCommand(reg1);
        loginForm.addCommand(reg2);
        loginForm.addCommand(exit);
        loginForm.setCommandListener(this);
        return loginForm;
        
    }
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc=" inscrireCSegment ">
    private Form inscrireCSegment() {
        CorG = 2;
        email = new TextField("Email :", "", 50, TextField.ANY);
        password = new TextField("Password :", "", 50, TextField.PASSWORD);
        nom = new TextField("Nom :", "", 50, TextField.ANY);
        prenom = new TextField("Prenom :", "", 50, TextField.ANY);
        inscrireForm = new Form("Inscrire Client");
        next= new Command("S'inscrire", Command.EXIT, 0);
        exit= new Command("Exit", Command.OK, 0);
        uploadC= new Command("Uploud image", Command.ITEM, 0);
        StringItem item = new StringItem("", "Uploud image ", Item.BUTTON);
        item.setDefaultCommand(uploadC);
        item.setItemCommandListener(this);
        inscrireForm.append(email);
        inscrireForm.append(password);
        inscrireForm.append(nom);
        inscrireForm.append(prenom);
        inscrireForm.append(item);
        inscrireForm.addCommand(next);
        inscrireForm.addCommand(exit);
        inscrireForm.setCommandListener(this);
        return inscrireForm;
    }
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc=" inscrireGSegment ">
    private Form inscrireGSegment() {
        CorG = 1;
        email = new TextField("Email :", "", 50, TextField.ANY);
        password = new TextField("Password :", "", 50, TextField.PASSWORD);
        nom = new TextField("Nom :", "", 50, TextField.ANY);
        prenom = new TextField("Prenom :", "", 50, TextField.ANY);
        numMob = new TextField("Numero Mobile :", "", 50, TextField.PHONENUMBER);
        numFix = new TextField("Numero Fix :", "", 50, TextField.PHONENUMBER);
        statM = new TextField("Statu Matri :", "", 50, TextField.ANY);
        inscrireForm = new Form("Inscrire Gerant");
        next= new Command("S'inscrire", Command.EXIT, 0);
        exit= new Command("Exit", Command.OK, 0);
        
        inscrireForm.append(email);
        inscrireForm.append(password);
        inscrireForm.append(nom);
        inscrireForm.append(prenom);
        inscrireForm.append(numMob);
        inscrireForm.append(numFix);
        inscrireForm.append(statM);
        inscrireForm.addCommand(next);
        inscrireForm.addCommand(exit);
        inscrireForm.setCommandListener(this);
        return inscrireForm;
    }
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc=" wellcomeSegment ">
    private Form wellcomeSegment(String name) {
        
        XForm = new Form("Welcome " + name);
        //next= new Command("S'inscrire", Command.EXIT, 0);
        exit= new Command("Exit", Command.OK, 0);
        inbox= new Command("Inbox", Command.SCREEN, 0);
        statCom= new Command("Stat", Command.SCREEN, 0);
        listC   =   new Command("List Clients", Command.SCREEN, 0);
        listOffreCom  =   new Command("List Offre", Command.SCREEN, 0);
        XForm.addCommand(exit);
        XForm.addCommand(inbox);
        XForm.addCommand(listC);
        XForm.addCommand(statCom);
        XForm.addCommand(listOffreCom);
        XForm.setCommandListener(this);
        return XForm;
    }
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc=" statSegment ">
    private Displayable statSegment(int[] data) {
        
        statDis = new PieChartCanvas(data);
        back = new Command("Back", Command.EXIT, 0);

        statDis.addCommand(back);
        statDis.setCommandListener(this);
        return statDis;
    }
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc=" ComposeSegment ">
    private TextBox ComposeSegment() {
        
        exit= new Command("Exit", Command.OK, 0);
        send= new Command("Send", Command.SCREEN, 0);
        back   =   new Command("Back", Command.EXIT, 0);
        inbox  =   new Command("Inbox", Command.OK, 0);
        sent   =   new Command("Sent", Command.OK, 0);
        bodyM = new TextBox("Compose a massege ", "", 200, TextField.ANY);
        
        bodyM.addCommand(send);
        bodyM.addCommand(inbox);
        bodyM.addCommand(sent);
        bodyM.addCommand(back);
        bodyM.addCommand(exit);
        bodyM.setCommandListener(this);
        return bodyM;
    }
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc=" connectingSegment ">
    private Form connectingSegment() {
        connectForm = new Form("Connecting");
        messageLabel = new StringItem(null, "Connecting...\nPlease wait.");
        connectForm.append(messageLabel);
        return connectForm;
        
    }
    // </editor-fold> 
    
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc=" Extra Code Block ">
    
    // <editor-fold defaultstate="collapsed" desc=" Code for uploud ">

    private static final String[] attrList = { "Read", "Write", "Hidden" };
    private static final String[] typeList = { "Regular File", "Directory" };
    
    
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
    
    
    
    /**
     * Show file list in the current directory .
     */
    void showCurrDir() {
        Enumeration e;
        FileConnection currDir = null;
        List browser;
        
        
        
        
        iconList = new Image[] { fileIcon, dirIcon };
        try {
            if (MEGA_ROOT.equals(currDirName)) {
                e = FileSystemRegistry.listRoots();
                browser = new List(currDirName, List.IMPLICIT);
            } else {
                currDir = (FileConnection)Connector.open("file://localhost/" + currDirName);
                e = currDir.list();
                browser = new List(currDirName, List.IMPLICIT);
                // not root - draw UP_DIRECTORY
                browser.append(UP_DIRECTORY, dirIcon);
            }
            
            while (e.hasMoreElements()) {
                String fileName = (String)e.nextElement();
                
                if (fileName.charAt(fileName.length() - 1) == SEP) {
                    // This is directory
                    browser.append(fileName, dirIcon);
                } else {
                    // this is regular file
                    browser.append(fileName, fileIcon);
                }
            }
            
            browser.setSelectCommand(view);
            //browser.addCommand(back);
            
            
            
            
            browser.setCommandListener(this);
            
            if (currDir != null) {
                currDir.close();
            }
            
            Display.getDisplay(this).setCurrent(browser);
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
            FileConnection fc =
                    (FileConnection)Connector.open("file://localhost/" + currDirName + fileName);
            
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
            
            
            HttpMultipartRequest req = new HttpMultipartRequest(
                    "http://localhost/image/upM.php",
                    params,
                    "userfile", fileName, "image/jpg", b, length
            );
            imageName = fileName;
            
            if (req.send()) {
                Alert alert =
                        new Alert("Done!",
                                "File uploud with seccess ", null, AlertType.INFO);
                alert.setTimeout(3000);
                Display.getDisplay(this).setCurrent(inscrireForm);
                Display.getDisplay(this).setCurrent(alert);
            }
        } catch (Exception e) {
            Alert alert =
                    new Alert("Error!",
                            "Can not access file " + fileName + " in directory " + currDirName +
                                    "\nException: " + e.getMessage(), null, AlertType.ERROR);
            alert.setTimeout(3000);
            Display.getDisplay(this).setCurrent(alert);
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" setData ">
    boolean setData(Displayable lastDisplayeb){
        try {
            sb = new StringBuffer();
            hc = (HttpConnection) Connector.open(url+replace(urlX, " ", "+"));
            dis = new DataInputStream(hc.openDataInputStream());
            while ((ch = dis.read()) != -1) {
                sb.append((char)ch);
                
            }
            return true;
        } catch (IOException ex) {
            problemCallMe(lastDisplayeb);
            return false;
        }
    }
    // </editor-fold>
 
    // <editor-fold defaultstate="collapsed" desc=" replace ">
    private String replace( String str, String pattern, String replace )
    {
        int s = 0;
        int e = 0;
        StringBuffer result = new StringBuffer();
        
        while ( (e = str.indexOf( pattern, s ) ) >= 0 )
        {
            result.append(str.substring( s, e ) );
            result.append( replace );
            s = e+pattern.length();
        }
        result.append( str.substring( s ) );
        return result.toString();
    }
    // </editor-fold>    
    
    // <editor-fold defaultstate="collapsed" desc=" BoitemessagesHandler ">
    void BoitemessagesHandler(Displayable lastDisplayeb) throws IOException{
        try {
            // this will handle our XML
            BoitemessagesHandler boiteHandler = new BoitemessagesHandler();
            // get a parser object
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // get an InputStream from somewhere (could be HttpConnection, for example)
            hc = (HttpConnection) Connector.open(url+replace(urlX, " ", "+"));
            dis = new DataInputStream(hc.openDataInputStream());
            parser.parse(dis, boiteHandler);
            // display the result
            boitemessages = boiteHandler.getBoitemessages();
            if("Inbox".equals(runState)){
                lstB = new List("Inbox", List.IMPLICIT);
                lstB.setCommandListener(this);
                if (boitemessages.length > 0) {
                    
                    for (int i = 0; i < boitemessages.length; i++) {
                        lstB.append(boitemessages[i].getNom_expediteur()+" "
                                +boitemessages[i].getPrenom_expediteur()+" "
                                +boitemessages[i].getContenu().substring(0, Math.min(boitemessages[i].getContenu().length(), 10))+"...", null);
                    }
                }
            }else{
                lstB = new List("Sent", List.IMPLICIT);
                
                if (boitemessages.length > 0) {
                    
                    for (int i = 0; i < boitemessages.length; i++) {
                        lstB.append(boitemessages[i].getNom_destinataire()+" "
                                +boitemessages[i].getPrenom_destinataire()+" "
                                +boitemessages[i].getContenu().substring(0, Math.min(boitemessages[i].getContenu().length(), 10))+"...", null);
                    }
                }
                
            }
            
            back   =   new Command("Back", Command.EXIT, 0);
            inbox  =   new Command("Inbox", Command.OK, 0);
            Compose   =   new Command("Compose", Command.OK, 0);
            sent   =   new Command("Sent", Command.OK, 0);
            exit   =   new Command("Exit", Command.OK, 0);
            lstB.addCommand(back);
            lstB.addCommand(inbox);
            lstB.addCommand(sent);
            lstB.addCommand(Compose);
            lstB.addCommand(exit);
            lstB.setCommandListener(this);
            display.setCurrent(lstB);
        } catch (ParserConfigurationException ex) {
            problemCallMe(lastDisplayeb);          
        } catch (SAXException ex) {
            problemCallMe(lastDisplayeb);
        }
    }
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc=" UtilisateurHandler ">
    void UtilisateurHandler(Displayable lastDisplayeb) throws IOException{
        try {
            UtilisateurHandler utilisateurHandler = new UtilisateurHandler();
                // get a parser object
                SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
                // get an InputStream from somewhere (could be HttpConnection, for example)
                hc = (HttpConnection) Connector.open(url+replace(urlX, " ", "+"));
                dis = new DataInputStream(hc.openDataInputStream());
                parser.parse(dis, utilisateurHandler);
                // display the result
                utilisateur = utilisateurHandler.getUtilisateur();
                lstC = new List("List Client", List.IMPLICIT);
                    
                    if (utilisateur.length > 0) {

                        for (int i = 0; i < utilisateur.length; i++) {
                            lstC.append(utilisateur[i].getNom()+" "
                                    +utilisateur[i].getPrenom()+" "
                                    +utilisateur[i].getMail(), null);
                        }
                    }
                back   =   new Command("Back", Command.EXIT, 0);
                exit   =   new Command("Exit", Command.OK, 0);
                
                lstC.addCommand(back);
                lstC.addCommand(exit);
                lstC.setCommandListener(this);
                display.setCurrent(lstC);
        } catch (ParserConfigurationException ex) {
            problemCallMe(lastDisplayeb);
        } catch (SAXException ex) {
            problemCallMe(lastDisplayeb);
        }
    }
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc=" OffreHandler ">
    void OffreHandler(Displayable lastDisplayeb) throws IOException{
        try {
            // this will handle our XML
            OffreHandler offresHandler = new OffreHandler();
            // get a parser object
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // get an InputStream from somewhere (could be HttpConnection, for example)
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/pidev_sprint2/web/app_dev.php/v1/offres.xml?limit=2");
            DataInputStream dis = new DataInputStream(hc.openDataInputStream());
            parser.parse(dis, offresHandler);
            // display the result
            offres = offresHandler.getOffres();
            lstO = new List("List Offre", List.IMPLICIT);
            lstO.setCommandListener(this);
            if (offres.length > 0) {
                for (int i = 0; i < offres.length; i++) {
                    
                    lstO.append(offres[i].getDescription(), this.getImage(i));
                    
                }
            }
            display.setCurrent(lstO);
        } catch (ParserConfigurationException ex) {
            problemCallMe(lastDisplayeb);
        } catch (SAXException ex) {
            problemCallMe(lastDisplayeb);
        }
    }
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc=" getImage params index de l'image dans la liste ">
    private Image getImage(int i) throws IOException {
        ContentConnection connection = (ContentConnection) Connector.open(offres[i].getUrlImage());

    // * There is a bug in MIDP 1.0.3 in which read() sometimes returns
        //   an invalid length. To work around this, I have changed the 
        //   stream to DataInputStream and called readFully() instead of read()
    //    InputStream iStrm = connection.openInputStream();
        DataInputStream iStrm = connection.openDataInputStream();

        ByteArrayOutputStream bStrm = null;
        Image im = null;

        try {
            // ContentConnection includes a length method
            byte imageData[];
            int length = (int) connection.getLength();
            if (length != -1) {
                imageData = new byte[length];

                // Read the png into an array
                // iStrm.read(imageData);        
                iStrm.readFully(imageData);
            } else // Length not available...
            {
                bStrm = new ByteArrayOutputStream();

                int ch;
                while ((ch = iStrm.read()) != -1) {
                    bStrm.write(ch);
                }

                imageData = bStrm.toByteArray();
                bStrm.close();
            }

            // Create the image from the byte array
            im = Image.createImage(imageData, 0, imageData.length);
        } finally {
            // Clean up
            if (iStrm != null) {
                iStrm.close();
            }
            if (connection != null) {
                connection.close();
            }
            if (bStrm != null) {
                bStrm.close();
            }
        }
        return (im == null ? null : im);
    }
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc=" ShowOffre ">
    private String showOffre(int i) {
        String res = "";
        sb = new StringBuffer();
        if (offres.length > 0) {
            sb.append("*id: ");
            sb.append(offres[i].getId());
            sb.append("\n");
            sb.append("*idGerant: ");
            sb.append(offres[i].getId_gerant());
            sb.append("\n");
            sb.append("*typeImmob: ");
            sb.append(offres[i].getTypeImmob());
            sb.append("\n");
            sb.append("*etat: ");
            sb.append(offres[i].getEtat());
            sb.append("\n");
            sb.append("*nature: ");
            sb.append(offres[i].getNature());
            sb.append("\n");
            sb.append("*payement: ");
            sb.append(offres[i].getPayement());
            sb.append("\n");
            sb.append("*surface: ");
            sb.append(offres[i].getSurface());

        }
        res = sb.toString();
        sb = new StringBuffer("");
        return res;
    }
      // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc=" ArchiveHandler ">
    void ArchiveHandler(Displayable lastDisplayeb) throws IOException{
        try {
            // this will handle our XML
            ArchiveHandler ArchivesHandler = new ArchiveHandler();
            // get a parser object
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // get an InputStream from somewhere (could be HttpConnection, for example)
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/pidevfinal3/pidev_sprint2/web/app_dev.php/v1/archives.xml");
            DataInputStream dis = new DataInputStream(hc.openDataInputStream());
            parser.parse(dis, ArchivesHandler);
            // display the result
            archives = ArchivesHandler.getArchive();
            lstA = new List("Archives", List.IMPLICIT);
            lstA.setCommandListener(this);
            if (archives.length > 0) {
                for (int i = 0; i < archives.length; i++) {
                    lstA.append(archives[i].getIdGerant()+" "
                            +archives[i].getDate()+" "
                            +archives[i].getUrl(), null);

                }
            }
         display.setCurrent(lstA);
        } catch (ParserConfigurationException ex) {
            problemCallMe(lastDisplayeb);
        } catch (SAXException ex) {
            problemCallMe(lastDisplayeb);
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" showArchive ">
    private String showArchive(int i) {
        String res = "";
        sb = new StringBuffer();
        if (archives.length > 0) {
            sb.append("* ");
            sb.append(archives[i].getId());
            sb.append("\n");
            sb.append("* ");
            sb.append(archives[i].getIdGerant());
            sb.append("\n");
            sb.append("* ");
            sb.append(archives[i].getDate());
            sb.append("\n");
            sb.append("* ");
            sb.append(archives[i].getUrl());
            sb.append("\n");
        }
        res = sb.toString();
        sb = new StringBuffer("");
        return res;
    }
      // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc=" StatHandler ">
    Stat1 [] Stat1Handler(Displayable lastDisplayeb) throws IOException{
        try {
            // this will handle our XML
            Stat1Handler Stat1Handler = new Stat1Handler();
            // get a parser object
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // get an InputStream from somewhere (could be HttpConnection, for example)
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/pi_mob_dao/Offre/Stat/getXmlStat1.php");
            DataInputStream dis = new DataInputStream(hc.openDataInputStream());
            parser.parse(dis, Stat1Handler);
            // display the result
            stat1 = Stat1Handler.getStat1();
            return stat1;
            
        } catch (ParserConfigurationException ex) {
            problemCallMe(lastDisplayeb);
        } catch (SAXException ex) {
            problemCallMe(lastDisplayeb);
        }
        return null;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" problemCallMe ">
    void problemCallMe(Displayable lastDisplayeb){
        display.setCurrent(lastDisplayeb);
        errorAlert = new Alert("Error ", "No Connection", null,AlertType.ERROR);
        errorAlert.setTimeout(3000);
        display.setCurrent(errorAlert);
    }
    // </editor-fold> 
    
    // </editor-fold> 

}
