package presentation;

import domain.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GomokuGUI extends JFrame{
	
	private static final  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static final int WIDTH = (3*screenSize.width)/4;
    private static final int HIGH =  (3*screenSize.height)/4;
    private static final Dimension PREFERRED_DIMENSION =
                         new Dimension(WIDTH,HIGH);
    private Gomoku gomoku;
    
    //Menu
    private JMenuItem menuNew;
    private JMenuItem menuOpen;
    private JMenuItem menuSave;
    private JMenuItem menuClose;
    
    //Start
    private JButton startButton;
    JComboBox<String> gameMode;
    JComboBox<String> gamePlayers;
    JTextField field;
    
    private GomokuGUI() {
    	gomoku = new Gomoku("Normal",10,0);
    	prepareElements();
    	prepareActions();
    }
    
	public static void main(String[] args) {
		GomokuGUI gui = new GomokuGUI();
		gui.setVisible(true);
	}
	
	private void prepareElements(){
		setTitle("GomokuPOO");
		setSize(PREFERRED_DIMENSION);
		int x = (screenSize.width - WIDTH)/2;
        int y = (screenSize.height - HIGH)/2;
        setLocation(x,y);
        setJMenuBar(prepareElementsMenu()); 
        getContentPane().add(prepareElementsStart());
	}
	
	private JMenuBar prepareElementsMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("File");
		
		menuNew = new JMenuItem("New");
		menuOpen = new JMenuItem("Open");
		menuSave = new JMenuItem("Save");
		menuClose = new JMenuItem("Close");
		
		gameMenu.add(menuNew);
		gameMenu.add(menuOpen);
		gameMenu.add(menuSave);
		gameMenu.addSeparator();
		gameMenu.add(menuClose);
		
		menuBar.add(gameMenu);
		menuBar.setBorderPainted(true);
		return menuBar;
		
	}
	
	private JPanel prepareElementsStart() {
		JPanel start =  new JPanel();
		start.setLayout(new BorderLayout());
		startButton =new JButton("Start new game");
		start.add(startButton,BorderLayout.CENTER);
		return start;
	}
	
	private void prepareElementsGame() {
		JPanel game = new JPanel();
		game.setLayout(new BorderLayout());
		JPanel board = new JPanel();
		CompoundBorder border = new CompoundBorder(new EmptyBorder(0,WIDTH/4,0,WIDTH/4),
				new TitledBorder(""));
		board.setBorder(border);
		board.setLayout(new GridLayout());
	}
	
	private JPanel prepareElementsStartConfig() {
		JPanel config = new JPanel();
		config.setLayout(null);
		field = new JTextField("Size of the board");
		field.setBounds(10,10,120,20);
		
		gameMode = new JComboBox<>();
		gameMode.setBounds(10,40,180,20);
		gameMode.addItem("Normal");
		gameMode.addItem("QuickTime");
		gameMode.addItem("Limited");
		
		gamePlayers = new JComboBox<>();
		gamePlayers.setBounds(10,70,180,20);
		gamePlayers.addItem("Player vs Player");
		gamePlayers.addItem("Player vs Machine");
		gamePlayers.addItem("Machine vs Machine");
		
		config.add(field);
		config.add(gameMode);
		config.add(gamePlayers);
		return config;
	}
	
	private void prepareActions(){
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			 public void windowClosing(WindowEvent ev){
				 closeOption();
			 }
		 });
		 prepareActionStart();
	}
	
	private void prepareActionStart() {
		JFrame parent = this;
		startButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getContentPane().removeAll();
				JDialog gameConfig = new JDialog(parent,"Game configs");
				gameConfig.setSize(new Dimension(WIDTH/2,HIGH/2));
				int x = (screenSize.width - WIDTH/2)/2;
		        int y = (screenSize.height - HIGH/2)/2;
		        gameConfig.setLocation(x,y);
		        prepareActionStartOption(gameConfig);
		        gameConfig.add(prepareElementsStartConfig());
				gameConfig.setVisible(true);
				String selectedMode = (String)gameMode.getSelectedItem();
				repaint();
			}
		});
	}
	private void prepareActionStartOption(JDialog parent){
		parent.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                field.requestFocusInWindow();
                System.out.println("Entro");
            }
        });
	}
	
	
	private void closeOption() {
		int yesNo = JOptionPane.showOptionDialog(null,"Are you sure you want exit?","Warning",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.CANCEL_OPTION,
					null,
					null,"No");
		if (yesNo == JOptionPane.YES_OPTION) {
			setVisible(false);
			System.exit(0);
		}
	}

}
