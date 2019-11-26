/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;


/**
 *
 * @author pedago
 */
public class CommandeEntity {
    private int cmdId;
    private String client;
    private Date dateSaisie;
    private Date dateEnvoi;
    private float port;
    private String dest;
    private String add;
    private String ville;
    private String region;
    private String cp;
    private String pays;
    private float remise ;

    public CommandeEntity(int cmdId, String client, Date dateSaisie, Date dateEnvoi, float port, String dest, String add, String ville, String region, String cp, String pays, float remise) {
        this.cmdId = cmdId;
        this.client = client;
        this.dateSaisie = dateSaisie;
        this.dateEnvoi = dateEnvoi;
        this.port = port;
        this.dest = dest;
        this.add = add;
        this.ville = ville;
        this.region = region;
        this.cp = cp;
        this.pays = pays;
        this.remise = remise;
    }

    public int getCmdId() {
        return cmdId;
    }

    public String getClient() {
        return client;
    }

    public Date getDateSaisie() {
        return dateSaisie;
    }

    public Date getDateEnvoi() {
        return dateEnvoi;
    }

    public float getPort() {
        return port;
    }

    public String getDest() {
        return dest;
    }

    public String getAdd() {
        return add;
    }

    public String getVille() {
        return ville;
    }

    public String getRegion() {
        return region;
    }

    public String getCp() {
        return cp;
    }

    public String getPays() {
        return pays;
    }

    public float getRemise() {
        return remise;
    }

    public void setCmdId(int cmdId) {
        this.cmdId = cmdId;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setDateSaisie(Date dateSaisie) {
        this.dateSaisie = dateSaisie;
    }

    public void setDateEnvoi(Date dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public void setPort(float port) {
        this.port = port;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public void setRemise(float remise) {
        this.remise = remise;
    }
    
    
            
            
}
