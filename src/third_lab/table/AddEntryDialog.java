package third_lab.table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AddEntryDialog extends Table {
    private JPanel contentPane;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
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
                textField1.getText(),
                textField2.getText(),
                textField3.getText(),
                textField4.getText(),
                textField5.getText(),
        };
    }
}
