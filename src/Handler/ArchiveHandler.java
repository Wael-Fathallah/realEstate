package Handler;

import Entity.*;
import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ArchiveHandler extends DefaultHandler {
    
    private Vector Archives;
    String idTag = "close";
    String idGTag = "close";
    String dateTag = "close";
    String urlTag = "close";
    
    public ArchiveHandler() {
        Archives = new Vector();
    }
    
    public Archive[] getArchive() {
        Archive[] personness = new Archive[Archives.size()];
        Archives.copyInto(personness);
        return personness;
    }
    // VARIABLES TO MAINTAIN THE PARSER'S STATE DURING PROCESSING
    private Archive currentArchive;
    
    // XML EVENT PROCESSING METHODS (DEFINED BY DefaultHandler)
    // startElement is the opening part of the tag "<tagname...>"
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("entry")) {
            currentArchive = new Archive();
            
        } else if (qName.equals("id")) {
            idTag = "open";
        } else if (qName.equals("id_gerant")) {
            idGTag = "open";
        } else if (qName.equals("date")) {
            dateTag = "open";
        } else if (qName.equals("url")) {
            urlTag = "open";
        }
        
    }
    
    public void endElement(String uri, String localName, String qName) throws SAXException {
        
        if (qName.equals("entry")) {
            // we are no longer processing a <reg.../> tag
            Archives.addElement(currentArchive);
            currentArchive = null;
        } else if (qName.equals("id")) {
            idTag = "close";
        } else if (qName.equals("id_gerant")) {
            idGTag = "close";
        } else if (qName.equals("date")) {
            dateTag = "close";
        } else if (qName.equals("url")) {
            urlTag = "close";
        }
    }
    // "characters" are the text between tags
    
    public void characters(char[] ch, int start, int length) throws SAXException {
        // we're only interested in this inside a <phone.../> tag
        if (currentArchive != null) {
            // don't forget to trim excess spaces from the ends of the string
            if (idTag.equals("open")) {
                String id = new String(ch, start, length).trim();
                currentArchive.setId(Integer.parseInt(id));
            } else if (idGTag.equals("open")) {
                String idGerant = new String(ch, start, length).trim();
                currentArchive.setIdGerant(Integer.parseInt(idGerant));
            } else if (urlTag.equals("open")) {
                String url = new String(ch, start, length).trim();
                currentArchive.setUrl(url);
            } else if (dateTag.equals("open")) {
                String date = new String(ch, start, length).trim();
                currentArchive.setDate(date);
                
            }
        }
        
    }
    
}
