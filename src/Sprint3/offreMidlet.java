/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Sprint3;

import Entity.*;
import Handler.*;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.ContentConnection;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.List;

import javax.microedition.midlet.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author seif
 */
public class offreMidlet extends MIDlet implements CommandListener, Runnable {
    
    Display disp = Display.getDisplay(this);
    
    
    Command cmdParse = new Command("offres", Command.SCREEN, 0);
    Command cmdBack = new Command("Back", Command.BACK, 0);
    Command cmdDelete=new Command("Delete",Command.OK,0);
    
    Offre[] offres;
    int selectedIndex;
    
    List lst = new List("offres", List.IMPLICIT);
    
    Form f = new Form("Accueil");
    Form form = new Form("Infos offre");
    Form loadingDialog = new Form("Please Wait");
    
    StringBuffer sb = new StringBuffer();
    
    private Canvas[] canvases;
    
    public void startApp() {
        
        f.append("Click Offres to get your offres_list");
        f.addCommand(cmdParse);
        f.setCommandListener(this);
        lst.setCommandListener(this);
        
        form.addCommand(cmdBack);
        form.addCommand(cmdDelete);
        
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
            this.selectedIndex=lst.getSelectedIndex();
            form.append("Informations Offre: \n");
            form.append(showOffre(lst.getSelectedIndex()));
            try {
                Image im=this.getImage(lst.getSelectedIndex());
                ImageItem ii = new ImageItem(null, im, ImageItem.LAYOUT_DEFAULT, null);
                form.append(ii);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            disp.setCurrent(form);
        }
        
        if (c == cmdBack) {
            form.deleteAll();
            disp.setCurrent(lst);
        }
        if(c==cmdDelete){
            try {
                this.deleteOffre(selectedIndex);
                lst.delete(selectedIndex);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void run() {
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
            
            if (offres.length > 0) {
                for (int i = 0; i < offres.length; i++) {
                    
                    lst.append(offres[i].getDescription(), this.getImage(i));
                    
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
//        iStrm.read(imageData);
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
    
    public void deleteOffre(int i) throws IOException{
        HttpConnection hc = (HttpConnection) Connector.open("http://localhost/pidev_sprint2/web/app_dev.php/v1/os/"+offres[i].getId()+"/offres.json");;
        hc.setRequestMethod("POST");
        System.out.println(hc.getResponseMessage());
        
        System.out.println("http://localhost/pidev_sprint2/web/app_dev.php/v1/os/"+offres[i].getId()+"/offres.json");
        
    }
}
