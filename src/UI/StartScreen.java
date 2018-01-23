package UI;

import java.nio.file.Path;
import java.nio.file.Paths;

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
	private Controller controller;

	public StartScreen(Controller controller)
	{
		this.controller = controller;
	}
	
	public void drawStartScreen()
	{
		pane = new JPanel();
		super.setContentPane(pane);
		JLabel name = new JLabel("Hi !");
		pane.add(name);
		
        Path currentRelativePath = Paths.get("");
        Path s = currentRelativePath.toAbsolutePath();

		final JFileChooser fc = new JFileChooser(s.toFile());
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
	      controller.getFacade().passFile(dir.getText() + "/" + filename.getText());
		pane.setVisible(true);
		this.pack();
		this.setVisible(true);
	
	}
}
