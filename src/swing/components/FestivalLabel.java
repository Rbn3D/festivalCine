package swing.components;

import javax.swing.*;
import java.awt.*;
import java.awt.font.*;

/**
 * 
 * @author Ruben
 *
 *	JLabel con efecto de sombreado del texto y constructores adicionales para un manejo eficiente
 *
 */
public class FestivalLabel extends JLabel 
{
	/**
	 * Crea un FestivalLabel con el texto especificado
	 * @param text
	 */
	public FestivalLabel(String text) {
        super(text);
    }
	
	/**
	 * Crea un FestivalLabel con el texto y color especificados
	 * @param text
	 * @param color
	 */
	public FestivalLabel(String text, Color color)
	{
		this(text);
		setForeground(color);
	}
	
	/**
	 * Crea un FestivalLabel con el texto, color y tamaño especificados
	 * @param text
	 * @param color
	 * @param size
	 */
	public FestivalLabel(String text, Color color, float size)
	{
		this(text, color);
		setFont(getFont().deriveFont(size));
	}

	private int left_x = 1, left_y = 1, right_x = 1, right_y = 1;
    private Color left_color = Color.gray, right_color = Color.gray;
    public void setLeftShadow(int x, int y, Color color) {
        left_x = x;
        left_y = y;
        left_color = color;
    }

    public void setRightShadow(int x, int y, Color color) {
        right_x = x;
        right_y = y;
        right_color = color;
    }

    public Dimension getPreferredSize() {
        String text = getText();
        FontMetrics fm = this.getFontMetrics(getFont());

        int w = fm.stringWidth(text);
        w += (text.length()-1);
        w += left_x + right_x;

        int h = fm.getHeight();
        h += left_y + right_y;

        return new Dimension(w,h);
    }
    
    /**
     * Renderiza el FestivalLabel con efectos de sombreado
     */
    public void paintComponent(Graphics g) {
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        char[] chars = getText().toCharArray();

        FontMetrics fm = this.getFontMetrics(getFont());
        int h = fm.getAscent();
        LineMetrics lm = fm.getLineMetrics(getText(),g);
        g.setFont(getFont());

        int x = 0;

        for(int i=0; i<chars.length; i++) {
            char ch = chars[i];
            int w = fm.charWidth(ch);

            g.setColor(left_color);
            g.drawString(""+chars[i],x-left_x,h-left_y);

            g.setColor(right_color);
            g.drawString(""+chars[i],x+right_x,h+right_y);

            g.setColor(getForeground());
            g.drawString(""+chars[i],x,h);

            x+=w;
        }

    }
}
