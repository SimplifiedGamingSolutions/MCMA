import java.awt.EventQueue;

import com.sgs.mcma.gui.view.BaseFrame;


public class Main {

	public static void main(String[] args) {
		InitializeFrames();
	}
	private static void InitializeFrames(){
		EventQueue.invokeLater(new Runnable() {	
			public void run() {
				BaseFrame frame = new BaseFrame("Mine Craft Mod Admin");
				frame.setVisible(true);
			}
		});
	}

}
