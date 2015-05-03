package Handler;

import Entity.Administrateur;
import java.util.Vector;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;


/**
 *
 * @author Elyes
 */
public class AdministrateurHandler extends DefaultHandler {

    private Vector ls;
    private Administrateur admin;

    public AdministrateurHandler(){
        
    }
    public void startDocument() throws SAXException {
        ls = new Vector();
    }

    public void endDocument() throws SAXException {
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs) throws SAXException {
        if (qName.equals("admin")) {
            admin = new Administrateur();
            admin.setId(Integer.parseInt(attrs.getValue("id")));
            admin.setNom(attrs.getValue("nom"));
            admin.setPrenom(attrs.getValue("prenom"));
            admin.setMail(attrs.getValue("mail"));
            admin.setPassword(attrs.getValue("password"));
            admin.setPrivilege(Integer.parseInt(attrs.getValue("priv")));
        }
    }

    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if (qName.equals("admin")) {
        ls.addElement(admin);
        }
    }
    
    public Administrateur[] getAdminsList() throws ParserConfigurationException, SAXException{
        Administrateur[] array;
        array = new Administrateur[ls.size()];
        for(int i=0;i<ls.size();i++){
            array[i] = (Administrateur) ls.elementAt(i);
        }
        return array;
    }
}
