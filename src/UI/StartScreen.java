package UI;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Denis Blondeel
 *
 */

public class StartScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	
	JPanel pane;

	public StartScreen()
	{
	}
	
	public void drawStartScreen(Controller controller)
	{
		pane = new JPanel();
		super.setContentPane(pane);
		JLabel name = new JLabel("Hi !");
		pane.add(name);
		
		final JFileChooser fc = new JFileChooser();
		pane.add(fc);
		
		pane.setVisible(true);
		this.pack();
		this.setVisible(true);
	
	}
}
