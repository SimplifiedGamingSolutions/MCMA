import java.awt.EventQueue;

import com.sgs.mcma.gui.view.BaseFrame;


public class Main {

	public static void main(String[] args) {
		InitializeFrames();
	}
	private static void InitializeFrames(){
		EventQueue.invokeLater(new Runnable() {	
			public void run() {
				BaseFrame frame = new BaseFrame("Minecraft Mod Admin", 1024, 700);
				frame.setVisible(true);
			}
		});
	}

}
