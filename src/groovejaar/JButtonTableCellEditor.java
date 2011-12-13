package groovejaar;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;


public class JButtonTableCellEditor extends AbstractCellEditor implements TableCellEditor {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3656274464878321185L;
	private JButton button;

    public JButtonTableCellEditor() {
        button = new InternalButton();
    }

    public void addActionListener(ActionListener l) {
        // Forwards the add to the button.
        button.addActionListener(l);
    }

    public void removeActionListener(ActionListener l) {
        // Forwards the remove to the button.
        button.removeActionListener(l);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        String buttonText = "Download";
        button.setText(buttonText);

        return button;
    }

    public Object getCellEditorValue() {
        return button.getText();
    }


    private class InternalButton extends JButton {
        /**
		 * 
		 */
		private static final long serialVersionUID = 7271801045259085299L;

		protected void fireActionPerformed(ActionEvent event) {
            // First of all, fires all actionPerformed.
            super.fireActionPerformed(event);
           
            // Then stops cell editing.
            fireEditingStopped();
        }
    }
}
