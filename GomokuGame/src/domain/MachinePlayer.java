package domain;

import java.util.ArrayList;

public abstract class MachinePlayer extends Player {

	public abstract ArrayList<Object> play();

	public abstract ArrayList<Object> miniMax();
}
