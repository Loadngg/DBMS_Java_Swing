package third_lab.table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

public class Table extends JDialog {
    private JPanel contentPane;
    private JTable mainTable;
    private JButton addBtn;
    private JButton deleteBtn;
    private JButton searchBtn;
    private JTextField firstFilter;
    private JTextField secondFilter;
    private JTextField thirdFilter;
    private JPanel jtfFiltersBlock;
    private JScrollPane scrollPane;
    private JButton startSearchBtn;
    private boolean filtersVisible = false;
    private final JPopupMenu popupMenu = new JPopupMenu();
    private final JMenuItem addItem = new JMenuItem("Добавить");
    private final JMenuItem deleteItem = new JMenuItem("Удалить");
    private final JMenuItem searchItem = new JMenuItem("Поиск");
    private final JButton[] buttons = new JButton[] { addBtn, deleteBtn, searchBtn };
    private final JTextField[] filters = new JTextField[] {firstFilter, secondFilter, thirdFilter};
    private final TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(mainTable.getModel());
    public final DefaultTableModel model = (DefaultTableModel) mainTable.getModel();

    public Table() {
        setPreference();
    }

    private void setFilterListeners() {
        try {
            StringBuilder regexString = new StringBuilder("(?i)");
            ArrayList<String> filtersTextArray = new ArrayList<>();
            for (JTextField filter : filters) {
                final String filterText = filter.getText();
                if (filterText.equals("")) continue;
                filtersTextArray.add(filterText);
            }

            for (String filterText : filtersTextArray) regexString.append(filterText).append("|");
            regexString.deleteCharAt(regexString.length() - 1);
            rowSorter.setRowFilter(RowFilter.regexFilter(String.valueOf(regexString)));
        } catch (PatternSyntaxException exception) {
            String message = "Возникла ошибка. Проверьте введённые данные!";
            JOptionPane.showMessageDialog(new JFrame(), message, "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setBtnListeners() {
        addBtn.addActionListener(e -> setAddEvent());
        deleteBtn.addActionListener(e -> setDeleteEvent());
        searchBtn.addActionListener(e -> setSearchEvent());
        startSearchBtn.addActionListener(e -> setFilterListeners());
    }

    private void setAddEvent() {
        AddEntryDialog addEntryDialog = new AddEntryDialog(model);
        addEntryDialog.pack();
        addEntryDialog.setVisible(true);
    }

    private void setDeleteEvent() {
        int[] rows = mainTable.getSelectedRows();
        for (int i = rows.length - 1; i >= 0; i--) model.removeRow(rows[i]);
    }

    private void setSearchEvent() {
        filtersVisible = !filtersVisible;
        jtfFiltersBlock.setVisible(filtersVisible);
        if (filtersVisible) return;
        for (JTextField filter : filters) filter.setText("");
        rowSorter.setRowFilter(null);
    }

    private void settingsPopupMenu() {
        settingPopupItem();
        addingPopupItems();
    }

    private void addingPopupItems() {
        popupMenu.add(addItem);
        popupMenu.add(deleteItem);
        popupMenu.add(searchItem);
    }

    private void settingPopupItem() {
        addItem.addActionListener(e -> setAddEvent());
        deleteItem.addActionListener(e -> setDeleteEvent());
        searchItem.addActionListener(e -> setSearchEvent());
    }

    private void settingsTable() {
        Object[] columnNames = new Object[]
                {"Название фильма", "Жанр", "Режиссёр", "Год выпуска", "Оценка"};

        mainTable.setRowSorter(rowSorter);
        mainTable.setComponentPopupMenu(popupMenu);
        mainTable.setPreferredScrollableViewportSize(new Dimension(800, 600));
        model.setColumnIdentifiers(columnNames);
    }

    private void settingButtonsBorder() {
        for (JButton button : buttons) {
            button.setBorder(new RoundedBorder(15));
            button.setFocusPainted(false);
        }
    }

    private void setPreference() {
        setContentPane(contentPane);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        settingsPopupMenu();
        settingsTable();
        settingButtonsBorder();
        setBtnListeners();

        jtfFiltersBlock.setVisible(filtersVisible);
        scrollPane.setComponentPopupMenu(popupMenu);
    }

    public static void main(String[] args) {
        Table dialog = new Table();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
