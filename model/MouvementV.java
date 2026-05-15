package model;

import java.math.BigDecimal;
import java.sql.Date;

public class MouvementV {

    private int id;

    private int produit_id;

    private String produit_name;

    private String methode_stock;

    private int quantite;

    private BigDecimal pu;

    private BigDecimal prix_total;

    private Date date_mouvement;

    private String type_mouvement;

    private int quantite_total;

    private BigDecimal valeur_stock;

    private BigDecimal cump;

    public MouvementV() {
    }

    public MouvementV(
            int id,
            int produit_id,
            String produit_name,
            String methode_stock,
            int quantite,
            BigDecimal pu,
            BigDecimal prix_total,
            Date date_mouvement,
            String type_mouvement,
            int quantite_total,
            BigDecimal valeur_stock,
            BigDecimal cump
    ) {

        this.id = id;
        this.produit_id = produit_id;
        this.produit_name = produit_name;
        this.methode_stock = methode_stock;
        this.quantite = quantite;
        this.pu = pu;
        this.prix_total = prix_total;
        this.date_mouvement = date_mouvement;
        this.type_mouvement = type_mouvement;
        this.quantite_total = quantite_total;
        this.valeur_stock = valeur_stock;
        this.cump = cump;
    }

    public int getId() {
        return id;
    }

    public int getProduit_id() {
        return produit_id;
    }

    public String getProduit_name() {
        return produit_name;
    }

    public String getMethode_stock() {
        return methode_stock;
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

    public String getType_mouvement() {
        return type_mouvement;
    }

    public int getQuantite_total() {
        return quantite_total;
    }

    public BigDecimal getValeur_stock() {
        return valeur_stock;
    }

    public BigDecimal getCump() {
        return cump;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduit_id(int produit_id) {
        this.produit_id = produit_id;
    }

    public void setProduit_name(String produit_name) {
        this.produit_name = produit_name;
    }

    public void setMethode_stock(String methode_stock) {
        this.methode_stock = methode_stock;
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

    public void setType_mouvement(String type_mouvement) {
        this.type_mouvement = type_mouvement;
    }

    public void setQuantite_total(int quantite_total) {
        this.quantite_total = quantite_total;
    }

    public void setValeur_stock(BigDecimal valeur_stock) {
        this.valeur_stock = valeur_stock;
    }

    public void setCump(BigDecimal cump) {
        this.cump = cump;
    }
}