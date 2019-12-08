/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 *
 * @author pedago
 */
public class CategorieEntity {

    private int Code;
    private String Libelle;
    private String Description;
    
    
    public CategorieEntity(int Code, String Libelle, String Description) {
        this.Code = Code;
        this.Libelle = Libelle;
        this.Description = Description;  
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String Libelle) {
        this.Libelle = Libelle;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    @Override
    public String toString() {
        return "CategorieEntity{" + "Code=" + Code + ", Libelle=" + Libelle + ", Description=" + Description + '}';
    }
    
    
    
    
    
}
