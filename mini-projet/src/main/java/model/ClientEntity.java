/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author pedago
 */
public class ClientEntity {

    private String Code;
    private String Societe;
    private String Contact;
    private String Fonction;
    private String Adresse;
    private String Ville;
    private String Region;
    private String Code_postal;
    private String Pays;
    private String Telephone;
    private String Fax;
    
    public ClientEntity(String Code, String Societe, String Contact, String Fonction, String Adresse, String Ville,
            String Region, String Code_postal, String Pays, String Telephone, String Fax) {
        this.Code = Code;
        this.Societe = Societe;
        this.Contact = Contact;
        this.Fonction = Fonction;
        this.Adresse = Adresse;
        this.Ville = Ville;
        this.Region = Region;
        this.Code_postal = Code_postal;
        this.Pays = Pays;
        this.Telephone = Telephone;
        this.Fax = Fax;
        
    }

    public String getCode() {
        return Code;
    }

    public String getSociete() {
        return Societe;
    }

    public String getContact() {
        return Contact;
    }

    public String getFonction() {
        return Fonction;
    }

    public String getAdresse() {
        return Adresse;
    }

    public String getVille() {
        return Ville;
    }

    public String getRegion() {
        return Region;
    }

    public String getCode_postal() {
        return Code_postal;
    }

    public String getPays() {
        return Pays;
    }

    public String getTelephone() {
        return Telephone;
    }

    public String getFax() {
        return Fax;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public void setSociete(String Societe) {
        this.Societe = Societe;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public void setFonction(String Fonction) {
        this.Fonction = Fonction;
    }

    public void setAdresse(String Adresse) {
        this.Adresse = Adresse;
    }

    public void setVille(String Ville) {
        this.Ville = Ville;
    }

    public void setRegion(String Region) {
        this.Region = Region;
    }

    public void setCode_postal(String Code_postal) {
        this.Code_postal = Code_postal;
    }

    public void setPays(String Pays) {
        this.Pays = Pays;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    public void setFax(String Fax) {
        this.Fax = Fax;
    }
    
    
}
