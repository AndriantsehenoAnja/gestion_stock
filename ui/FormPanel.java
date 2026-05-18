package ui;

import dao.GenericDAO;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FormPanel<T> extends JPanel {

    private T object;

    // stocke les champs dynamiques
    private Map<Field, JTextField> fieldMap =
            new HashMap<>();

    public FormPanel(T object) {

        this.object = object;

        Class<?> clazz = object.getClass();

        Field[] fields = clazz.getDeclaredFields();

        // nombre dynamique de lignes
        setLayout(new GridLayout(fields.length + 1,
                2,
                10,
                10));

        // création automatique du formulaire
        for (Field field : fields) {

            String labelText = field.getName();
            if (field.getType() == java.sql.Date.class) {
                labelText += " (yyyy-MM-dd)";
            }
            if (field.getType().getPackage().getName().equals("model")) {
                JComboBox comboBox = new JComboBox();
                List<T> items = new GenericDAO<T>().findAll(field.getType());
                for (T item : items) {
                    comboBox.addItem(item);
                }
                add(comboBox);
            }
            JLabel label =
                    new JLabel(labelText);

            JTextField textField =
                    new JTextField();

            fieldMap.put(field, textField);

            add(label);
            add(textField);
        }

        JButton btnSave =
                new JButton("Enregistrer");

        add(btnSave);

        btnSave.addActionListener(e -> saveObject());
    }

    private void saveObject() {

        try {

            // remplir automatiquement l'objet
            for (Map.Entry<Field, JTextField> entry
                    : fieldMap.entrySet()) {

                Field field = entry.getKey();

                JTextField textField =
                        entry.getValue();

                field.setAccessible(true);

                String value = textField.getText();
                
                if (value == null || value.trim().isEmpty()) {
                    continue;
                }

                Class<?> type = field.getType();

                // conversion automatique
                if (type == int.class ||
                        type == Integer.class) {

                    field.set(object,
                            Integer.parseInt(value));

                } else if (type == double.class ||
                        type == Double.class) {

                    field.set(object,
                            Double.parseDouble(value));

                } else if (type == float.class ||
                        type == Float.class) {

                    field.set(object,
                            Float.parseFloat(value));

                } else if (type == java.math.BigDecimal.class) {

                    field.set(object, new java.math.BigDecimal(value));

                } else if (type == java.sql.Date.class) {

                    field.set(object, java.sql.Date.valueOf(value));

                } else {

                    field.set(object, value);
                }
            }

            GenericDAO<T> dao =
                    new GenericDAO<>();

            String tableName =
                    object.getClass()
                            .getSimpleName()
                            .toLowerCase();

            dao.save(object, tableName);

            JOptionPane.showMessageDialog(this,
                    "Objet sauvegardé");

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(this,
                    e.getMessage());
        }
    }
}