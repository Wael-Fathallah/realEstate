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
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author Stack overFlow TEAM
 */
public class realEstateMidlet extends MIDlet implements CommandListener, Runnable  {
    //Global variable
    private Display display;
    private Alert errorAlert;
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
    private int         adminTorFIndex;
    //inscrire Screen
    private TextField   nom;
    private TextField   prenom;
    private ChoiceGroup ClOrGe;
    private Form        inscrireForm;
    private TextField   numFix;
    private TextField   numMob;
    private TextField   statM;
    
    Form f2 = new Form("Welcome");
    Form f3 = new Form("Erreur");
    public void startApp() {
        display = Display.getDisplay(this);
        display.setCurrent(loginSegment());
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c == exit) {
        destroyApp(false);
        notifyDestroyed();
        }
        if (c == next) {
            urlX="AUTH/login.php?mail="+email.getString().trim()+"&pass="+password.getString().trim()+"&user=1";
            Thread th = new Thread(this);
            th.start();
        }
        if (c == reg1) {
            display.setCurrent(inscrireCSegment());
        }
        
    }
    
    private Form loginSegment() {
        
        email = new TextField("Email :", "", 50, TextField.ANY);
        password = new TextField("Password :", "", 50, TextField.ANY);
        adminTorF = new ChoiceGroup("", Choice.MULTIPLE);
        adminTorF.append("Login Like Admin", null);
        loginForm = new Form("Login");
        next= new Command("Next", Command.OK, 0);
        exit= new Command("Exit", Command.EXIT, 0);
        reg1= new Command("S'inscrire like Client", Command.SCREEN, 0);
        reg2= new Command("S'inscrire like Gerant", Command.SCREEN, 0);
        
        loginForm.append(email);
        loginForm.append(password);
        adminTorFIndex = loginForm.append(adminTorF);
        loginForm.addCommand(next);
        loginForm.addCommand(exit);
        loginForm.addCommand(reg1);
        loginForm.addCommand(reg2);
        loginForm.setCommandListener(this);
        return loginForm;
        
    }
    
    private Form inscrireCSegment() {
        email = new TextField("Email :", "", 50, TextField.ANY);
        password = new TextField("Password :", "", 50, TextField.ANY);
        nom = new TextField("Nom :", "", 50, TextField.ANY);
        prenom = new TextField("Prenom :", "", 50, TextField.ANY);
        inscrireForm = new Form("Inscrire Client");
        next= new Command("Next", Command.OK, 0);
        exit= new Command("Exit", Command.EXIT, 0);
        reg1= new Command("S'inscrire like Client", Command.SCREEN, 0);
        reg2= new Command("S'inscrire like Gerant", Command.SCREEN, 0);
        
        inscrireForm.append(email);
        inscrireForm.append(password);
        inscrireForm.append(nom);
        inscrireForm.append(prenom);
        inscrireForm.addCommand(next);
        inscrireForm.addCommand(exit);
        inscrireForm.addCommand(reg1);
        inscrireForm.addCommand(reg2);
        inscrireForm.setCommandListener(this);
        return inscrireForm;
    }
    
    public void run() {
        try {
                hc = (HttpConnection) Connector.open(url+urlX);
                dis = new DataInputStream(hc.openDataInputStream());
                while ((ch = dis.read()) != -1) {
                    sb.append((char)ch);
                    
                }
                System.out.print(sb.toString().trim());
                System.out.print(adminTorFIndex);
                if ("OK".equals(sb.toString().trim())) {
                    display.setCurrent(f2);
                }else{
                    errorAlert = new Alert("Error", sb.toString().trim(), null,AlertType.ERROR);
                    errorAlert.setTimeout(3000);
                    display.setCurrent(errorAlert);
                }
                sb = new StringBuffer();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }
}
