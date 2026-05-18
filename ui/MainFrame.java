package ui;

import dao.MouvementVDAO;
import model.Mouvement;
import model.MouvementV;
import model.Produit;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class MainFrame extends JFrame {

    private JPanel mainPanel;

    public MainFrame() {

        setTitle("Gestion de Stock");

        setSize(1000, 600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // =========================
        // MENU
        // =========================

        JPanel menuPanel = new JPanel();

        JButton btnProduit =
                new JButton("Produit");

        JButton btnMouvement =
                new JButton("Mouvement");

        JButton btnStock =
                new JButton("Etat Stock");

        menuPanel.add(btnProduit);
        menuPanel.add(btnMouvement);
        menuPanel.add(btnStock);

        add(menuPanel, BorderLayout.NORTH);

        // =========================
        // MAIN PANEL
        // =========================

        mainPanel = new JPanel(new BorderLayout());

        add(mainPanel, BorderLayout.CENTER);

        // =========================
        // EVENTS
        // =========================

        btnProduit.addActionListener(e -> {

            showProduitForm();
        });

        btnMouvement.addActionListener(e -> {

            showMouvementForm();
        });

        btnStock.addActionListener(e -> {

            showStockPage();
        });

        // page par défaut
        showProduitForm();
    }

    // ==================================================
    // FORM PRODUIT
    // ==================================================

    private void showProduitForm() {

        mainPanel.removeAll();

        Produit produit = new Produit();

        FormPanel<Produit> formPanel =
                new FormPanel<>(produit);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        refresh();
    }

    // ==================================================
    // FORM MOUVEMENT
    // ==================================================

    private void showMouvementForm() {

        mainPanel.removeAll();

        Mouvement mouvement = new Mouvement();

        FormPanel<Mouvement> formPanel =
                new FormPanel<>(mouvement);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        refresh();
    }

    // ==================================================
    // PAGE STOCK / LISTE
    // ==================================================

    private void showStockPage() {

        mainPanel.removeAll();

        JPanel container =
                new JPanel(new BorderLayout());

        // =====================================
        // TOP PANEL
        // =====================================

        JPanel topPanel =
                new JPanel();

        JLabel lblDate =
                new JLabel("Date : ");

        JTextField txtDate =
                new JTextField(15);

        JButton btnSearch =
                new JButton("Valider");

        topPanel.add(lblDate);
        topPanel.add(txtDate);
        topPanel.add(btnSearch);

        container.add(topPanel,
                BorderLayout.NORTH);

        // =====================================
        // TABLE PANEL
        // =====================================

        JPanel tableContainer =
                new JPanel(new BorderLayout());

        container.add(tableContainer,
                BorderLayout.CENTER);

        // =====================================
        // ACTION
        // =====================================

        btnSearch.addActionListener(e -> {

            try {

                Date date =
                        Date.valueOf(
                                txtDate.getText()
                        );

                MouvementVDAO dao =
                        new MouvementVDAO();

                List<MouvementV> list =
                        dao.findByDate(date);

                tableContainer.removeAll();

                TablePanel tablePanel =
                        new TablePanel(list,true);

                tableContainer.add(
                        tablePanel,
                        BorderLayout.CENTER
                );

                tableContainer.revalidate();

                tableContainer.repaint();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Date invalide : yyyy-mm-dd"
                );

                ex.printStackTrace();
            }
        });

        mainPanel.add(container,
                BorderLayout.CENTER);

        refresh();
    }

    // ==================================================
    // REFRESH
    // ==================================================

    private void refresh() {

        mainPanel.revalidate();

        mainPanel.repaint();
    }
}