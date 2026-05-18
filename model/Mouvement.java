package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Mouvement {

    private int id;
    private Produit produit_id;

    private int quantite;

    private BigDecimal pu;

    private BigDecimal prix_total;

    private Date date_mouvement;

    private String type;

    public Mouvement() {
    }

    public Mouvement(
            int id,
            Produit produit_id,
            int quantite,
            BigDecimal pu,
            BigDecimal prix_total,
            Date date_mouvement,
            String type
    ) {
        this.id = id;
        this.produit_id = produit_id;
        this.quantite = quantite;
        this.pu = pu;
        this.prix_total = prix_total;
        this.date_mouvement = date_mouvement;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public Produit getProduit_id() {
        return produit_id;
    }

    public int getQuantite() {
        return quantite;
    }

    public BigDecimal getPu() {
        return pu;
    }

    public BigDecimal getPrix_total() {
        return prix_total;
    }

    public Date getDate_mouvement() {
        return date_mouvement;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduit_id(Produit produit_id) {
        this.produit_id = produit_id;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setPu(BigDecimal pu) {
        this.pu = pu;
    }

    public void setPrix_total(BigDecimal prix_total) {
        this.prix_total = prix_total;
    }

    public void setDate_mouvement(Date date_mouvement) {
        this.date_mouvement = date_mouvement;
    }

    public void setType(String type) {
        this.type = type;
    }
}
