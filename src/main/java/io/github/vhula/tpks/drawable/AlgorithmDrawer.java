package io.github.vhula.tpks.drawable;

import io.github.vhula.tpks.controller.EditPropertiesCommand;
import io.github.vhula.tpks.model.*;
import io.github.vhula.tpks.view.ElementPopupMenu;
import io.github.vhula.tpks.view.FilePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class AlgorithmDrawer extends JPanel implements Drawer {

	private final ArrayList<JLabel> elements = new ArrayList<JLabel>();
	
	public AlgorithmDrawer() {
		super();
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEtchedBorder());
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		setVisible(true);
	}

	@Override
	public void draw(Graphics g) {
	}

	public void refresh() {
		FileModel fileModel = FileModel.getInstance();
		Algorithm algorithm = fileModel.getAlgorithm();
		this.removeAll();
		elements.clear();
		for (int i = 0; i < algorithm.size(); i++) {
			Element element = algorithm.getElement(i);
			int size = 30;
			int imSize = 48;
			Color imColor = Color.BLACK;
			String color = "black";
			String im = "";
			if (algorithm.getCursor() == element) {
				color = "blue";
				im = "_blue";
				imColor = Color.BLUE;
			}
			JLabel el = null;
			if (element.getClass().equals(OperatorElement.class) || 
					element.getClass().equals(ConditionalElement.class)) {
                AlgorithmHandler handler = FileModel.getInstance().getHandler();
                if (handler.eternals && handler.isInEternal(algorithm.getNodeIndex(element))) {
                    color = "red";
                }
				String label = "<html>";
				String[] names = element.getNames();
				int[] indexes = element.getIndexes();
				for (int j = 0; j < names.length; j++)
					label += String.format(" <b><font size=\"%d\" color=\"%s\">", size, color) + names[j] + "</b><sub>" + indexes[j] + "</sub>";
				el = new JLabel(label);
			}
			if (element.getClass().equals(BeginElement.class) ||
					element.getClass().equals(EndElement.class)) {
				el = new JLabel(String.format("<html><b><font size=\"%d\" color=\"%s\">", size, color)+element.getNames()[0]);
			}
			if (element.getClass().equals(UpArrowElement.class)) {
				BufferedImage image = new BufferedImage(imSize, imSize, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2 = image.createGraphics();
				g2.setColor(imColor);
				g2.setStroke(new BasicStroke(3));
				g2.setFont(new Font("LucidaSans", Font.PLAIN, 12));
				g2.drawString(""+element.getIndexes()[0], imSize/2, 12);
				g2.drawLine(imSize/2, 18, imSize/2, imSize);
				g2.drawLine(imSize/2, 18, imSize/4 + 6, imSize/4 + 12);
				g2.drawLine(imSize/2, 18, imSize/2 + 6, imSize/4 + 12);
				el = new JLabel(new ImageIcon(image));
			}
			if (element.getClass().equals(DownArrowElement.class)) {
				BufferedImage image = new BufferedImage(imSize, imSize, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2 = image.createGraphics();
				g2.setColor(imColor);
				g2.setStroke(new BasicStroke(3));
				g2.setFont(new Font("LucidaSans", Font.PLAIN, 12));
				g2.drawString(""+element.getIndexes()[0], imSize/2, 12);
				g2.drawLine(imSize/2, 18, imSize/2, imSize);
				g2.drawLine(imSize/2, imSize, imSize/2 - 6, imSize - 6);
				g2.drawLine(imSize/2, imSize, imSize/2 + 6, imSize - 6);
				el = new JLabel(new ImageIcon(image));
			}
			if (el != null) {
				add(el);
				el.addMouseListener(new MouseObserver());
				elements.add(el);
			}
			else {
				
			}
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		draw(g);
	}

	private class MouseObserver extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
            FileModel.getInstance().getHandler().eternals = false;
			if (e.isMetaDown()) {
				ElementPopupMenu menu = ElementPopupMenu.getMenu();
				int idx = elements.indexOf(e.getSource());
				FileModel.getInstance().getAlgorithm().setSelected(idx);
				menu.show((Component)e.getSource(), e.getX(), e.getY());
			} else if (e.getButton() == MouseEvent.BUTTON1) {
				int idx = elements.indexOf(e.getSource());
				FileModel.getInstance().getAlgorithm().setCursor(idx);
				FilePanel.getInstance(null).getDrawer().refresh();
				FileModel.getInstance().notifyObservers();
				EditorModel.getInstance(null).notifyObservers();
				if(e.getClickCount() >= 2) {
					FileModel.getInstance().getAlgorithm().setSelected(idx);
					new EditPropertiesCommand().doCommand();
				}
			}
		}
	}
	
	

}
