package domain;

import java.util.ArrayList;

/*
 * 
 * @author Juan Daniel Murcia - Mateo Forero Fuentes
 * @version 1.8.5
 */
public abstract class MachinePlayer extends Player {
	protected int timeRetard = 1000;
	public abstract int[] play();

	public abstract int[] miniMax();
}
