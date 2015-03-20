package codeB.client.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import codeB.CommonConstants;
import codeB.client.SimulationClient;

public class SocketTestingClient extends SimulationClient{
	
	private Socket clientSocket;
	private BufferedReader in;
    private PrintWriter out;

	public SocketTestingClient(int simulationNum) {
		super(simulationNum);
		// TODO Auto-generated constructor stub
	}
	
	private void connect(){
		try {
			clientSocket = new Socket(CommonConstants.SIMULATION_SERVER_ADDRESS,CommonConstants.SERVER_PORT);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println();
			e.printStackTrace();
		}
	}
	
	public void run(){
		
		long startTime = System.currentTimeMillis();
		long endTime = startTime + CommonConstants.SERVER_SIMULATION_TIME_LENGTH;
        long currentTime = 0;
		int counter = 0;
		long lastTime = 0;
		
		while((currentTime = System.currentTimeMillis()) < endTime) {
			connect();
			if(clientSocket == null || in == null || out == null){
				System.out.println("client " + this.getSimulationNum() + " did not connect");
				return;
			}
			if(clientSocket.isClosed()){
				break;
			}
			System.out.println("Client " + this.getSimulationNum() + " has socket port " + clientSocket.getPort() + " local port " + clientSocket.getLocalPort());
//			if(currentTime - lastTime > 100){
				lastTime = currentTime;
				counter++;
				out.println("Client " + this.getSimulationNum() + " to Server: msg " + counter);
				out.flush();
				out.close();
				System.out.println("CLIENT PRINT: Client " + this.getSimulationNum() + " sent msg " + counter);
//			}
			
			try {
				String a= "";
				while((a = in.readLine())!=null){
					System.out.println("Client " + this.getSimulationNum() + " PRINT: " + in.readLine());
				}
				//clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
		}
	}
}
