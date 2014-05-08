package simulator;

import java.util.Random;

import simulator.TxRxEvent.TxRxEventType;

/**
 * 
 * Esta classe implementa o comportamento de um receptor..<br>
 * <br>
 * Possu�� m��todos para:<br>
 * - Iniciar a recep��ao de dados {@link Sender.Data}.<br>
 * - Terminar a recep��ao de dados {@link Sender.Data}.<br>
 * 
 * @author Grupo de Redes do ISCTE (20012/13)
 * 
 */

public class Receiver {
	public enum State {
		WAIT, RX
	};

	private State state;
	private double PROB_ERROR;

	public Receiver(double Peb) {
		state = State.WAIT;
		
		PROB_ERROR = 1 - Math.pow((1 - Peb), (26 + 8));
	}

	/**
	 * Inicia a recep����o de dados.
	 * 
	 * @param Os
	 *            dados recebidos.
	 */
	public void startRX(Data data) {

		state = State.RX;

		// Update statistics

		// Output
		String s = "[Receiver@";
		s = s + Simulator.getClock() + " Start RX Data ID: " + data.getID()
				+ "]";
		Simulator.debug(s);

		s = "" + Simulator.getClock() + "\t" + "Srx" + "\t" + data.getID()
				+ "\t";
		s = s + data.getTimeStamp() + "\t" + "-" + "\t" + "-" + "\t"
				+ Simulator.getClock() + "\t" + "-" + "\t" + "-" + "\t" + "-";
		Simulator.data(s);

	}

	public void stopRX(Data data) {

		state = State.WAIT;

		// Update statistics
		/*
		 * Ao tempo total no sistema, delaySys �� adicionado o tempo passado no
		 * sistema (tempoActual-tempoGera����o) da trama que acabou de ser
		 * recebida
		 */
		TxRxSystem.delaySys += Simulator.getClock() - data.getTimeStamp();

		// Output
		String s = "[Receiver@";
		s = s + Simulator.getClock() + " Stop RX Data ID (State=" + state
				+ "): " + data.getID() + "]";
		Simulator.debug(s);

		s = "" + Simulator.getClock() + "\t" + "Erx" + "\t" + data.getID()
				+ "\t";
		s = s + data.getTimeStamp() + "\t" + "-" + "\t" + "-" + "\t" + "-"
				+ "\t" + Simulator.getClock() + "\t" + "-" + "\t" + "-";
		Simulator.data(s);

		if (Successful()){
			Simulator.addEvent(new TxRxEvent(Simulator.getClock(),
					TxRxEventType.ACK, data));
		}

	}
// 
	private boolean Successful() {
		Random rand = new Random();
		double randInt = rand.nextDouble();
		if (randInt < PROB_ERROR) {
			System.out.println("\n !!!!!  FODEU  !!!!!  " + randInt+"\n");
			return false;
		}
		return true;
	}
}
