package codeB.main;

import java.util.ArrayList;
import java.util.List;

import codeB.client.TradingClient;
import codeB.client.impl.SocketTestingClient;
import codeB.server.SimulationServer;

public class SimulationMain {
	
	private List<TradingClient> clientList;
	
	public void runSimulation(){
		prepareClients();
		startServer();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startClients();
	}
	
	public void prepareClients(){
		clientList = new ArrayList<TradingClient>();
		//change here to put different strategies into the simulation
		for(int i = 1; i <= 1; i++){
			//make 10 socket testing client
			TradingClient client = new SocketTestingClient(i);
			clientList.add(client);
		}
	}
	
	private void startServer() {
		Thread sThread = new Thread(new SimulationServer());
		sThread.start();
	}

	private void startClients() {
		int num = 0;
		for(TradingClient client: clientList){
			Thread cTread = new Thread(client);
			cTread.start();
			num++;
			System.out.println("CLIENT " + num + " started ");
		}	
	}

	public static void main(String [] args){
		new SimulationMain().runSimulation();
	}
}
