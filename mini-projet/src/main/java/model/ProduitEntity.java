/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Correspond à un enregistrement de la table Produit
 * @author pedago
 */
public class ProduitEntity {
    private int produitId;
    private String nom;
    private int fournisseur;
    private int categorie;
    private String quantiteUnite;
    private float prix ;
    private int qttStock;
    private int reapro;
    private int dispo;

    public ProduitEntity(int produitId, String nom, int fournisseur, int categorie, String quantiteUnite,
                         float prix, int qttStock, int reapro, int dispo) {
        this.produitId = produitId;
        this.nom = nom;
        this.fournisseur = fournisseur;
        this.categorie = categorie;
        this.quantiteUnite = quantiteUnite;
        this.prix = prix;
        this.qttStock = qttStock;
        this.reapro = reapro;
        this.dispo = dispo;
    }
    
    /*set*/
    
    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setFournisseur(int fournisseur) {
        this.fournisseur = fournisseur;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    public void setQuantiteUnite(String quantiteUnite) {
        this.quantiteUnite = quantiteUnite;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setQttStock(int qttStock) {
        this.qttStock = qttStock;
    }

    public void setReapro(int reapro) {
        this.reapro = reapro;
    }

    public void setDispo(int dispo) {
        this.dispo = dispo;
    }
    
    /*Get*/

    public int getProduitId() {
        return produitId;
    }

    public String getNom() {
        return nom;
    }

    public int getFournisseur() {
        return fournisseur;
    }

    public int getCategorie() {
        return categorie;
    }

    public String getQuantiteUnite() {
        return quantiteUnite;
    }

    public float getPrix() {
        return prix;
    }

    public int getQttStock() {
        return qttStock;
    }

    public int getReapro() {
        return reapro;
    }

    public int getDispo() {
        return dispo;
    }
    
}
