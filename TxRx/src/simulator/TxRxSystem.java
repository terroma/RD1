package simulator;

import simulator.Transmiter.State;
import simulator.TxRxEvent.TxRxEventType;

public class TxRxSystem {

    // Parametros da simulação
	 static int MAX_DATA  = 100;
	
	 static int DATA_SIZE;
	 static double INTERVAL = 1.0;
	
	 static double RITMO_BINARIO;
	 static double DISTANCIA;
	 
	 static double Peb=0.1;
	
	// Contadores Estatisticos	
    static double delayQ   = 0.0;
	static double delayQtx = 0.0;
	static double delaySys = 0.0;
	

	public TxRxSystem(int DATA_SIZE, double RITMO_BINARIO, double DISTANCIA){
		this.DATA_SIZE=DATA_SIZE;
		this.RITMO_BINARIO=RITMO_BINARIO;
		this.DISTANCIA=DISTANCIA;
	}


	public void init() {
		Simulator.setLogLevel(Simulator.LogLevel.FULLDEBUG);	

		Simulator.setDataFile("data.txt");
		String s="#Simulation Results \n";
		s=s+"#Ntramas \t"+MAX_DATA+"\n";
		s=s+"#L \t\t"+DATA_SIZE+"\n";
		s=s+"#TaxaChegadas \t"+INTERVAL+"\n";
		s=s+"#Rb \t\t"+RITMO_BINARIO+"\n";
		s=s+"#d \t\t"+DISTANCIA+"\n";		
		Simulator.data(s);
		
		/* Variáveis de estado - participantes no sistema */
		Source     source   = new Source(MAX_DATA, DATA_SIZE, INTERVAL);
		Receiver   receiver = new Receiver(Peb, DATA_SIZE);
		Transmiter transmiter    = new Transmiter(RITMO_BINARIO, DISTANCIA, DATA_SIZE);
				
		/* Evento que arranca a simulação - abertura do serviço no instante 0.0 */
		TxRxEvent seed = new TxRxEvent(0.0, TxRxEventType.Generate_DATA, null);
		Simulator.addEvent(seed);

		/* Evento actual - o que se está a processar em cada ciclo */
		TxRxEvent current = (TxRxEvent)Simulator.nextEvent();

		/* Ciclo central da simulação */
		while (current!=null) {
			
			switch ((TxRxEvent.TxRxEventType)(current.type())) {
			    // Processamento de cada um dos tipos de acontecimentos
				case Generate_DATA:
					source.generateData();
					break;
					
				case Arrival_DATA:
					transmiter.arrivalData((Data)current.data());
					break;
					
				case StopTX:
					transmiter.stopTx((Data)current.data());
					break;
					
				case StartRX:
					receiver.startRX((Data)(current.data()));
					break;
				
				case StopRX:
					receiver.stopRX((Data)(current.data()));
					break;
					
				case ACK:
					receiver.ACK((Data)(current.data()), source.getMaxData(), source.getMeanDataInterval());
					break;
			}					
			/* Retira da lista o próximo acontecimento */
			current = (TxRxEvent) Simulator.nextEvent();	
		}
		Simulator.info("FIM.");
		
		// Impressão dos resultados da simulação.
		Simulator.info("STATS.");
		s = "Avg. delayQ = "+(delayQ/(double)MAX_DATA)+"\n";
		s = s+"Avg. delayQTx = "+(delayQtx/(double)MAX_DATA)+"\n";
		s = s+"Avg. delaySys = "+(delaySys/(double)MAX_DATA)+"\n";
		s = s+"Numero medio de tramas na fila, Nq = "+(delayQ/Simulator.getClock())+"\n";
		s = s+"Numero medio de tramas na fila ou a transmitir, Nqtx = "+(delayQtx/Simulator.getClock())+"\n";
		s = s+"Numero medio de tramas no sistema total, Nsistema = "+(delaySys/Simulator.getClock())+"\n";
		Simulator.info(s);
	}



		
}
