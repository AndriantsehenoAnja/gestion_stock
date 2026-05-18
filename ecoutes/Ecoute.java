package ecoutes;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import javax.swing.JTable;
import model.MouvementV;
import ui.Fiche;
import ui.TablePanel;
import dao.MouvementVDAO;

public class Ecoute implements MouseListener {

    private JTable table;

    public Ecoute(JTable table) {
        this.table = table;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (table != null) {

            int row = table.rowAtPoint(e.getPoint());

            MouvementV mouv = new MouvementV();
            if (row >= 0) {


                // Remplissage dans l'ordre des attributs
                mouv.setId((int) table.getValueAt(row, 0));

                mouv.setProduit_id((int) table.getValueAt(row, 1));

                mouv.setProduit_name((String) table.getValueAt(row, 2));

                mouv.setMethode_stock((String) table.getValueAt(row, 3));

                mouv.setQuantite((int) table.getValueAt(row, 4));

                mouv.setPu((BigDecimal) table.getValueAt(row, 5));

                mouv.setPrix_total((BigDecimal) table.getValueAt(row, 6));

                mouv.setDate_mouvement((java.sql.Date) table.getValueAt(row, 7));

                mouv.setType_mouvement((String) table.getValueAt(row, 8));

                mouv.setQuantite_total((int) table.getValueAt(row, 9));

                mouv.setValeur_stock((BigDecimal) table.getValueAt(row, 10));

                mouv.setCump((BigDecimal) table.getValueAt(row, 11));

            }
            Fiche fiche = new Fiche();
            fiche.add(new TablePanel(MouvementVDAO.getDetails(mouv)));
        } else {

            System.out.println("Mouse clicked at: " + e.getPoint());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed at: " + e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse released at: " + e.getPoint());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse entered component.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse exited component.");
    }
}
