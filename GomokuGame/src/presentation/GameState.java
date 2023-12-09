package presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicButtonListener;

import domain.Gomoku;
import domain.MineSquare;
import domain.NormalSquare;
import domain.Square;
import domain.TeleportSquare;

class GomokuState extends JPanel {
	private GomokuGUI gui;
	private int size;
	private int SIDE;
	private Gomoku gomoku;
	private JButton[][] buttons;

	public GomokuState(GomokuGUI gui) {
		this.gui = gui;
		gomoku = Gomoku.getGomoku();
		size = Math.min((3 * GomokuGUI.WIDTH) / 4, (3 * GomokuGUI.HIGH) / 4);
		SIDE = size / gomoku.getSize();
		buttons = new JButton[gomoku.getSize()][gomoku.getSize()];
		setLayout(new GridLayout(gomoku.getSize(), gomoku.getSize(), 1, 1));
		for (int i = 0; i < gomoku.getSize(); i++) {
			for (int j = 0; j < gomoku.getSize(); j++) {
				buttons[i][j] = new JButton();
				add(buttons[i][j]);
			}
		}
		prepareActionsSquareClicked();
		setPreferredSize(new Dimension(1 + SIDE * gomoku.getSize(), 1 + SIDE * gomoku.getSize()));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int f = 0; f <= gomoku.getSize(); f++) {
			g.drawLine(f * SIDE, 0, f * SIDE, gomoku.getSize() * SIDE);
		}
		for (int c = 0; c <= gomoku.getSize(); c++) {
			g.drawLine(0, c * SIDE, gomoku.getSize() * SIDE, c * SIDE);
		}
		for (int i = 0; i < gomoku.getSize(); i++) {
			for (int j = 0; j < gomoku.getSize(); j++) {
				Color color = gomoku.getTokenColor(i, j);
				Square sq = gomoku.getSquare(i,j);
				if (color == null) {
					if (sq instanceof NormalSquare) {
						color = new Color(190, 120, 50);
					}
					else if(sq instanceof MineSquare) {
						color = new Color(255, 0, 0);
					}
					else if(sq instanceof TeleportSquare) {
						color = new Color(128, 0, 128);
					}
					else{
						color = new Color(255, 215, 0);
					}
				} 
				buttons[i][j].setBackground(color);
			}
		}
	}

	private void prepareActionsSquareClicked() {
		for (int i = 0; i < gomoku.getSize(); i++) {
			for (int j = 0; j < gomoku.getSize(); j++) {
				int x = i;
				int y = j;
				for (MouseListener m : buttons[i][j].getMouseListeners()) {
					buttons[i][j].removeMouseListener(m);
				}
				buttons[i][j].addMouseListener(new BasicButtonListener(null) {
					public void mouseEntered(MouseEvent e) {}
					public void mouseExited(MouseEvent e) {}
				});
				buttons[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int init = gomoku.getTurn();
						System.out.println(init);
						Color color = gomoku.getTokenColor(x, y);
						if (color == null) {
							gomoku.play(gui.getMove(), x, y);
							String winner = gomoku.getWinner();
							System.out.println(gomoku.getTurn());
							if (init != gomoku.getTurn()) {
								gui.prepareElementsTokensInfo();
							}
							if (winner != null) {
								repaint();
								JOptionPane.showMessageDialog(null, "The winner is: " + winner);
								gui.getContentPane().removeAll();
								gui.add(gui.start);
								
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
						repaint();
					}
				});

			}
		}
	}
}