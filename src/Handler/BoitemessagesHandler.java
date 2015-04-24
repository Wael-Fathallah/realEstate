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

package Handler;

import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import Entity.*;

/**
 *
 * @author FATHALLAH Wael
 */
public class BoitemessagesHandler extends DefaultHandler {
  
    private Vector boitemessagess;
    String idTag = "close";
    String nomExTag = "close";
    String preExTag = "close";
    String nomDeTag = "close";
    String preDeTag = "close";
    String contenuTag = "close";
    String vuTag = "close";
    
    public BoitemessagesHandler() {
        boitemessagess = new Vector();
    }

    public Boitemessages[] getBoitemessages() {
        Boitemessages[] boitemessagesss = new Boitemessages[boitemessagess.size()];
        boitemessagess.copyInto(boitemessagesss);
        return boitemessagesss;
    }
    // VARIABLES TO MAINTAIN THE PARSER'S STATE DURING PROCESSING
    private Boitemessages currentBoitemessage;

    // XML EVENT PROCESSING METHODS (DEFINED BY DefaultHandler)
    // startElement is the opening part of the tag "<tagname...>"
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("Messages")) {
            currentBoitemessage = new Boitemessages();
            
           currentBoitemessage.setId(attributes.getValue("m0"));
            currentBoitemessage.setNom_expediteur(attributes.getValue("m1"));
            currentBoitemessage.setPrenom_expediteur(attributes.getValue("m2"));
            currentBoitemessage.setNom_destinataire(attributes.getValue("m3"));
            currentBoitemessage.setPrenom_destinataire(attributes.getValue("m4"));
            currentBoitemessage.setContenu(attributes.getValue("m5"));
            currentBoitemessage.setVu(attributes.getValue("m6"));
            
            
            
        } else if (qName.equals("m0")) {
            idTag = "open";
        } else if (qName.equals("m1")) {
            nomExTag = "open";
        } else if (qName.equals("m2")) {
            preExTag = "open";
        } else if (qName.equals("m3")) {
            nomDeTag = "open";
        } else if (qName.equals("m4")) {
            preDeTag = "open";
        } else if (qName.equals("m5")) {
            contenuTag = "open";
        } else if (qName.equals("m6")) {
            vuTag = "open";
        }
       
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("Messages")) {
            // we are no longer processing a <reg.../> tag
            boitemessagess.addElement(currentBoitemessage);
            currentBoitemessage = null;
        } else if (qName.equals("m0")) {
            idTag = "close";
        } else if (qName.equals("m1")) {
            nomExTag = "close";
        } else if (qName.equals("m2")) {
            preExTag = "close";
        } else if (qName.equals("m3")) {
            nomDeTag = "close";
        } else if (qName.equals("m4")) {
            preDeTag = "close";
        } else if (qName.equals("m5")) {
            contenuTag = "close";
        } else if (qName.equals("m6")) {
            vuTag = "close";
        }
    }
    // "characters" are the text between tags

    public void characters(char[] ch, int start, int length) throws SAXException {
        // we're only interested in this inside a <phone.../> tag
        if (currentBoitemessage != null) {
            // don't forget to trim excess spaces from the ends of the string
            if (idTag.equals("open")) {
                currentBoitemessage.setId(new String(ch, start, length).trim());
            } else
                if (nomExTag.equals("open")) {
                currentBoitemessage.setNom_expediteur(new String(ch, start, length).trim());
            } else
                    if (preExTag.equals("open")) {
                currentBoitemessage.setPrenom_expediteur(new String(ch, start, length).trim());
            } else
                if (nomDeTag.equals("open")) {
                currentBoitemessage.setNom_destinataire(new String(ch, start, length).trim());
            } else
                    if (preDeTag.equals("open")) {
                currentBoitemessage.setPrenom_destinataire(new String(ch, start, length).trim());
            } else
                if (contenuTag.equals("open")) {
                currentBoitemessage.setContenu(new String(ch, start, length).trim());
            } else
                    if (vuTag.equals("open")) {
                currentBoitemessage.setVu(new String(ch, start, length).trim());
            }
        }
    }
    
}
