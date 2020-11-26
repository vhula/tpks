package io.github.vhula.tpks.drawable;

import java.awt.*;

/**
 * Клас для малювання ЛСА.
 * @author Vadym Hula
 *
 */
public interface Drawer {
	
	void draw(Graphics g);
	/**
	 * Оновлює малюнок ЛСА.
	 */
	void refresh();

}
