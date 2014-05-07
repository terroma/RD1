package simulator;

public class Source {
	public enum State { IDLE };
	
	private State state;
	private int maxData;
	private double meanDataInterval;
	private int meanDataSize;
	private int sent = 0; // Contador do número de tramas enviadas
	
	public Source(int max_data, int size, double interval) {
		maxData = max_data;
		meanDataInterval = interval;
		meanDataSize = size;
		
		state = State.IDLE;
	}
	public int getMaxData(){
		return maxData;
	}
	public double getMeanDataInterval(){
		return meanDataInterval;
	}
	
	public void generateData() {	
		
		// Constant Size..
		int dataSize = meanDataSize;
		Data data = new Data(Simulator.getClock(), sent, dataSize);	
		sent++;
		TxRxEvent newEvent = new TxRxEvent (Simulator.getClock()+0.0,
				                  TxRxEvent.TxRxEventType.Arrival_DATA, data);
		Simulator.addEvent(newEvent);
		
		
		
		// Output
		String s = "[Source@";
		s=s+Simulator.getClock()+" Generating Data ID: "+data.getID()+"]";
		Simulator.debug(s);
		
        s=""+Simulator.getClock()+"\t"+"G"+"\t"+data.getID()+"\t";
        s=s+data.getTimeStamp()+"\t"+"-"+"\t"+"-"+"\t"+"-"+"\t"+"-"+"\t"+"-"+"\t"+"-";
		Simulator.data(s);	
	}
}
