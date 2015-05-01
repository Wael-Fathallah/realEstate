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
public class Utilisateur {
    
    private String    id;
    private String  mail;
    private String  password;
    private String  nom;
    private String  prenom;
    private String    numMobile;
    private String     numFix;
    private String  statMAtri;
    private String     role;    
    private String  URLp;

    public Utilisateur(String id, String mail, String password, String nom, String prenom, String numMobile, String numFix, String statMAtri, String role, String URLp) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.numMobile = numMobile;
        this.numFix = numFix;
        this.statMAtri = statMAtri;
        this.role = role;
        this.URLp = URLp;
    }

    public Utilisateur() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumMobile() {
        return numMobile;
    }

    public void setNumMobile(String numMobile) {
        this.numMobile = numMobile;
    }

    public String getNumFix() {
        return numFix;
    }

    public void setNumFix(String numFix) {
        this.numFix = numFix;
    }

    public String getStatMAtri() {
        return statMAtri;
    }

    public void setStatMAtri(String statMAtri) {
        this.statMAtri = statMAtri;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getURLp() {
        return URLp;
    }

    public void setURLp(String URLp) {
        this.URLp = URLp;
    }

    public String toString() {
        return "Utilisateur{" + "id=" + id + ", mail=" + mail + ", password=" + password + ", nom=" + nom + ", prenom=" + prenom + ", numMobile=" + numMobile + ", numFix=" + numFix + ", statMAtri=" + statMAtri+"}";
    }

    
}
