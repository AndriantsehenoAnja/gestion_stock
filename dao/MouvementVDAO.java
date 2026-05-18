package dao;

import connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Mouvement;
import model.MouvementV;

public class MouvementVDAO {

    public List<MouvementV> findByDate(Date date) {

        List<MouvementV> list = new ArrayList<>();

        // Utilisation de DISTINCT ON pour récupérer le dernier mouvement de chaque produit
        String sql =
                "SELECT DISTINCT ON (produit_id) * FROM mouvement_v " +
                "WHERE date_mouvement <= ? " +
                "ORDER BY produit_id, date_mouvement DESC, id DESC";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement ps =
                        connection.prepareStatement(sql)
        ) {

            ps.setDate(1, date);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                MouvementV mv = new MouvementV();

                mv.setId(
                        rs.getInt("id")
                );

                mv.setProduit_id(
                        rs.getInt("produit_id")
                );

                mv.setProduit_name(
                        rs.getString("produit_name")
                );

                mv.setMethode_stock(
                        rs.getString("methode_stock")
                );

                mv.setQuantite(
                        rs.getInt("quantite")
                );

                mv.setPu(
                        rs.getBigDecimal("pu")
                );

                mv.setPrix_total(
                        rs.getBigDecimal("prix_total")
                );

                mv.setDate_mouvement(
                        rs.getDate("date_mouvement")
                );

                mv.setType_mouvement(
                        rs.getString("type_mouvement")
                );

                mv.setQuantite_total(
                        rs.getInt("quantite_total")
                );

                mv.setValeur_stock(
                        rs.getBigDecimal("valeur_stock")
                );

                mv.setCump(
                        rs.getBigDecimal("cump")
                );

                list.add(mv);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }
    public static List<MouvementV> getDetails(MouvementV mouvement) {
        List<MouvementV> list = new ArrayList<>();
        List<MouvementV> resultat = new ArrayList<>();

        String sql = "SELECT * FROM mouvement_v " +
                "WHERE produit_id = ? AND date_mouvement <= ? " +
                "ORDER BY date_mouvement DESC";
        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement ps =
                        connection.prepareStatement(sql)
        ) {
            ps.setInt(1, mouvement.getProduit_id());
            ps.setDate(2, mouvement.getDate_mouvement());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MouvementV mv = new MouvementV();
                mv.setId(rs.getInt("id"));
                mv.setProduit_id(rs.getInt("produit_id"));
                mv.setProduit_name(rs.getString("produit_name"));
                mv.setMethode_stock(rs.getString("methode_stock"));
                mv.setQuantite(rs.getInt("quantite"));
                mv.setPu(rs.getBigDecimal("pu"));
                mv.setPrix_total(rs.getBigDecimal("prix_total"));
                mv.setDate_mouvement(rs.getDate("date_mouvement"));
                mv.setType_mouvement(rs.getString("type_mouvement"));
                list.add(mv);
            }
            if(mouvement.getMethode_stock().equalsIgnoreCase("FIFO")) {
                int quantite_restante = mouvement.getQuantite_total();
                for (MouvementV mv : list) {
                    if (quantite_restante <= 0) {
                        break;
                    }
                    if (mv.getType_mouvement().equalsIgnoreCase("ENTREE")) {
                        if (mv.getQuantite() <= quantite_restante) {
                            resultat.add(mv);
                            quantite_restante -= mv.getQuantite();
                        } else {
                            MouvementV partiel = new MouvementV();
                            partiel.setProduit_id(mv.getProduit_id());
                            partiel.setProduit_name(mv.getProduit_name());
                            partiel.setMethode_stock(mv.getMethode_stock());
                            partiel.setQuantite(quantite_restante);
                            partiel.setPu(mv.getPu());
                            partiel.setPrix_total(mv.getPu().multiply(new java.math.BigDecimal(quantite_restante)));
                            partiel.setDate_mouvement(mv.getDate_mouvement());
                            partiel.setType_mouvement(mv.getType_mouvement());
                            resultat.add(partiel);
                            quantite_restante = 0;
                        }
                    }
                }
            } else if (mouvement.getMethode_stock().equalsIgnoreCase("LIFO")) {
                int quantite_restante = mouvement.getQuantite_total();
                for (int i = list.size() - 1; i >= 0; i--) {
                    MouvementV mv = list.get(i);
                    if (quantite_restante <= 0) {
                        break;
                    }
                    if (mv.getType_mouvement().equalsIgnoreCase("ENTREE")) {
                        if (mv.getQuantite() <= quantite_restante) {
                            resultat.add(mv);
                            quantite_restante -= mv.getQuantite();
                        } else {
                            MouvementV partiel = new MouvementV();
                            partiel.setProduit_id(mv.getProduit_id());
                            partiel.setProduit_name(mv.getProduit_name());
                            partiel.setMethode_stock(mv.getMethode_stock());
                            partiel.setQuantite(quantite_restante);
                            partiel.setPu(mv.getPu());
                            partiel.setPrix_total(mv.getPu().multiply(new java.math.BigDecimal(quantite_restante)));
                            partiel.setDate_mouvement(mv.getDate_mouvement());
                            partiel.setType_mouvement(mv.getType_mouvement());
                            resultat.add(partiel);
                            quantite_restante = 0;
                        }
                    }
                }
                
            }else{
                resultat.add(mouvement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultat;
    }
}