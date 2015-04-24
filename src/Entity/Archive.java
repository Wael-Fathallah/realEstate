/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

/**
 *
 * @author ulrich
 */
public class Archive {
    
    private int id;
    private int idGerant;
    private String date;
    private String url;

    public Archive(int id, int idGerant, String date, String url) {
        this.id = id;
        this.idGerant = idGerant;
        this.date = date;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdGerant() {
        return idGerant;
    }

    public void setIdGerant(int idGerant) {
        this.idGerant = idGerant;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString() {
        return "Archive{" + "id=" + id + ", idGerant=" + idGerant + ", date=" + date + ", url=" + url + '}';
    }
    
    Archive getArchive(){
    
        return null;
    }

    public Archive() {
    }
    
}
