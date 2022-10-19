package third_lab.table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AddEntryDialog extends Table {
    private JPanel contentPane;
    private JTextField typeField;
    private JTextField authorField;
    private JTextField materialsField;
    private JTextField availableField;
    private JTextField priceField;
    private JButton addRowBtn;

    public AddEntryDialog(DefaultTableModel model) {
        setContentPane(contentPane);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        addRowBtn.addActionListener(e -> {
            model.addRow(getFieldsText());
            dispose();
        });
    }

    private String[] getFieldsText() {
        return new String[] {
                typeField.getText(),
                authorField.getText(),
                materialsField.getText(),
                availableField.getText(),
                priceField.getText(),
        };
    }
}
