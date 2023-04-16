package lefiydev;

import java.awt.EventQueue;

import lefiydev.frame.Screen;

public class Main {
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					Screen sceen = new Screen();
					sceen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}