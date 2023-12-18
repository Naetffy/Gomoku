package presentation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.Gomoku;

public class TimeGUI extends JPanel implements Subscriber<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5L;
	private double timePlayerOne;
	private double timePlayerTwo;
	private Subscription subscription;
	private JLabel c1;
	private JLabel c2;
	
	public TimeGUI() {
		Gomoku.getGomoku().getTime().subscribe(this);
		setLayout(new GridLayout(0,2,50,0));
		c1 = new JLabel("Time player one: "+ timePlayerOne);
		c2 = new JLabel("Time player two: "+ timePlayerTwo);
		add(c1);
		add(c2);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		c1.setText("Time player one: "+ (timePlayerOne/1000));
		c2.setText("Time player two: "+ (timePlayerTwo/1000));
	}
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
		repaint();
	}

	@Override
	public void onNext(Integer item) {
		subscription.request(1); 
		if (Gomoku.getGomoku().getTurn()%2 == 0) {
			timePlayerOne = item;
		}
		else {
			timePlayerTwo = item;
		}
		repaint();
	}

	@Override
	public void onError(Throwable throwable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onComplete() {
		// TODO Auto-generated method stub
		
	}

}
