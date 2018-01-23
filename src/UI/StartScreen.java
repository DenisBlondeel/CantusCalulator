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
	private JTextField filename = new JTextField(), dir = new JTextField();

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
		
		int rVal = fc.showOpenDialog(fc);
	      if (rVal == JFileChooser.APPROVE_OPTION) {
	        filename.setText(fc.getSelectedFile().getName());
	        dir.setText(fc.getCurrentDirectory().toString());
	      }
	      if (rVal == JFileChooser.CANCEL_OPTION) {
	        filename.setText("You pressed cancel");
	        dir.setText("");
	      }
	      JLabel nameFile = new JLabel(filename.getText() + dir.getText());
	      pane.add(nameFile);
		pane.setVisible(true);
		this.pack();
		this.setVisible(true);
	
	}
}