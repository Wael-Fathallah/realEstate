/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Handler;

import Entity.Utilisateur;
import java.util.Vector;
import javax.microedition.lcdui.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Hamza
 */
public class GerantHandler extends DefaultHandler{
       private Vector utilisateurs;
    String idTag = "close";
    String mailTag = "close";
    String passTag = "close";
    String nomTag = "close";
    String prenTag = "close";
    String numMTag = "close";
    String numFTag = "close";
    String statMTag = "close";
    String rolTag = "close";
    String URLpTag = "close";
    
    public GerantHandler() {
        utilisateurs = new Vector();
    }

    public Utilisateur[] getUtilisateur() {
        Utilisateur[] utilisateurss = new Utilisateur[utilisateurs.size()];
        utilisateurs.copyInto(utilisateurss);
        return utilisateurss;
    }
    // VARIABLES TO MAINTAIN THE PARSER'S STATE DURING PROCESSING
    private Utilisateur currentUtilisateur;

    // XML EVENT PROCESSING METHODS (DEFINED BY DefaultHandler)
    // startElement is the opening part of the tag "<tagname...>"
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("utilisateur")) {
            currentUtilisateur = new Utilisateur();
            
            currentUtilisateur.setId(attributes.getValue("u0"));
            currentUtilisateur.setMail(attributes.getValue("u3"));
            currentUtilisateur.setPassword(attributes.getValue("u4"));
            currentUtilisateur.setNom(attributes.getValue("u1"));
            currentUtilisateur.setPrenom(attributes.getValue("u2"));
            currentUtilisateur.setNumMobile(attributes.getValue("u5"));
            currentUtilisateur.setNumFix(attributes.getValue("u6"));
            currentUtilisateur.setStatMAtri(attributes.getValue("u7"));
            currentUtilisateur.setRole(attributes.getValue("u8"));
            currentUtilisateur.setURLp(attributes.getValue("u9"));
            
            
        } else if (qName.equals("u0")) {
            idTag = "open";
        } else if (qName.equals("u3")) {
            mailTag = "open";
        } else if (qName.equals("u4")) {
            passTag = "open";
        } else if (qName.equals("u1")) {
            nomTag = "open";
        } else if (qName.equals("u2")) {
            prenTag = "open";
        } else if (qName.equals("u5")) {
            numMTag = "open";
        } else if (qName.equals("u6")) {
            numFTag = "open";
        } else if (qName.equals("u7")) {
            statMTag = "open";
        } else if (qName.equals("u8")) {
            rolTag = "open";
        } else if (qName.equals("u9")) {
            URLpTag = "open";
        }
       
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("utilisateur")) {
            // we are no longer processing a <reg.../> tag
            utilisateurs.addElement(currentUtilisateur);
            currentUtilisateur = null;
        } else if (qName.equals("u0")) {
            idTag = "close";
        } else if (qName.equals("u3")) {
            mailTag = "close";
        } else if (qName.equals("u4")) {
            passTag = "close";
        } else if (qName.equals("u1")) {
            nomTag = "close";
        } else if (qName.equals("u2")) {
            prenTag = "close";
        } else if (qName.equals("u5")) {
            numMTag = "close";
        } else if (qName.equals("u6")) {
            numFTag = "close";
        } else if (qName.equals("u7")) {
            statMTag = "close";
        } else if (qName.equals("u8")) {
            rolTag = "close";
        } else if (qName.equals("u9")) {
            URLpTag = "close";
        }
    }
    // "characters" are the text between tags

    public void characters(char[] ch, int start, int length) throws SAXException {
        // we're only interested in this inside a <phone.../> tag
        if (currentUtilisateur != null) {
            // don't forget to trim excess spaces from the ends of the string
            if (idTag.equals("open")) {
                currentUtilisateur.setId(new String(ch, start, length).trim());
            } else
                if (mailTag.equals("open")) {
                currentUtilisateur.setMail(new String(ch, start, length).trim());
            } else
                    if (passTag.equals("open")) {
                currentUtilisateur.setPassword(new String(ch, start, length).trim());
            } else
                if (nomTag.equals("open")) {
                currentUtilisateur.setNom(new String(ch, start, length).trim());
            } else
                    if (prenTag.equals("open")) {
                currentUtilisateur.setPrenom(new String(ch, start, length).trim());
            } else
                if (numMTag.equals("open")) {
                currentUtilisateur.setNumMobile(new String(ch, start, length).trim());
            } else
                    if (numFTag.equals("open")) {
                currentUtilisateur.setNumFix(new String(ch, start, length).trim());
            } else
                if (statMTag.equals("open")) {
                currentUtilisateur.setStatMAtri(new String(ch, start, length).trim());
            } else
                    if (rolTag.equals("open")) {
                currentUtilisateur.setRole(new String(ch, start, length).trim());
            } else
                    if (URLpTag.equals("open")) {
                currentUtilisateur.setURLp(new String(ch, start, length).trim());
            }
        }
    }
    
    
}
