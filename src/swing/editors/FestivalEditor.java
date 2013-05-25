package swing.editors;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import dao.DBObject;
import dao.IFestivalDAO.TipoConsulta;
import datos.ETipoUsuario;
import datos.Usuario;

import net.miginfocom.swing.MigLayout;

import proceso.AppController;
import swing.eventos.FormChangeEventHandler;
import swing.eventos.FormChangeListener;
import swing.forms.IFormOf;
import swing.views.FestivalAppPanel;

/**
 * Clase dedicada a gestionar la edición de objetos en la aplicación
 * @author Ruben
 *
 * @param <B> el Tipo a gestionar
 */
public class FestivalEditor<B extends DBObject> extends FestivalAppPanel 
{
	/**
	 * Tipo de dato
	 */
	protected B tipo;
	/**
	 * Tabla para ver la lista de objetos
	 */
	protected JXTable editorTable;
	/**
	 * TableModel para recibir y mostrar los datos
	 */
	protected FestivalListTableModel tableModel;
	/**
	 * ScrollPane para permitir scrolling en editorTale
	 */
	protected JScrollPane tableScroll;
	/**
	 * Botón de añadir
	 */
	protected JButton btnAdd = new JButton("Añadir");
	/**
	 * Botón de editar
	 */
	protected JButton btnEdit = new JButton("Editar");
	/**
	 * Botón de eliminar
	 */
	protected JButton btnRemove = new JButton("Eliminar");
	/**
	 * Botón de refrescar
	 */
	protected JButton btnRefresh = new JButton("Refrescar");
	/**
	 * Panel donde se situan los botones de edición
	 */
	protected JPanel pnlButtons = new JPanel(new MigLayout("fill, wrap", "[]","[][][][]"));
	/**
	 * Lista de escuchadores a los eventos de cambios en los datos
	 */
	protected ArrayList<FormChangeListener<B>> tblChangeListeners = new ArrayList<FormChangeListener<B>>();
	
	/**
	 * Crea un Festival editor con los siguientes valores
	 * @param controller Controlador de aplicación, usado para obtener los datos
	 * @param editForm Formulario de edicion para añadir y editar los datos
	 * @param tableModel TableModel para mostrar los datos
	 */
	public FestivalEditor(AppController controller, IFormOf<B> editForm, FestivalListTableModel<B> tableModel)
	{
		setAppController(controller);
		setEditForm(editForm);
		this.tableModel = tableModel; 
		setLayout(new MigLayout("","[600][75]"));
		editorTable = new JXTable(tableModel);
		editorTable.setSortable(true);
		editorTable.addHighlighter(HighlighterFactory.createSimpleStriping());
		editorTable.setShowGrid(false, true); 
		editorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableScroll = new JScrollPane(editorTable);
		add(tableScroll, "cell 0 0,grow");
		pnlButtons.add(btnAdd,"growx");
		pnlButtons.add(btnEdit,"growx");
		pnlButtons.add(btnRemove, "growx");
		pnlButtons.add(btnRefresh, "growx");
		add(pnlButtons, "cell 1 0, grow");
		setOpaque(false);
		editorTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) 
			{
				handleSelectionChange(e.getFirstIndex());
			}
		});
		btnAdd.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				FestivalEditor.this.editForm.propmt(TipoConsulta.insertar, null);
			}
		});
		btnEdit.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				procesarEditarRegistro();
			}
		});
		btnRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				procesarEliminarRegistro();	
			}
		});
		btnRefresh.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				actualizarDatos();
			}
		});
		if(editForm!=null)
		{
			editForm.addFormListener(new FormChangeListener<B>() {
	
				@Override
				public void FormChanged(FormChangeEventHandler<B> handler) 
				{
					if(handler.isNeedsUpdate())
						actualizarDatos();
				}
			});
		}
	}

	/**
	 * Dispara el evento de selección cambiada
	 * @param index
	 */
	private void handleSelectionChange(int index)
	{
		B value;
		try
		{
			value = (B) tableModel.getData().get(index);
		}
		catch(Exception e)
		{
			value = null;
		}
		fireChangeEvent(new FormChangeEventHandler<B>(null, value, true));
	}

	/**
	 * Actualiza los datos en la tabla, obteniendolos de nuevo de la capaDAO
	 */
	public void actualizarDatos() 
	{
		tableModel.updateData();	
	}

	/**
	 * Procesa la eliminación de un registro
	 */
	private void procesarEliminarRegistro() 
	{
		if(editorTable.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(FestivalEditor.this, "Ningún registro seleccionado.Seleccione el registro a eliminar primero.");
		}
		else 
		{
			String msg = "¿Desea eliminar el registro seleccionado?";
			if(editorTable.getSelectedRowCount() > 1)
				msg = "Ha seleccionado " + editorTable.getSelectedRowCount() + " registros. ¿Desea eliminarlos?";
			if(JOptionPane.showConfirmDialog(this, msg) == JOptionPane.YES_OPTION)
			{
				for(int i : editorTable.getSelectedRows())
				{
					DBObject obj = (DBObject) tableModel.getData().get(i);
					getAppController().getCapaDAO().borrar(obj);

				}
				actualizarDatos();
			}
		}
	}

	/**
	 * Procesa la edición de un registro
	 */
	private void procesarEditarRegistro() 
	{
		if(editorTable.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(FestivalEditor.this, "Ningún registro seleccionado.Seleccione el registro a editar primero.");
		}
		else if(editorTable.getSelectedRowCount() > 1)
		{
			JOptionPane.showMessageDialog(FestivalEditor.this, "Ha seleccionado varios registros. Seleccione sólo el registro a editar.");
		}
		else
		{
			FestivalEditor.this.editForm.propmt(TipoConsulta.modificar, (B)tableModel.getData().get(editorTable.getSelectedRow()));
		}
	}

	/**
	 * Formulario de inserción y edición
	 */
	private IFormOf<B> editForm;
	
	/**
	 * Getter de editForm
	 */
	public IFormOf<B> getEditForm() 
	{
		return editForm;
	}
	/**
	 * Setter de editForm
	 */
	public void setEditForm(IFormOf<B> editForm) 
	{
		this.editForm = editForm;
	}
	/**
	 * Añade un escuchador al evento de cambios en los datos
	 * @param listener El escuchador
	 */
	public void addChangeListener(FormChangeListener<B> listener)
	{
		tblChangeListeners.add(listener);
	}
	/**
	 * Elimina un escuchador del evento de cambios en los datos
	 * @param listener El escuchador
	 */
	public void removeChangeListener(FormChangeListener<B> listener)
	{
		tblChangeListeners.remove(listener);
	}
	
	/**
	 * Dispara el evento de cambios en los datos
	 * @param handler El EventHandler
	 */
	protected void fireChangeEvent(FormChangeEventHandler<B> handler)
	{
		for(FormChangeListener<B> listener : tblChangeListeners)
			listener.FormChanged(handler);
	}
	
	/**
	 * Actualiza los datos de la tabla
	 */
	public void updateData()
	{
		tableModel.updateData();
	}
	
}
