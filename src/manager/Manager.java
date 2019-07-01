package manager;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class Manager extends JFrame {
	View view;
	
	public Manager() {
		view =new View();
		
		JTabbedPane pane = new JTabbedPane();
		pane.add("¸ÀÁý°ü¸®", view);
		pane.setSelectedIndex(0);
		add("Center",pane);
		setSize(800,1050);
		setVisible(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public static void main(String[] args) {
		new Manager();
		new Login();
	}

}
