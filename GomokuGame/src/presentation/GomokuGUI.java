package presentation;

import domain.*;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.*;

public class GomokuGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (3 * screenSize.width) / 4;
	public static final int HIGH = (3 * screenSize.height) / 4;
	private static final Dimension PREFERRED_DIMENSION = new Dimension(WIDTH, HIGH);
	private Gomoku gomoku;

	// Menu
	private JMenuItem menuNew;
	private JMenuItem menuOpen;
	private JMenuItem menuSave;
	private JMenuItem menuClose;

	// Start
	public JPanel start;
	private JButton startButton;
	
	// Game

	private GomokuState state;
	private String move;
	
	// PlayerOne
	private JPanel player1;
	private JButton[] tokensPlayer1;
	JPanel tokens1;
	
	//PlayerTwo
	private JPanel player2;
	private JButton[] tokensPlayer2;
	JPanel tokens2;

	private GomokuGUI() {
		prepareElements();
		prepareActions();
	}

	public static void main(String[] args) {
		GomokuGUI gui = new GomokuGUI();
		gui.setVisible(true);
	}

	private void prepareElements() {
		setTitle("GomokuPOO");
		setSize(PREFERRED_DIMENSION);
		int x = (screenSize.width - WIDTH) / 2;
		int y = (screenSize.height - HIGH) / 2;
		setLocation(x, y);
		setJMenuBar(prepareElementsMenu());
		prepareElementsStart();
		getContentPane().add(start);
		setResizable(false);
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

	public void prepareElementsStart() {
		start = new JPanel();
		start.setLayout(new BorderLayout());
		startButton = new JButton("Start new game");
		start.add(startButton, BorderLayout.CENTER);
	}

	
	public void prepareElementsGame() {
		this.gomoku = Gomoku.getGomoku();
		getContentPane().removeAll();
		move = "NormalToken";
		state = new GomokuState(this);
		player1 = new JPanel();
		player2 = new JPanel();
		tokens1 = new JPanel();
		Set<Class<? extends Token>> tokens = gomoku.getTokenSubtypes();
		tokens1.setLayout(new GridLayout(tokens.size(), 2));
		tokens1.setBorder(
				new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder("Tokens player one:" )));
		tokens2 = new JPanel();
		tokens2.setLayout(new GridLayout(tokens.size(), 2));
		tokens2.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder("Tokens player two:" )));
		
		tokensPlayer1 = new JButton[tokens.size()];
		tokensPlayer2 = new JButton[tokens.size()];
		int i = 0;
		for(Class<? extends Token> token : tokens) {
			tokensPlayer1[i] = new JButton(token.getSimpleName());
			tokensPlayer2[i] = new JButton(token.getSimpleName());
			tokensPlayer1[i].setEnabled(false);
			i++;
		}
		prepareElementsTokensInfo();
		prepareActionsTokensInfo();
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		getContentPane().add(player1, gbc);

		gbc.gridx = 1;
		getContentPane().add(state, gbc);

		gbc.gridx = 2;
		getContentPane().add(player2, gbc);

		getContentPane().revalidate();
		getContentPane().repaint();
	}

	private void prepareElementsPlayer1() {
		tokens1.removeAll();
		Player playerOne = gomoku.getPlayerOne();
		HashMap<String, Integer> map = playerOne.getMap();
		for(int i = 0; i < map.size();i++) {
			if (tokensPlayer1[i].isEnabled())tokensPlayer1[i].setEnabled(false);
			else tokensPlayer1[i].setEnabled(true);
			tokens1.add(tokensPlayer1[i]);
			tokens1.add(new JLabel(": " + map.get(tokensPlayer1[i].getText())));
		}
		player1.add(tokens1);
		player1.revalidate();
		player1.repaint();
	}

	private void prepareElementsPlayer2() {
		tokens2.removeAll();
		Player playerTwo = gomoku.getPlayerTwo();
		HashMap<String, Integer> map = playerTwo.getMap();
		for(int i = 0; i < map.size();i++) {
			if (tokensPlayer2[i].isEnabled())tokensPlayer2[i].setEnabled(false);
			else tokensPlayer2[i].setEnabled(true);
			tokens2.add(tokensPlayer2[i]);
			tokens2.add(new JLabel(": " + map.get(tokensPlayer2[i].getText())));
		}
		player2.add(tokens2);
		player2.revalidate();
		player2.repaint();
	}

	public void prepareElementsTokensInfo() {
		prepareElementsPlayer1();
		prepareElementsPlayer2();
	}

	private void prepareActions() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				closeOption();
			}
		});
		prepareActionsStart();
	}

	private void prepareActionsStart() {
		GomokuGUI parent = this;
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				JDialog gameConfig = new GameConfig(screenSize,WIDTH,HIGH,parent);
				
			}
		});
	}
	
	private void prepareActionsTokensInfo(){
		for(JButton j : tokensPlayer1 ) {
			j.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					move = j.getText();
				}
			});
		}
		
		for(JButton j : tokensPlayer2) {
			j.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					move = j.getText();
				}
			});
		}
	}

	private void closeOption() {
		int yesNo = JOptionPane.showOptionDialog(null, "Are you sure you want exit?", "Warning",
				JOptionPane.YES_NO_OPTION, JOptionPane.CANCEL_OPTION, null, null, "No");
		if (yesNo == JOptionPane.YES_OPTION) {
			setVisible(false);
			System.exit(0);
		}
	}

	public String getMove() {
		return move;
	}

}


