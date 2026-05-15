package dao;

import connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
}