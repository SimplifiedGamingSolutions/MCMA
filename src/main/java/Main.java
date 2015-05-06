import java.awt.EventQueue;

import com.sgs.mcma.gui.view.BaseFrame;
import com.sgs.mcma.shared.communication.Server;


public class Main {

	public static void main(String[] args) {
		InitializeFrames();
		Thread server = new Thread(new Runnable(){
			public void run() {
				new Server("localhost", 39640).run();
			}
		});
		server.start();
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
