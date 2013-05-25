package swing.forms;

import javax.annotation.Nonnull;
import javax.swing.JPasswordField;

import org.formbuilder.mapping.typemapper.impl.StringMapper;

/**
 * Usado para generar un JPasswordField en el formulario de Login de la aplicación
 * @author Ruben
 *
 */
public class StringToPasswordFieldMapper extends StringMapper<JPasswordField> {

	@Override @Nonnull
	public JPasswordField createEditorComponent() {
		return new JPasswordField();
	}

}
