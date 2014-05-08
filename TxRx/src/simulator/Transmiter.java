package simulator;

import java.util.LinkedList;

import simulator.Receiver.State;
import simulator.TxRxEvent.TxRxEventType;

public class Transmiter {
	public enum State {
		IDLE, TX
	};

	double Rb;
	double d;
	int L;
	static double vp = 200000000.0; // vp = 2e8 m/s.
	State state;
	LinkedList<Data> queue;
	private int sent;
	private TxRxEvent timeout;

	Transmiter(double binaryRate, double length, int DATA_SIZE) {

		Rb = binaryRate;
		d = length;
		L = DATA_SIZE;

		queue = new LinkedList<Data>();
		state = State.IDLE;
	}

	public void arrivalData(Data data) {

		// Output
		String s = "";
		s = "" + Simulator.getClock() + "\t" + "A" + "\t" + data.getID() + "\t";
		int tx = 0;
		if (state == State.TX)
			tx = 1;
		s = s + data.getTimeStamp() + "\t" + Simulator.getClock() + "\t" + "-"
				+ "\t" + "-" + "\t" + "-" + "\t" + queue.size() + "\t"
				+ (queue.size() + tx);
		Simulator.data(s);

		// Sec????o a completar
		// ta ito
		if (state == State.IDLE) {
			startTx(data);
		} else if (state == State.TX) {
			queue.add(data);
		}

	}

	public void startTx(Data data) {
		// Update statistics
		TxRxSystem.delayQ += Simulator.getClock() - data.getTimeStamp();

		// Output
		String s = "[Transmiter@";
		s = s + Simulator.getClock() + " Start TX Data ID: " + data.getID()
				+ "]";
		Simulator.debug(s);
		s = "" + Simulator.getClock() + "\t" + "Stx" + "\t" + data.getID()
				+ "\t";
		s = s + data.getTimeStamp() + "\t" + Simulator.getClock() + "\t" + "-"
				+ "\t" + "-" + "\t" + "-" + "\t" + queue.size() + "\t"
				+ (queue.size() + 1);
		Simulator.data(s);

		// Sec????o a completar
		state = State.TX;

		Simulator.addEvent(new TxRxEvent(Simulator.getClock()
				+ (data.getSize() / Rb), TxRxEventType.StopTX, data));

		Simulator.addEvent(new TxRxEvent(Simulator.getClock() + (d / vp),
				TxRxEventType.StartRX, data));

	}

	public void stopTx(Data data) {
		if (state == State.TX) {
			// Update statistics
			TxRxSystem.delayQtx += Simulator.getClock() - data.getTimeStamp();

			// Output
			String s = "";
			s = "" + Simulator.getClock() + "\t" + "Etx" + "\t" + data.getID()
					+ "\t";
			s = s + data.getTimeStamp() + "\t" + "-" + "\t"
					+ Simulator.getClock() + "\t" + "-" + "\t" + "-" + "\t"
					+ queue.size() + "\t" + queue.size();
			Simulator.data(s);
			s = "[Transmiter@";
			s = s + Simulator.getClock() + " Stop TX Data ID: " + data.getID()
					+ "]";
			Simulator.debug(s);

			// Sec????o a completar

			double Trtt = (d / vp) + Simulator.getClock() - data.getTimeStamp();
			double Tout = (d / vp + L / Rb) * 1.01;

			
			
			Simulator.addEvent(new TxRxEvent(Simulator.getClock() + (d / vp),
					TxRxEventType.StopRX, data));

			timeout = new TxRxEvent(Simulator.getClock() + 0.35,
					TxRxEventType.Timeout, data);
			Simulator.addEvent(timeout);

			if (queue.isEmpty()) {
				state = State.IDLE;
			} else {
				startTx(queue.poll());
			}

		} else {
			Simulator.debug("ERROR - not a valid state for action stopTx");
			System.exit(1);
		}
	}

	public void Timeout(Data data) {
		startTx(data);
	}

	public void ACK(Data data, int maxData) {

		Simulator.removeEvent(timeout);
		state = State.IDLE;

		// Update statistics

		// Output
		String s = "[Transmiter@";
		s = s + Simulator.getClock() + " ACK Data ID: " + data.getID() + "]";
		Simulator.debug(s);

		s = "" + Simulator.getClock() + "\t" + "Srx" + "\t" + data.getID()
				+ "\t";
		s = s + data.getTimeStamp() + "\t" + "-" + "\t" + "-" + "\t"
				+ Simulator.getClock() + "\t" + "-" + "\t" + "-" + "\t" + "-";
		Simulator.data(s);

		sent++;
		if (sent < maxData) {
			
			Simulator.addEvent(new TxRxEvent(
							Simulator.getClock(),
							TxRxEvent.TxRxEventType.Generate_DATA, null));
		}
	}
}
