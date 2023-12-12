package domain;

import java.util.ArrayList;

/*
 * 
 * @author Juan Daniel Murcia - Mateo Forero Fuentes
 * @version 1.8.5
 */
public abstract class MachinePlayer extends Player {

	public abstract ArrayList<Object> play();

	public abstract ArrayList<Object> miniMax();
}
