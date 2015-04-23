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
  /*
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

    public Utilisateur[] getPersonne() {
        Utilisateur[] boitemessagesss = new Utilisateur[boitemessagess.size()];
        boitemessagess.copyInto(boitemessagesss);
        return boitemessagesss;
    }
    // VARIABLES TO MAINTAIN THE PARSER'S STATE DURING PROCESSING
    private Boitemessages currentBoitemessage;

    // XML EVENT PROCESSING METHODS (DEFINED BY DefaultHandler)
    // startElement is the opening part of the tag "<tagname...>"
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("utulisateur")) {
            currentBoitemessage = new Boitemessages();
            
            currentBoitemessage.setId(Integer.parseInt(attributes.getValue("id")));
            currentBoitemessage.setNom_expediteur(attributes.getValue("mail"));
            currentBoitemessage.setPrenom_expediteur(attributes.getValue("password"));
            currentBoitemessage.setNom_destinataire(attributes.getValue("nom"));
            currentBoitemessage.setPrenom_destinataire(attributes.getValue("prenom"));
            currentBoitemessage.setContenu(attributes.getValue("numMobile"));
            currentBoitemessage.setVu(Integer.parseInt(attributes.getValue("numFix")));
            
            
            
        } else if (qName.equals("id")) {
            idTag = "open";
        } else if (qName.equals("mail")) {
            nomExTag = "open";
        } else if (qName.equals("password")) {
            preExTag = "open";
        } else if (qName.equals("nom")) {
            nomDeTag = "open";
        } else if (qName.equals("prenom")) {
            preDeTag = "open";
        } else if (qName.equals("numMobile")) {
            contenuTag = "open";
        } else if (qName.equals("numFix")) {
            vuTag = "open";
        }
       
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("person")) {
            // we are no longer processing a <reg.../> tag
            boitemessagess.addElement(currentBoitemessage);
            currentBoitemessage = null;
        } else if (qName.equals("id")) {
            idTag = "close";
        } else if (qName.equals("mail")) {
            mailTag = "close";
        } else if (qName.equals("password")) {
            passTag = "close";
        } else if (qName.equals("nom")) {
            nomTag = "close";
        } else if (qName.equals("prenom")) {
            prenTag = "close";
        } else if (qName.equals("numMobile")) {
            numMTag = "close";
        } else if (qName.equals("numFix")) {
            numFTag = "close";
        } else if (qName.equals("status_matrimonial")) {
            statMTag = "close";
        } else if (qName.equals("role")) {
            rolTag = "close";
        } else if (qName.equals("URLp")) {
            URLpTag = "close";
        }
    }
    // "characters" are the text between tags

    public void characters(char[] ch, int start, int length) throws SAXException {
        // we're only interested in this inside a <phone.../> tag
        if (currentBoitemessage != null) {
            // don't forget to trim excess spaces from the ends of the string
            if (idTag.equals("open")) {
                currentBoitemessage.setId(Integer.parseInt(new String(ch, start, length).trim()));
            } else
                if (mailTag.equals("open")) {
                currentBoitemessage.setMail(new String(ch, start, length).trim());
            } else
                    if (passTag.equals("open")) {
                currentBoitemessage.setPassword(new String(ch, start, length).trim());
            } else
                if (nomTag.equals("open")) {
                currentBoitemessage.setNom(new String(ch, start, length).trim());
            } else
                    if (prenTag.equals("open")) {
                currentBoitemessage.setPrenom(new String(ch, start, length).trim());
            } else
                if (numMTag.equals("open")) {
                currentBoitemessage.setNumMobile(Integer.parseInt(new String(ch, start, length).trim()));
            } else
                    if (numFTag.equals("open")) {
                currentBoitemessage.setNumFix(Integer.parseInt(new String(ch, start, length).trim()));
            } else
                if (statMTag.equals("open")) {
                currentBoitemessage.setStatMAtri(new String(ch, start, length).trim());
            } else
                    if (rolTag.equals("open")) {
                currentBoitemessage.setRole(Integer.parseInt(new String(ch, start, length).trim()));
            } else
                    if (URLpTag.equals("open")) {
                currentBoitemessage.setURLp(new String(ch, start, length).trim());
            }
        }
    }
    */
}
