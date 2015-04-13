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
    //Connexion
    HttpConnection hc;
    DataInputStream dis;
    String url = "http://localhost/parsing/";
    String urlX = "";
    StringBuffer sb = new StringBuffer();
    int ch;
    //Login Screen 
    private TextField   email;
    private TextField   password;
    private Form        loginForm;
    private Command     next;
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
        
        if (c == next) {
            Thread th = new Thread(this);
            th.start();
        }
        
    }
    
    private Form loginSegment() {
        
        email = new TextField("Email :", "", 50, TextField.ANY);
        password = new TextField("Password :", "", 50, TextField.ANY);
        loginForm = new Form("Login");
        next= new Command("Next", Command.SCREEN, 0);
        loginForm.append(email);
        loginForm.append(password);
        loginForm.addCommand(next);
        loginForm.setCommandListener(this);
        return loginForm;
        
    }
    
    private void logoutSegment() {
        
    }
    
    public void run() {
        try {
                hc = (HttpConnection) Connector.open(url+urlX);
                dis = new DataInputStream(hc.openDataInputStream());
                while ((ch = dis.read()) != -1) {
                    sb.append((char)ch);
                }
                if ("OK".equals(sb.toString().trim())) {
                    display.setCurrent(f2);
                }else{
                    display.setCurrent(f3);
                }
                sb = new StringBuffer();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }
}
