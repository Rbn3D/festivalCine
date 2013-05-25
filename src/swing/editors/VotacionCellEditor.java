package swing.editors;

import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;

/**
 * Editor de celdas numéricas con JSpinner (Usado en UserPanel para puntuar las películas)
 * @author Ruben
 *
 */
public class VotacionCellEditor extends AbstractCellEditor implements TableCellEditor {
	private JSpinner spinner = null;

	public VotacionCellEditor() {
		SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 5, 1);
		spinner = new JSpinner(model);
		JFormattedTextField textField = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
		textField.setColumns(5);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					fireEditingStopped();
				}
			}
		});

		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e)
			{
			}
		});
	}

	@Override
	public Object getCellEditorValue() {
		Integer integer = (Integer)spinner.getValue();
		return integer.doubleValue();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		return spinner;
	}
}