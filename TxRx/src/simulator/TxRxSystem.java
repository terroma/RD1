package simulator;

import simulator.Transmiter.State;
import simulator.TxRxEvent.TxRxEventType;

public class TxRxSystem {

<<<<<<< HEAD
    // Parametros da simula����o
	 static int MAX_DATA  = 10;
	 
=======
    // Parametros da simulação
	 static int MAX_DATA  = 10;
	
>>>>>>> FETCH_HEAD
	 static int DATA_SIZE;
	 static double INTERVAL = 1.0;
	
	 static double RITMO_BINARIO;
	 static double DISTANCIA;
	 
	 static double Peb=0.1;
	 
<<<<<<< HEAD
=======
	 double tempoInicial=Simulator.getClock();
	 double asd;
	 
>>>>>>> FETCH_HEAD
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
		
		/* Vari��veis de estado - participantes no sistema */
		Source     source   = new Source(MAX_DATA, DATA_SIZE, INTERVAL);
		Receiver   receiver = new Receiver(Peb);
		Transmiter transmiter    = new Transmiter(RITMO_BINARIO, DISTANCIA, DATA_SIZE);
				
		/* Evento que arranca a simula����o - abertura do servi��o no instante 0.0 */
		TxRxEvent seed = new TxRxEvent(0.0, TxRxEventType.Generate_DATA, null);
		Simulator.addEvent(seed);

		/* Evento actual - o que se est�� a processar em cada ciclo */
		TxRxEvent current = (TxRxEvent)Simulator.nextEvent();
		
		/* Ciclo central da simula����o */
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
<<<<<<< HEAD
					transmiter.ACK((Data)(current.data()), source.getMaxData());
					System.out.println(source.getMeanDataInterval());
=======
					transmiter.ACK((Data)(current.data()), source.getMaxData(), source.getMeanDataInterval());
>>>>>>> FETCH_HEAD
					break;
					
				case Timeout:
					transmiter.Timeout((Data)(current.data()));
					break;
			}					
			/* Retira da lista o pr��ximo acontecimento */
			current = (TxRxEvent) Simulator.nextEvent();	
		}
		
		Simulator.info("FIM.");
		
		// Impress��o dos resultados da simula����o.
		Simulator.info("STATS.");
		s = "Avg. delayQ = "+(delayQ/(double)MAX_DATA)+"\n";
		s = s+"Avg. delayQTx = "+(delayQtx/(double)MAX_DATA)+"\n";
		s = s+"Avg. delaySys = "+(delaySys/(double)MAX_DATA)+"\n";
		s = s+"Numero medio de tramas na fila, Nq = "+(delayQ/Simulator.getClock())+"\n";
		s = s+"Numero medio de tramas na fila ou a transmitir, Nqtx = "+(delayQtx/Simulator.getClock())+"\n";
		s = s+"Numero medio de tramas no sistema total, Nsistema = "+(delaySys/Simulator.getClock())+"\n";
<<<<<<< HEAD
//		s = s+"U = "+((MAX_DATA*(DATA_SIZE/RITMO_BINARIO))/(Simulator.getClock()/1000));
		s = s+"U = "+((MAX_DATA*(DATA_SIZE/RITMO_BINARIO))/(Simulator.getClock()));
=======
		s = s+"U = "+(1/(1+2*(MAX_DATA*(DATA_SIZE/RITMO_BINARIO))/Simulator.getClock()));
>>>>>>> FETCH_HEAD
		System.out.println("\nMax_Data: "+MAX_DATA+"\nData_Size: "+DATA_SIZE+"\nRitmo: "+RITMO_BINARIO+"\n"+Simulator.getClock());
		Simulator.info(s);
	}



		
}
