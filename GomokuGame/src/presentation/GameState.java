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

class GomokuState extends JPanel {
	private GomokuGUI gui;
	private int size;
	private int SIDE;
	private Gomoku gomoku;
	private JButton[][] buttons;
	private boolean[][] visited;

	public GomokuState(GomokuGUI gui) {
		this.gui = gui;
		gomoku = Gomoku.getGomoku();
		setBackground(new Color(190, 120, 50));
		size = Math.min((3 * GomokuGUI.WIDTH) / 4, (3 * GomokuGUI.HIGH) / 4);
		SIDE = size / gomoku.getSize();
		visited = new boolean[gomoku.getSize()][gomoku.getSize()];
		buttons = new JButton[gomoku.getSize()][gomoku.getSize()];
		setLayout(new GridLayout(gomoku.getSize(), gomoku.getSize(), 1, 1));
		for (int i = 0; i < gomoku.getSize(); i++) {
			for (int j = 0; j < gomoku.getSize(); j++) {
				buttons[i][j] = new JButton();
				visited[i][j] = false;
				buttons[i][j].setContentAreaFilled(false);
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
				if (color == null) {
					color = new Color(190, 120, 50);
					buttons[i][j].setContentAreaFilled(false);
					visited[i][j] = false;
				} else {
					visited[i][j] = true;
					buttons[i][j].setContentAreaFilled(true);
				}
				buttons[i][j].setBackground(color);
			}
		}
		revalidate();
		repaint();
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
						if (!visited[x][y]) {
							gomoku.play(gui.getMove(), x, y);
							if(gomoku.getTokenColor(x, y) != null) {
								visited[x][y] = true;
								String winner = gomoku.getWinner();
								gui.prepareElementsTokensInfo();
								if (winner != null) {
									JOptionPane.showMessageDialog(null, "The winner is: " + winner);
									gui.getContentPane().removeAll();
									gui.add(gui.start);
								}
								
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