/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sprint3;

import Entity.Offre;
import Handler.OffreHandler;
import java.io.DataInputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author seif
 */
public class offreMidlet  extends MIDlet implements CommandListener, Runnable {
    
    
    Display disp = Display.getDisplay(this);
    Command cmdParse = new Command("offres", Command.SCREEN, 0);
    Command cmdBack = new Command("Back", Command.BACK, 0);
    Offre [] offres;
    List lst = new List("offres", List.IMPLICIT);
    Form f = new Form("Accueil");
    Form form = new Form("Infos offre");
    Form loadingDialog = new Form("Please Wait");
    StringBuffer sb = new StringBuffer();

    public void startApp() {
        f.append("Click Offres to get your offres_list");
        f.addCommand(cmdParse);
        f.setCommandListener(this);
        lst.setCommandListener(this);
        form.addCommand(cmdBack);
        form.setCommandListener(this);
        disp.setCurrent(f);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
         if (c == cmdParse) {
            disp.setCurrent(loadingDialog);
            Thread th = new Thread(this);
            th.start();
        }

        if (c == List.SELECT_COMMAND) {
            form.append("Informations Offre: \n");
            form.append(showOffre(lst.getSelectedIndex()));
            disp.setCurrent(form);
        }

        if (c == cmdBack) {
            form.deleteAll();
            disp.setCurrent(lst);
        }
    }

    
    public void run() {
        try {
            // this will handle our XML
            OffreHandler offresHandler = new OffreHandler();
            // get a parser object
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // get an InputStream from somewhere (could be HttpConnection, for example)
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/pidev_sprint2/web/app_dev.php/v1/offres.xml");
            DataInputStream dis = new DataInputStream(hc.openDataInputStream());
            parser.parse(dis, offresHandler);
            // display the result
            offres = offresHandler.getOffres();

            if (offres.length > 0) {
                for (int i = 0; i < offres.length; i++) {
                    lst.append(offres[i].getId()+" "
                            +offres[i].getId_gerant(), null);

                }
            }

        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }
        disp.setCurrent(lst);
    }

    private String showOffre(int i) {
        String res = "";
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
}
