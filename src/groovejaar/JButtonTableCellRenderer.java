package groovejaar;
/*
 * Author:  andbin - http://andbin.altervista.org
 * License: Public Domain
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS, IMPLIED OR OTHERWISE, INCLUDING WITHOUT LIMITATION, ANY
 * WARRANTY OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.
 */
import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;





public class JButtonTableCellRenderer extends JButton implements TableCellRenderer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1777876296523346352L;
	public JButtonTableCellRenderer() {
        // Some look and feels might ignore this ....
        setRolloverEnabled(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	String buttonText = "Download";
        setText(buttonText);

        getModel().setRollover(hasFocus);

        return this;
    }


    /*
     * The following methods are overridden to be "empty" for performance
     * reasons. If you want to understand better why, please read:
     *
     * http://java.sun.com/javase/6/docs/api/javax/swing/table/DefaultTableCellRenderer.html#override
     */
    public void invalidate() {}
    public void validate() {}
    public void revalidate() {}
    public void repaint(long tm, int x, int y, int width, int height) {}
    public void repaint(Rectangle r) {}
    public void repaint() {}
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
}
