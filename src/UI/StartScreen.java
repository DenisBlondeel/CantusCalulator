package UI;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.*;
import java.awt.*;
import java.lang.Object;
import java.awt.event.*;

import javax.swing.JComponent;

public class StartScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	
	JPanel pane;
	private JTextField filename = new JTextField(), dir = new JTextField();
	private Controller controller;

        private JFrame mainFrame;
        private JLabel headerLabel;
        private JLabel statusLabel;
        private JPanel controlPanel;

    public StartScreen(Controller controller)
    {
        this.controller = controller;
    }

    public void drawStartScreen() {
        prepareGUI();
        //create a menu bar
        final JMenuBar menuBar = new JMenuBar();

        //create menus
        final JMenu fileMenu = new JMenu("File");
        final JMenu aboutMenu = new JMenu("About");

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setActionCommand("Open");

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand("Exit");
 
        openMenuItem.addActionListener(new OpenFile());
        exitMenuItem.addActionListener(new Exit());

        //add menu items to menus
        fileMenu.add(openMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        //add menu to menubar
        menuBar.add(fileMenu);
        menuBar.add(aboutMenu);

        //add menubar to the frame
        mainFrame.setJMenuBar(menuBar);
        mainFrame.setVisible(true);
    }

    class OpenFile implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            getFile();
        }
    }

    class DrawGraphs implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.getFacade().drawPieChart();
            controller.getFacade().drawTimeline();
        }
    }

    class Exit implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        System.exit(0);
        }
    }


    public void getFile() {
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

    private void prepareGUI() {
        mainFrame = new JFrame("Cantus Calculator");
        mainFrame.setSize(400,400);
        mainFrame.setLayout(new GridLayout(3, 1));

        headerLabel = new JLabel("",JLabel.CENTER );
        statusLabel = new JLabel("",JLabel.CENTER);    
        statusLabel.setSize(350,100);
    
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }    
        });
    
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    } 
}
