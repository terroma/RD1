package simulator;

import simulator.TxRxEvent.TxRxEventType;

/**
 * 
 * Esta classe implementa o comportamento de um receptor..<br>
 * <br>
 * Possuí métodos para:<br>
 * - Iniciar a recepçao de dados {@link Sender.Data}.<br>
 * - Terminar a recepçao de dados {@link Sender.Data}.<br>
 * 
 * @author Grupo de Redes do ISCTE (20012/13)
 * 
 */

public class Receiver {
	public enum State {
		WAIT, RX
	};

	private State state;
	private double Peb;
	private int m;
	private double PROB_CORR;
	private int sent;

	public Receiver(double Peb, int DATA_SIZE) {
		state = State.WAIT;
		m = DATA_SIZE;
		this.Peb = Peb;
		PROB_CORR = Math.pow((1 - Peb), (m + 8));
	}

	/**
	 * Inicia a recepção de dados.
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
		 * Ao tempo total no sistema, delaySys é adicionado o tempo passado no
		 * sistema (tempoActual-tempoGeração) da trama que acabou de ser
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

		Simulator.addEvent(new TxRxEvent(Simulator.getClock(),
				TxRxEventType.ACK, data));

	}

	public void ACK(Data data, int maxData, double meanDataInterval) {

		state = State.WAIT;

		// Update statistics

		// Output
		String s = "[Receiver@";
		s = s + Simulator.getClock() + " ACK Data ID: " + data.getID() + "]";
		Simulator.debug(s);

		s = "" + Simulator.getClock() + "\t" + "Srx" + "\t" + data.getID()
				+ "\t";
		s = s + data.getTimeStamp() + "\t" + "-" + "\t" + "-" + "\t"
				+ Simulator.getClock() + "\t" + "-" + "\t" + "-" + "\t" + "-";
		Simulator.data(s);

		sent++;
		if (sent < maxData) {	
			// Constant Rate...
			double dataInterval = meanDataInterval;
			Simulator.addEvent(new TxRxEvent (Simulator.getClock()+dataInterval, TxRxEvent.TxRxEventType.Generate_DATA, null));
		}
		
		
		
	}
}
