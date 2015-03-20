package codeB.client;

import codeB.client.strategy.BaseStrategy;

public abstract class TradingClient implements Runnable{
	protected BaseStrategy strat;
	
	public void run() {
	}
	
	public BaseStrategy getStrat() {
		return strat;
	}

	public void setStrat(BaseStrategy strat) {
		this.strat = strat;
	}

	
}
