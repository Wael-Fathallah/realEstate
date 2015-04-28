/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Handler;

import Entity.Adresse;
import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author FATHALLAH Wael
 */
public class AdresseHandler extends DefaultHandler {
    
    private Vector adresses;
    String idTag = "close";
    String gouvernoratTag = "close";
    String codepostalTag = "close";
    String villeTag = "close";
    
    StringBuffer strB = new StringBuffer(1024);
    
    public AdresseHandler() {
        adresses = new Vector();
    }
    
    public Adresse[] getAdresses() {
        Adresse[] adressess = new Adresse[adresses.size()];
        adresses.copyInto(adressess);
        return adressess;
    }
    // VARIABLES TO MAINTAIN THE PARSER'S STATE DURING PROCESSING
    private Adresse currentAdresse;
    
    // XML EVENT PROCESSING METHODS (DEFINED BY DefaultHandler)
    // startElement is the opening part of the tag "<tagname...>"
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        strB.setLength(0);
        System.out.println("Start Element :" + qName);
        if (qName.equals("entry")) {
            currentAdresse = new Adresse();
            
        } else if (qName.equals("id")) {
            idTag = "open";
        } else if (qName.equals("gouvernorat")) {
            gouvernoratTag = "open";
        } else if (qName.equals("ville")) {
            villeTag = "open";
        } else if (qName.equals("codepostal")) {
            codepostalTag = "open";
        }
    }
    
    public void endElement(String uri, String localName, String qName) throws SAXException {
        
        if (qName.equals("entry")) {
            // we are no longer processing a <reg.../> tag
            adresses.addElement(currentAdresse);
            currentAdresse = null;
        } else if (qName.equals("id")) {
            String id = strB.toString();
            currentAdresse.setId(Integer.parseInt(id));
            
        } else if (qName.equals("gouvernorat")) {
            
            String gouvernorat = strB.toString();
            currentAdresse.setGouvernorat(gouvernorat);
            
        } else if (qName.equals("ville")) {
            
            String ville = strB.toString();
            currentAdresse.setVille(ville);
            
        } else if (qName.equals("codepostal")) {
            
            String code = strB.toString();
            currentAdresse.setCode_pos(Integer.parseInt(code));
            
        }
    }
    
    
    public void characters(char[] ch, int start, int length) throws SAXException {
        // we're only interested in this inside a <phone.../> tag
        
        if (currentAdresse != null) {
            // don't forget to trim excess spaces from the ends of the string
            if (idTag.equals("open")) {
                
                strB.append(ch, start, length);
                
            } else if (gouvernoratTag.equals("open")) {
                
                strB.append(ch, start, length);
                
            } else if (villeTag.equals("open")) {
                
                strB.append(ch, start, length);
                
            } else if (codepostalTag.equals("open")) {
                
                strB.append(ch, start, length);
                
            }
            
        }
    }
    
}
