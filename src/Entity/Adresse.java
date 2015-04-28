/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

/**
 *
 * @author ShujiX
 */
public class Adresse {
    private int id;
    private String ville;
    private String gouvernorat;
    private int code_pos;

    public Adresse() {
    }

    public Adresse(String ville, String gouvernorat, int code_pos) {
        this.ville = ville;
        this.gouvernorat = gouvernorat;
        this.code_pos = code_pos;
    }

    public Adresse(int id, String ville, String gouvernorat, int code_pos) {
        this.id = id;
        this.ville = ville;
        this.gouvernorat = gouvernorat;
        this.code_pos = code_pos;
    }

    public int getCode_pos() {
        return code_pos;
    }

    public void setCode_pos(int code_pos) {
        this.code_pos = code_pos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getGouvernorat() {
        return gouvernorat;
    }

    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

  
    public String toString() {
        return "Adresse{" + "id=" + id + ", ville=" + ville + ", gouvernorat=" + gouvernorat + ", code_pos=" + code_pos + '}';
    }
    
}
