package ui;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ecoutes.Ecoute;

public class TablePanel extends JPanel {

    private JTable table;

    // Constructeur par défaut gardant l'ancien comportement (cliquable par défaut)
    public TablePanel(List<?> objects) {
        this(objects, true);
    }

    // Nouveau constructeur avec le paramètre estClicable
    public TablePanel(List<?> objects, boolean estClicable) {

        setLayout(new BorderLayout());

        if (objects == null || objects.isEmpty()) {
            return;
        }

        Object firstObject = objects.get(0);

        Field[] fields =
                firstObject.getClass().getDeclaredFields();

        String[] columns = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            columns[i] = fields[i].getName();
        }

        DefaultTableModel model =
                new DefaultTableModel(columns, 0);

        for (Object obj : objects) {

            Object[] row = new Object[fields.length];

            try {

                for (int i = 0; i < fields.length; i++) {

                    fields[i].setAccessible(true);

                    row[i] = fields[i].get(obj);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            model.addRow(row);
        }

        table = new JTable(model);
        
        // Ajout conditionnel de l'écouteur
        if (estClicable) {
            table.addMouseListener(new Ecoute(table));
        }
        
        add(new JScrollPane(table),
                BorderLayout.CENTER);
    }
}