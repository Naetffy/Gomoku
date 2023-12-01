package presentation;

import domain.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GomokuGUI extends JFrame {

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

	private JComboBox<String> gameMode;
	private JTextField limitTokens;
	private JTextField limitTime;

	private JComboBox<String> gamePlayers;
	private JTextField gamePlayerOne;
	private JTextField gamePlayerTwo;

	private JTextField gameSizeField;
	private JSlider gameSlider;
	private JButton createGame;

	// Game

	private GomokuState state;
	private JPanel player1;
	private JPanel player2;

	private GomokuGUI() {

		prepareElements();
		prepareActions();
	}

	public static void main(String[] args) {
		GomokuGUI gui = new GomokuGUI();
		gui.setVisible(true);
	}

	public Gomoku getGomoku() {
		return gomoku;
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

	private JPanel prepareElementsStartConfig() {
		JPanel config = new JPanel();
		config.setLayout(new GridLayout(4, 1));
		config.add(prepareElementsStartConfigGameSize());
		config.add(prepareElementsStartConfigGameMode());
		config.add(prepareElementsStartConfigGamePlayers());
		// config.add(prepareElementsStartConfigGameSlider());
		config.add(prepareElementsStartCreateGame());
		return config;
	}

	private JPanel prepareElementsStartConfigGameSize() {
		JPanel configGameSize = new JPanel();
		configGameSize
				.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder("Size of the board")));
		configGameSize.setLayout(new GridLayout(0, 3, WIDTH / 16, 0));
		gameSizeField = new JTextField("15");
		configGameSize.add(gameSizeField);
		return configGameSize;
	}

	private JPanel prepareElementsStartConfigGameMode() {
		JPanel configGameMode = new JPanel();
		configGameMode.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder("Game mode config")));
		configGameMode.setLayout(new GridLayout(0, 3, WIDTH / 16, 0));
		gameMode = new JComboBox<>();
		gameMode.addItem("Normal");
		gameMode.addItem("QuickTime");
		gameMode.addItem("Limited");
		limitTokens = new JTextField("Limit tokens");
		limitTokens.setEditable(false);
		limitTime = new JTextField("Limit time (seconds)");
		limitTime.setEditable(false);
		configGameMode.add(gameMode);
		configGameMode.add(limitTokens);
		configGameMode.add(limitTime);
		prepareActionsStartConfigGameMode();
		return configGameMode;
	}

	private JPanel prepareElementsStartConfigGamePlayers() {
		JPanel configGamePlayers = new JPanel();
		configGamePlayers
				.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder("Players config")));
		configGamePlayers.setLayout(new GridLayout(0, 3, WIDTH / 16, 0));
		gamePlayers = new JComboBox<>();
		gamePlayers.addItem("Player vs Player");
		gamePlayers.addItem("Player vs Machine");
		gamePlayers.addItem("Machine vs Machine");
		gamePlayerOne = new JTextField("Name player one");
		gamePlayerTwo = new JTextField("Name player two");
		configGamePlayers.add(gamePlayers);
		configGamePlayers.add(gamePlayerOne);
		configGamePlayers.add(gamePlayerTwo);
		prepareActionsStartConfigGamePlayers();
		return configGamePlayers;
	}

	private JPanel prepareElementsStartConfigGameSlider() {
		JPanel configGameSlider = new JPanel();
		configGameSlider.setBorder(
				new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder("Especial percentage (%)")));
		configGameSlider.setLayout(new FlowLayout(FlowLayout.LEFT));
		gameSlider = new JSlider(0, 100, 20);
		gameSlider.setMajorTickSpacing(20);
		gameSlider.setMinorTickSpacing(5);
		gameSlider.setPaintTicks(true);
		gameSlider.setPaintLabels(true);
		configGameSlider.add(gameSlider);
		return configGameSlider;
	}

	private JPanel prepareElementsStartCreateGame() {
		JPanel start = new JPanel();
		start.setLayout(new FlowLayout(FlowLayout.CENTER));
		createGame = new JButton("Create the game");
		start.add(createGame);
		return start;
	}

	private void prepareElementsGame() {
		getContentPane().removeAll();

		state = new GomokuState(this);
		player1 = new JPanel();
		player2 = new JPanel();
		prepareElementsPlayer1();
		prepareElementsPlayer2();

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
		player1 = new JPanel();
		player1.setLayout(new BoxLayout(player1, BoxLayout.Y_AXIS));
		Player playerOne = gomoku.getPlayerOne();
		HashMap<String, Integer> map = playerOne.getMap();
		JPanel tokens = new JPanel();
		tokens.setLayout(new GridLayout(3, 2));
		tokens.setBorder(
				new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder("Tokens" + playerOne.getName())));
		ImageIcon img1 = new ImageIcon("src/resources/images/Normal.png");
		tokens.add(new JLabel(img1));
		tokens.add(new JLabel("Normal token: " + map.get("Normal")));
		ImageIcon img2 = new ImageIcon("src/resources/images/Heavy.png");
		tokens.add(new JLabel(img2));
		tokens.add(new JLabel("Heavy token: " + map.get("Heavy")));
		ImageIcon img3 = new ImageIcon("src/resources/images/Temporary.png");
		tokens.add(new JLabel(img3));
		tokens.add(new JLabel("Temporary token: " + map.get("Temporary")));
		player1.add(tokens);
	}

	private void prepareElementsPlayer2() {
		player2 = new JPanel();
		player2.setLayout(new BoxLayout(player2, BoxLayout.Y_AXIS));
		Player playerTwo = gomoku.getPlayerTwo();
		HashMap<String, Integer> map = playerTwo.getMap();
		JPanel tokens = new JPanel();
		tokens.setLayout(new GridLayout(3, 2));
		tokens.setBorder(
				new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder("Tokens" + playerTwo.getName())));
		ImageIcon img1 = new ImageIcon("src/resources/images/Normal.png");
		tokens.add(new JLabel(img1));
		tokens.add(new JLabel("Normal token: " + map.get("Normal")));
		ImageIcon img2 = new ImageIcon("src/resources/images/Heavy.png");
		tokens.add(new JLabel(img2));
		tokens.add(new JLabel("Heavy token: " + map.get("Heavy")));
		ImageIcon img3 = new ImageIcon("src/resources/images/Temporary.png");
		tokens.add(new JLabel(img3));
		tokens.add(new JLabel("Temporary token: " + map.get("Temporary")));
		player2.add(tokens);
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
		JFrame parent = this;
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog gameConfig = new JDialog(parent, "Game configs");
				gameConfig.setSize(new Dimension(WIDTH / 2, HIGH / 2));
				int x = (screenSize.width - WIDTH / 2) / 2;
				int y = (screenSize.height - HIGH / 2) / 2;
				gameConfig.setLocation(x, y);
				gameConfig.add(prepareElementsStartConfig());
				gameConfig.setVisible(true);
				prepareActionsCreateGame(gameConfig);
			}
		});
	}

	private void prepareActionsStartConfigGameMode() {
		gameMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> combo = (JComboBox<String>) e.getSource();
				String selecction = (String) combo.getSelectedItem();
				if (selecction.equals("Normal")) {
					limitTokens.setEditable(false);
					limitTime.setEditable(false);
					limitTokens.setText("No limit");
					limitTime.setText("No limit");
				} else if (selecction.equals("QuickTime")) {
					limitTokens.setEditable(false);
					limitTime.setEditable(true);
					limitTokens.setText("No limit");
					limitTime.setText("600");
				} else {
					limitTokens.setEditable(true);
					limitTime.setEditable(false);
					limitTokens.setText("50");
					limitTime.setText("No limit");
				}
			}
		});
	}

	private void prepareActionsStartConfigGamePlayers() {
		gamePlayers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> combo = (JComboBox<String>) e.getSource();
				String selecction = (String) combo.getSelectedItem();
				if (selecction.equals("Player vs Player")) {
					gamePlayerOne.setEditable(true);
					gamePlayerTwo.setEditable(true);
					gamePlayerOne.setText("Name player one");
					gamePlayerTwo.setText("Name player two");
				} else if (selecction.equals("Player vs Machine")) {
					gamePlayerOne.setEditable(true);
					gamePlayerTwo.setEditable(false);
					gamePlayerOne.setText("Name player one");
					gamePlayerTwo.setText("Machine");
				} else {
					gamePlayerOne.setEditable(false);
					gamePlayerTwo.setEditable(false);
					gamePlayerOne.setText("MachineOne");
					gamePlayerTwo.setText("MachineTwo");
				}
			}
		});
	}

	private void prepareActionsCreateGame(JDialog parent) {
		createGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int size = Integer.parseInt(gameSizeField.getText());
					String mode = (String) gameMode.getSelectedItem();
					try {
						System.out.println(mode);
						gomoku = new Gomoku(mode, size, 0);
						parent.dispose();
						prepareActionSetPlayersTypeAndName();
						prepareElementsGame();

					} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
							| IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
						ex.printStackTrace();
					}

				} catch (NumberFormatException ex) {
					Timer timer = new Timer(1000, new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							JOptionPane.getRootFrame().dispose();
						}
					});
					timer.setRepeats(false);
					timer.start();
					JOptionPane.showMessageDialog(null, "Invalid game size");
					timer.restart();
				}
			}
		});
	}

	private void prepareActionSetPlayersTypeAndName() {
		String players = (String) gamePlayers.getSelectedItem();
		if (players.equals("Player vs Player")) {
			try {
				gomoku.setPlayers("Normal", "Normal");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		} else if (players.equals("Player vs Machine")) {
			try {
				gomoku.setPlayers("Normal", "Expert");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		} else {
			try {
				gomoku.setPlayers("Expert", "Expert");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		gomoku.setPlayersInfo(gamePlayerOne.getText(),new Color(0,0,0), gamePlayerTwo.getText(),new Color(255,255,255));
	}

	private void closeOption() {
		int yesNo = JOptionPane.showOptionDialog(null, "Are you sure you want exit?", "Warning",
				JOptionPane.YES_NO_OPTION, JOptionPane.CANCEL_OPTION, null, null, "No");
		if (yesNo == JOptionPane.YES_OPTION) {
			setVisible(false);
			System.exit(0);
		}
	}

}

class GomokuState extends JPanel {
	private GomokuGUI gui;
	private int size;
	private int SIDE;
	Gomoku gomoku;
	private JButton[][] buttons;

	public GomokuState(GomokuGUI gui) {
		this.gui = gui;
		gomoku = gui.getGomoku();
		setBackground(new Color(190, 120, 50));
		size = Math.min((3 * GomokuGUI.WIDTH) / 4, (3 * GomokuGUI.HIGH) / 4);
		SIDE = size / gomoku.getSize();
		buttons = new JButton[gomoku.getSize()][gomoku.getSize()];
		setLayout(new GridLayout(gomoku.getSize(), gomoku.getSize(), 1, 1));
		for (int i = 0; i < gomoku.getSize(); i++) {
			for (int j = 0; j < gomoku.getSize(); j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].setContentAreaFilled(false);
				add(buttons[i][j]);
			}
		}
		prepareActionsSquareClicked();
		setPreferredSize(new Dimension(1 + SIDE * gomoku.getSize(), 1 + SIDE * gomoku.getSize()));
	}

	public void paintComponent(Graphics g) {
		gomoku = gui.getGomoku();
		super.paintComponent(g);

		for (int f = 0; f <= gomoku.getSize(); f++) {
			g.drawLine(f * SIDE, 0, f * SIDE, gomoku.getSize() * SIDE);
		}
		for (int c = 0; c <= gomoku.getSize(); c++) {
			g.drawLine(0, c * SIDE, gomoku.getSize() * SIDE, c * SIDE);
		}

	}

	private void prepareActionsSquareClicked() {
		for (int i = 0; i < gomoku.getSize(); i++) {
			for (int j = 0; j < gomoku.getSize(); j++) {
				int x = i;
				int y = j;
				buttons[i][j].addActionListener(new ActionListener() {
					private boolean circular = false;

					public void actionPerformed(ActionEvent e) {
						if (!circular) {
							Dimension size = buttons[x][y].getSize();
							if (size.width != size.height) {
								size.width = size.height = Math.min(size.width, size.height);
								buttons[x][y].setSize(size);
							}
							buttons[x][y].setContentAreaFilled(true);
							gomoku.play("Normal",x, y);
							buttons[x][y].setBackground(gomoku.getTokenColor(x, y));
							circular = true;
							String winner = gomoku.getWinner();
							if (winner != null) {
								JOptionPane.showMessageDialog(null, "The winner is: " + winner);
								gui.getContentPane().removeAll();
								gui.add(gui.start);
								gui.revalidate();
								gui.repaint();
							}
						} else {
							Timer timer = new Timer(1000, new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									JOptionPane.getRootFrame().dispose();
								}
							});
							timer.setRepeats(false);
							timer.start();
							JOptionPane.showMessageDialog(null, "The square is already visited");
							timer.restart();
						}
					}
				});
			}
		}
	}
}
