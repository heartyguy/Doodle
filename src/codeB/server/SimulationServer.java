package codeB.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import codeB.CommonConstants;

public class SimulationServer implements Runnable{
	ServerDataStore dataStore;

	private ServerSocket serverSocket;

	/**
	 * This executor service has 10 threads. 
	 * So it means your server can process max 10 concurrent requests.
	 */
	private ExecutorService executorService = Executors.newFixedThreadPool(CommonConstants.SERVER_THREAD_POOL_SIZE);        
	
	public void runServer() {        
		int serverPort = CommonConstants.SERVER_PORT;
		try {
			System.out.println("Starting Server");
			serverSocket = new ServerSocket(serverPort); 
			serverSocket.setPerformancePreferences(1, 2, 0);
			long startTime = System.currentTimeMillis();
			long endTime = startTime + CommonConstants.SERVER_SIMULATION_TIME_LENGTH;
			System.out.println("start time + " + startTime);
			System.out.println("end time + " + endTime);
			
			while(System.currentTimeMillis() < endTime) {
				System.out.println("Waiting for request");
				try {
					Socket s = serverSocket.accept();
					System.out.println("Processing request");
					executorService.submit(new ServiceRequest(s));
				} catch(IOException ioe) {
					System.out.println("Error accepting connection");
					ioe.printStackTrace();
				}
			}
			stopServer();
		}catch(IOException e) {
			System.out.println("Error starting Server on "+serverPort);
			e.printStackTrace();
		}
	}

	//Call the method when you want to stop your server
	private void stopServer() {
		//Stop the executor service.
		System.out.println("stopping server....");
		executorService.shutdownNow();
		try {
			//Stop accepting requests.
			serverSocket.close();
		} catch (IOException e) {
			System.out.println("Error in server shutdown");
			e.printStackTrace();
		}
		System.out.println("Server is stopped");
		System.exit(0);
	}

	class ServiceRequest implements Runnable {

		private Socket socket;
		private PrintWriter out;
		private BufferedReader in;
		private int msgCounter;
		
		public ServiceRequest(Socket connection) {
			this.socket = connection;
		}

		public void run() {
			try {
				in = new BufferedReader(
				        new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
            
			if(socket == null || in == null|| out == null){
				System.out.println("connection failed");
				return;
			}
			System.out.println("connection succeeded");
			while(true){
				//System.out.println("in server while");
				if(socket.isClosed()){
					break;
				}
				String req = null;
				try {
					req = in.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				//System.out.println("after readline block");
				if(req != null){
					msgCounter++;
					System.out.println("SERVER PRINT : RECEIVED msg # " + msgCounter + " '" + req + "'");
					out.println("msg # " + msgCounter + " '" + req + "'");
				}
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
		}
		
		public void interrupt(){
			//clean up
			try {
				socket.close();
			}catch(IOException ioe) {
				System.out.println("clean up closing client connection");
			}
		}
	}

	@Override
	public void run() {
		runServer();
	}
}

