package com.infoedge.plugin.gcmtester.ui;

import com.infoedge.plugin.gcmtester.model.GcmResponseStatus;

import javax.swing.*;
import javax.swing.table.*;
import java.util.Vector;

/**
 * Created by gagandeep on 30/3/16.
 */
public class GcmResponseResultPanel {
    private JTable gcmResultsTable;
    private JPanel mainPanel;
    private JLabel resultTotalLabel;
    private JLabel resultSuccessLabel;
    private JLabel resultFailureLabel;

    private static final String[] COLUMNS = {"Gcm Reg Id","Result"};

    private GcmResultsTableDialog gcmResultsTableDialog;

    public GcmResponseResultPanel(GcmResultsTableDialog gcmResultsTableDialog, Vector<GcmResponseStatus> data, int failed, int passed) {
        this.gcmResultsTableDialog = gcmResultsTableDialog;
        initTable(data);

        if(failed == 0) {
            resultFailureLabel.setVisible(false);
        } else {
            resultFailureLabel.setText("Failed = "+failed);
        }

        if(passed == 0) {
            resultSuccessLabel.setVisible(false);
        } else {
            resultSuccessLabel.setText("Success = "+passed);
        }

        if(data == null) {
            resultTotalLabel.setVisible(false);
        } else {
            resultTotalLabel.setText("Total = "+data.size());
        }
    }

    private void initTable(Vector<GcmResponseStatus> data) {
        GcmResponseStatusTableModel tableModel = new GcmResponseStatusTableModel(COLUMNS,data);
        gcmResultsTable.setModel(tableModel);
        gcmResultsTable.getColumnModel().getColumn(0).setPreferredWidth(600);
        gcmResultsTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        gcmResultsTable.getColumnModel().getColumn(0).setCellRenderer(new GcmIdCellRenderer());
    }

    public JPanel getMainPanel() {
        return this.mainPanel;
    }


    private static class GcmResponseStatusTableModel extends AbstractTableModel {

        public static final int REG_ID_COLUMN_INDEX = 0;
        public static final int STATUS_COLUMN_INDEX = 1;

        private String[] columnNames;
        private Vector<GcmResponseStatus> dataVector;

        public GcmResponseStatusTableModel(String[] columnNames,Vector<GcmResponseStatus> dataVector) {
            this.columnNames = columnNames;
            this.dataVector = dataVector;
            fireTableDataChanged();
        }

        public String getColumnName(int column) {
            return columnNames[column];
        }

        public Class getColumnClass(int column) {
            switch (column) {
                case REG_ID_COLUMN_INDEX:
                case STATUS_COLUMN_INDEX:
                    return String.class;
                default:
                    return Object.class;
            }
        }


        @Override
        public int getRowCount() {
            return dataVector.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            GcmResponseStatus gcmResponseStatus = dataVector.get(rowIndex);

            switch (columnIndex) {
                case REG_ID_COLUMN_INDEX:
                    return gcmResponseStatus.gcmId;
                case STATUS_COLUMN_INDEX:
                    return gcmResponseStatus.status;
            }
            return "";
        }

    }

    private static class GcmIdCellRenderer extends JTextArea implements TableCellRenderer {

        public GcmIdCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
        }

        public JComponent getTableCellRendererComponent(JTable table, Object
                value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());//or something in value, like value.getNote()...
            setSize(table.getColumnModel().getColumn(column).getWidth(),
                    getPreferredSize().height);
            if (table.getRowHeight(row) != getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height);
            }
            return this;
        }
    }

}
