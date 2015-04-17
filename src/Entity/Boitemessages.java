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

package Entity;

/**
 *
 * @author FATHALLAH Wael
 */
public class Boitemessages {
    
    private String  id;
    private String  nom_expediteur;
    private String  prenom_expediteur;
    private String  nom_destinataire;
    private String  prenom_destinataire;
    private String  contenu;
    private String vu;

    public Boitemessages(String id, String nom_expediteur, String prenom_expediteur, String nom_destinataire, String prenom_destinataire, String contenu, String vu) {
        this.id = id;
        this.nom_expediteur = nom_expediteur;
        this.prenom_expediteur = prenom_expediteur;
        this.nom_destinataire = nom_destinataire;
        this.prenom_destinataire = prenom_destinataire;
        this.contenu = contenu;
        this.vu = vu;
    }

    public Boitemessages() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom_expediteur() {
        return nom_expediteur;
    }

    public void setNom_expediteur(String nom_expediteur) {
        this.nom_expediteur = nom_expediteur;
    }

    public String getPrenom_expediteur() {
        return prenom_expediteur;
    }

    public void setPrenom_expediteur(String prenom_expediteur) {
        this.prenom_expediteur = prenom_expediteur;
    }

    public String getNom_destinataire() {
        return nom_destinataire;
    }

    public void setNom_destinataire(String nom_destinataire) {
        this.nom_destinataire = nom_destinataire;
    }

    public String getPrenom_destinataire() {
        return prenom_destinataire;
    }

    public void setPrenom_destinataire(String prenom_destinataire) {
        this.prenom_destinataire = prenom_destinataire;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getVu() {
        return vu;
    }

    public void setVu(String vu) {
        this.vu = vu;
    }
    
    

}