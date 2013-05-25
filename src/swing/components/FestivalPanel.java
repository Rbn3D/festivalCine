package swing.components;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * 
 * Contenedor JPanel que tiene un degradado de fondo con colores personalizbles y una imagen de fondo
 * 
 * @author Ruben
 *
 */
public class FestivalPanel extends JPanel 
{
	public FestivalPanel()
	{
		File dir = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
		backgroundImage = new ImageIcon(dir.getParent() + "/res/images/cine.png");
	}
	
	/**
	 * Imagen de fondo
	 */
	private ImageIcon backgroundImage;
	/**
	 * Color de gradiante 1
	 */
	private Color colorGradient1 = new Color(50,210,255);
	/**
	 * Color de gradiante 2
	 */
	private Color colorGradient2 = new Color(0,120,255);
	
	/**
	 * Getter de colorGradient1
	 */
	public Color getColorGradient1() {
		return colorGradient1;
	}
	/**
	 * Setter de colorGradient1
	 */
	public void setColorGradient1(Color colorGradient1) {
		this.colorGradient1 = colorGradient1;
		this.repaint();
	}
	/**
	 * Getter de colorGradient2
	 */
	public Color getColorGradient2() {
		return colorGradient2;
	}
	/**
	 * Setter de colorGradient2
	 */
	public void setColorGradient2(Color colorGradient2) {
		this.colorGradient2 = colorGradient2;
		this.repaint();
	}
	/**
	 * Renderiza el JPanel con el degradado y la imagen de fondo
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
	Graphics2D g2d = (Graphics2D)g;
	int w = getWidth( );
	int h = getHeight( );
	GradientPaint gp = new GradientPaint(0, 0, colorGradient1,0, h, colorGradient2 );
	g2d.setPaint( gp );
	g2d.fillRect( 0, 0, w, h );
	g2d.drawImage(backgroundImage.getImage(), (w/2)-(backgroundImage.getIconWidth()/2), (h/2)-(backgroundImage.getIconHeight()/2), null);
	setOpaque(false);
	super.paintComponent(g);
	setOpaque(true);
	}
}
