package codeB.client;

public abstract class SimulationClient extends TradingClient {
	protected int simulationNum;
	
	public SimulationClient(int simulationNum) {
		super();
		this.simulationNum = simulationNum;
	}

	public int getSimulationNum() {
		return simulationNum;
	}

	public void setSimulationNum(int simulationNum) {
		this.simulationNum = simulationNum;
	}
}
