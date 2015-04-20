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

import Entity.Boitemessages;
import Handler.BoitemessagesHandler;
import java.io.DataInputStream;
import java.io.IOException;
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
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import net.mypapit.java.StringTokenizer;
import org.xml.sax.SAXException;

/**
 * @author Stack overFlow TEAM
 */
public class realEstateMidlet extends MIDlet implements CommandListener, Runnable  {
    //Global variable
    private Display display;
    private Alert   errorAlert;
    private Form    XForm;
    private String  runState;
    private Form    connectForm;
    private StringItem messageLabel;
    private String  myID = null;   //have to saved
    private String  myName = null; //have to saved
    private String  userName;      //have to saved
    private String  userPass;      //have to saved
    private int     userType;      //have to saved
    private int     CorG;
    //Connexion
    HttpConnection hc;
    DataInputStream dis;
    String url = "http://localhost/Pi_MOB_DAO/";
    String urlX = "";
    StringBuffer sb = new StringBuffer();
    int ch;
    //Login Screen 
    private TextField   email;
    private TextField   password;
    private ChoiceGroup adminTorF;
    private Form        loginForm;
    private Command     next;
    private Command     exit;
    private Command     reg1;
    private Command     reg2;
    //inscrire Screen
    private TextField   nom;
    private TextField   prenom;
    private Form        inscrireForm;
    private TextField   numFix;
    private TextField   numMob;
    private TextField   statM;
    
    //Inbox Screen
    private Boitemessages[] boitemessages;
    private List        lst;
    private Command     back;
    private Command     inbox;
    private Command     sent;
    private Command     Compose;
    private Command     send;
    private TextBox     bodyM;
    
    
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

    public void commandAction(Command c, Displayable d) {
        //Exit
        if (c == exit) {
        destroyApp(false);
        notifyDestroyed();
        }
        //Back
        if (c == back) {
            display.setCurrent(wellcomeSegment(myName));
        }
        
        //Login Command
        if (c == next && d == loginForm) {
            runState = "Login";
                userType = 1;
            if(adminTorF.isSelected(0)){
                userType = 0;
            }
            userName = email.getString().trim();
            userPass = password.getString().trim();
            urlX="AUTH/login.php?mail="+userName+"&pass="+userPass+"&user="+userType;
            Thread th = new Thread(this);
            th.start();
            display.setCurrent(connectingSegment());

        }
        //Inscrire Command
        if (c == next && d == inscrireForm) {
            runState = "Inscrire";
            userName = email.getString().trim();
            userPass = password.getString().trim();
            myName = nom.getString().trim() + " " + prenom.getString().trim();
            userType = 1;
            if(CorG==2){
                urlX="AUTH/regC.php?mail="+userName+"&pass="+userPass+"&nom="+nom.getString().trim()+"&prenom="+nom.getString().trim()+"&stat=";
            }else{
                urlX="AUTH/regC.php?mail="+userName+"&pass="+userPass+"&nom="+nom.getString().trim()+"&prenom="+nom.getString().trim()+"&stat=";
            }
            Thread th = new Thread(this);
            th.start();
            display.setCurrent(connectingSegment());

        }
        if (c == reg1) {
            
            display.setCurrent(inscrireCSegment());
            
        }
        if (c == reg2) {
            
            display.setCurrent(inscrireGSegment());
        }
        //MailBox Command
        if (c == inbox) {
            
            runState = "Inbox";
          
            urlX="Boitemessages/getXmlMessage.php?myID="+myID+"&IorS=I&mail="+userName+"&pass="+userPass+"&user="+userType;
            Thread th = new Thread(this);
            th.start();
            display.setCurrent(connectingSegment());
        }
        if (c == sent) {
            
            runState = "Sent";
            
            urlX="Boitemessages/getXmlMessage.php?myID="+myID+"&IorS=S&mail="+userName+"&pass="+userPass+"&user="+userType;
            Thread th = new Thread(this);
            th.start();
            display.setCurrent(connectingSegment());
        }
        if (c == Compose) {
            
            display.setCurrent(ComposeSegment());
        }
        if (c == send) {
            
            runState = "Send";
            
            urlX="Boitemessages/sendMessage.php?myID="+myID+"&mail="+userName+"&pass="+userPass+"&user="+userType+"&body="+bodyM.getString().trim()+"&sendMail="+"wael.fathallah@esprit.tn";
            Thread th = new Thread(this);
            th.start();
            display.setCurrent(connectingSegment());
        }
    }
    
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
    
    private Form inscrireCSegment() {
        CorG = 2;
        email = new TextField("Email :", "", 50, TextField.ANY);
        password = new TextField("Password :", "", 50, TextField.PASSWORD);
        nom = new TextField("Nom :", "", 50, TextField.ANY);
        prenom = new TextField("Prenom :", "", 50, TextField.ANY);
        inscrireForm = new Form("Inscrire Client");
        next= new Command("S'inscrire", Command.EXIT, 0);
        exit= new Command("Exit", Command.OK, 0);

        inscrireForm.append(email);
        inscrireForm.append(password);
        inscrireForm.append(nom);
        inscrireForm.append(prenom);
        inscrireForm.addCommand(next);
        inscrireForm.addCommand(exit);
        inscrireForm.setCommandListener(this);
        return inscrireForm;
    }
    
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
    private Form wellcomeSegment(String name) {
        
        XForm = new Form("Welcome " + name);
        //next= new Command("S'inscrire", Command.EXIT, 0);
        exit= new Command("Exit", Command.OK, 0);
        inbox= new Command("Inbox", Command.SCREEN, 0);
        XForm.addCommand(exit);
        XForm.addCommand(inbox);
        XForm.setCommandListener(this);
        return XForm;
    }
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
    private Form connectingSegment() {
        connectForm = new Form("Connecting");
        messageLabel = new StringItem(null, "Connecting...\nPlease wait.");
        connectForm.append(messageLabel);
        return connectForm;
        
    }
    public void run() {
        try {
            
            if("Login".equals(runState) || "Send".equals(runState) || "Inscrire".equals(runState)){
                
                hc = (HttpConnection) Connector.open(url+replace(urlX, " ", "+"));
                dis = new DataInputStream(hc.openDataInputStream());
                String tmp = null;
                while ((ch = dis.read()) != -1) {
                    sb.append((char)ch);
                              
                }
                    tmp = sb.toString().trim();
                    StringTokenizer tok;
                    tok = new StringTokenizer(tmp,"|");
                    tmp = tok.nextToken();
                    if("Login".equals(runState)){
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
                    }else if("Send".equals(runState)){
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
                    }else if("Inscrire".equals(runState)){
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
                
                sb = new StringBuffer();
            }
            if("Inbox".equals(runState) || "Sent".equals(runState)){
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
                    lst = new List("Inbox", List.IMPLICIT);

                    if (boitemessages.length > 0) {

                        for (int i = 0; i < boitemessages.length; i++) {
                            lst.append(boitemessages[i].getNom_expediteur()+" "
                                    +boitemessages[i].getPrenom_expediteur()+" "
                                    +boitemessages[i].getContenu().substring(0, Math.min(boitemessages[i].getContenu().length(), 10))+"...", null);
                        }
                    }
                }else{
                    lst = new List("Sent", List.IMPLICIT);
                    
                    if (boitemessages.length > 0) {

                        for (int i = 0; i < boitemessages.length; i++) {
                            lst.append(boitemessages[i].getNom_destinataire()+" "
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
                lst.addCommand(back);
                lst.addCommand(inbox);
                lst.addCommand(sent);
                lst.addCommand(Compose);
                lst.addCommand(exit);
                lst.setCommandListener(this);
                display.setCurrent(lst);
            }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        }
    }

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
    

    
}
