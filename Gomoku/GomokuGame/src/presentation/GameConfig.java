package presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import domain.Gomoku;
import domain.GomokuException;


/**
 * The GameConfig class represents a dialog for configuring the settings of a Gomoku game,
 * including board size, game mode, players, and other options.
 */
public class GameConfig extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private int WIDTH;
	private GomokuGUI gui;
	Gomoku gomoku;
	
	private JComboBox<String> gameMode;
	private JTextField limitTokens;
	private JTextField limitTime;

	private JComboBox<String> gamePlayers;
	private JTextField gamePlayerOne;
	private JTextField gamePlayerTwo;

	private JTextField gameSizeField;
	private JSlider gameSlider;
	private JButton createGame;
	
	
	private JButton selectColorPlayerOne;
	private JButton selectColorPlayerTwo;
	private Color playerOneColor;
	private Color playerTwoColor;
	
	
	/**
     * Constructs a GameConfig dialog with the specified screen size, width, height, and parent GUI.
     *
     * @param screenSize The screen size.
     * @param WIDTH      The width of the dialog.
     * @param HIGH       The height of the dialog.
     * @param gui        The parent GomokuGUI.
     */
	public GameConfig(Dimension screenSize,int WIDTH, int HIGH,GomokuGUI gui) {
		this.gui = gui;
		this.WIDTH = WIDTH;
		this.setSize(new Dimension(WIDTH / 2, HIGH / 2));
		int x = (screenSize.width - WIDTH / 2) / 2;
		int y = (screenSize.height - HIGH / 2) / 2;
		selectColorPlayerOne = new JButton("Select Color for Player One");
	    selectColorPlayerTwo = new JButton("Select Color for Player Two");
		this.setLocation(x, y);
		this.add(prepareElementsStartConfig());
		this.setVisible(true);
		prepareActionsCreateGame(this);
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
		JButton selectColorPlayerOne = new JButton("Select Color for Player One");
	    JButton selectColorPlayerTwo = new JButton("Select Color for Player Two");
	    configGamePlayers.add(selectColorPlayerOne);
	    configGamePlayers.add(selectColorPlayerTwo);
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

	@SuppressWarnings("unused")
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

	
	/**
     * Sets up the action listeners for the game mode combo box to dynamically adjust the text fields
     * based on the selected game mode.
     */
	private void prepareActionsStartConfigGameMode() {
		gameMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
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
	
	
	/**
     * Sets up the action listeners for the game players combo box to dynamically adjust the text fields
     * based on the selected game players configuration.
     */
	private void prepareActionsStartConfigGamePlayers() {
	    gamePlayers.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            @SuppressWarnings("unchecked")
	            JComboBox<String> combo = (JComboBox<String>) e.getSource();
	            String selection = (String) combo.getSelectedItem();
	            if (selection.equals("Player vs Player")) {
	                gamePlayerOne.setEditable(true);
	                gamePlayerTwo.setEditable(true);
	                gamePlayerOne.setText("Name player one");
	                gamePlayerTwo.setText("Name player two");
	            } else if (selection.equals("Player vs Machine")) {
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
	            selectColorPlayerOne.addActionListener(evt -> {
	                playerOneColor = showColorSelectionDialog("Select Color for Player One", playerOneColor);
	                selectColorPlayerOne.setBackground(playerOneColor);
	            });

	            selectColorPlayerTwo.addActionListener(evt -> {
	                playerTwoColor = showColorSelectionDialog("Select Color for Player Two", playerTwoColor);
	                selectColorPlayerTwo.setBackground(playerTwoColor);
	            });
	            prepareActionSetPlayersTypeAndName(playerOneColor, playerTwoColor);
	        }
	    });
	}

	
	private Color showColorSelectionDialog(String title, Color initialColor) {
		JColorChooser colorChooser = new JColorChooser(initialColor);
	    JDialog colorDialog = JColorChooser.createDialog(GameConfig.this, title, true, colorChooser,
	            e -> {}, null);
	    colorDialog.setVisible(true);
	    return colorChooser.getColor();

	}

	
	/**
     * Sets up the action listener for the "Create the game" button to create a Gomoku game with the specified
     * configurations and start the game.
     *
     * @param parent The parent JDialog.
     */
	private void prepareActionsCreateGame(JDialog parent) {
		createGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int size = Integer.parseInt(gameSizeField.getText());
					String mode = (String) gameMode.getSelectedItem();
					gomoku = new Gomoku(mode, size);
					parent.dispose();
					prepareActionSetPlayersTypeAndName(playerOneColor, playerTwoColor);
					gui.prepareElementsGame();
				} catch (NumberFormatException | GomokuException ex ) {
					Timer timer = new Timer(1000, new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							JOptionPane.getRootFrame().dispose();
						}
					});
					timer.setRepeats(false);
					timer.start();
					JOptionPane.showMessageDialog(null, "Invalid game size");
					timer.restart();
				} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
						| IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
					ex.printStackTrace();
				} 
			}
		});
	}
	
	
	 /**
     * Sets up the players' types, names, and other configurations based on the selected options.
	 * @param playerTwoColor2 
	 * @param playerOneColor2 
     */
	private void prepareActionSetPlayersTypeAndName(Color playerOneColor2, Color playerTwoColor2) {
		int defaultNum = Integer.parseInt(gameSizeField.getText())*Integer.parseInt(gameSizeField.getText());;
		try {
			gomoku.setLimits(Integer.parseInt(limitTokens.getText()),20);
		} catch (NumberFormatException e) {
			gomoku.setLimits((defaultNum*defaultNum)/2,20);
		}
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
		gomoku.setEspecialInfo(100, 5);
	}
}