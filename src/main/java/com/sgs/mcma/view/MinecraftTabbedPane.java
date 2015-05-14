package com.sgs.mcma.view;

import java.awt.Container;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalTabbedPaneUI;

import com.sgs.mcma.view.summary.PlayerListPanel;

@SuppressWarnings("serial")
public class MinecraftTabbedPane extends JTabbedPane {

	public MinecraftTabbedPane() {
		Font font = null;
		try{
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("Resources\\Minecrafter.Alt.ttf")));
		}
		catch(Exception e){
			e.printStackTrace();
		}
        font = font.deriveFont(Font.PLAIN,20);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		setFont(font);
		setUI(new StretchTabbedPaneUI());
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseExited(e);
				PlayerListPanel.instance.popup.setVisible(false);
			}
		});
	}

	public MinecraftTabbedPane(int tabPlacement) {
		super(tabPlacement);
	}

	public MinecraftTabbedPane(int tabPlacement, int tabLayoutPolicy) {
		super(tabPlacement, tabLayoutPolicy);
	}

	public static class StretchTabbedPaneUI extends MetalTabbedPaneUI {
		
		public StretchTabbedPaneUI() {
		}
		
		@Override
		protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
			return fontHeight + 10;
		}
		
	    public static ComponentUI createUI(JComponent c) {
	        return new StretchTabbedPaneUI();
	    }

	    @Override
	    protected LayoutManager createLayoutManager() {
	        return new StretchTabbedPaneLayout();
	    }


	    protected class StretchTabbedPaneLayout extends MetalTabbedPaneUI.TabbedPaneLayout {

	        protected Container tabContainer;

	        @Override
	        protected void calculateTabRects(int tabPlacement, int tabCount) {
	            super.calculateTabRects(tabPlacement, tabCount);
	            // TODO: check if it makes sense to stretch
	            int max = 0;
	            int sum = 0;
	            Rectangle r = new Rectangle();
	            for (int i = 0; i < tabCount; i++) {
	                getTabBounds(i, r);
	                max = Math.max(max, r.width);
	                sum += r.width;
	            }
	            // TODO: calculate real width, that is -insets
	            int paneWidth = tabPane.getWidth() - 10; 
	            int free = paneWidth - sum;
	            // nothing to distribute
	            if (free < tabCount) return;
	            int add = free /tabCount;
	            int offset = 0;
	            for (int i = 0; i < tabCount; i++) {
	                r = rects[i]; 
	                r.height = 40;
	                r.x += offset;
	                r.width += add;
	                offset += add;
	            }

	        }

	    }
	}
}
