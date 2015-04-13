/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class UtilisateurHandler extends DefaultHandler {
  
    private Vector utilisateurs;
    String idTag = "close";
    String nomTag = "close";
    String prenTag = "close";

    public UtilisateurHandler() {
        utilisateurs = new Vector();
    }

    public Utilisateur[] getPersonne() {
        Utilisateur[] utilisateurss = new Utilisateur[utilisateurs.size()];
        utilisateurs.copyInto(utilisateurss);
        return utilisateurss;
    }
    // VARIABLES TO MAINTAIN THE PARSER'S STATE DURING PROCESSING
    private Utilisateur currentUtilisateur;

    // XML EVENT PROCESSING METHODS (DEFINED BY DefaultHandler)
    // startElement is the opening part of the tag "<tagname...>"
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("person")) {
            currentUtilisateur = new Utilisateur();
            //2ème methode pour parser les attributs
            currentUtilisateur.setId(attributes.getValue("id"));
            currentUtilisateur.setNom(attributes.getValue("nom"));
            currentUtilisateur.setPrenom(attributes.getValue("prenom"));
            /****/
            
        } else if (qName.equals("id")) {
            idTag = "open";
        } else if (qName.equals("nom")) {
            nomTag = "open";
        } else if (qName.equals("prenom")) {
            prenTag = "open";
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("person")) {
            // we are no longer processing a <reg.../> tag
            utilisateurs.addElement(currentUtilisateur);
            currentUtilisateur = null;
        } else if (qName.equals("id")) {
            idTag = "close";
        } else if (qName.equals("nom")) {
            nomTag = "close";
        } else if (qName.equals("prenom")) {
            prenTag = "close";
        }
    }
    // "characters" are the text between tags

    public void characters(char[] ch, int start, int length) throws SAXException {
        // we're only interested in this inside a <phone.../> tag
        if (currentUtilisateur != null) {
            // don't forget to trim excess spaces from the ends of the string
            if (idTag.equals("open")) {
                String id = new String(ch, start, length).trim();
                currentUtilisateur.setId(id);
            } else
                if (nomTag.equals("open")) {
                String nom = new String(ch, start, length).trim();
                currentUtilisateur.setNom(nom);
            } else
                    if (prenTag.equals("open")) {
                String pren = new String(ch, start, length).trim();
                currentUtilisateur.setPrenom(pren);
            }
        }
    }
    
}
