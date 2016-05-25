package es.ucm.fdi.tp.assignment6.test;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.assignment6.Main;

public class ClientRunner {
	
	private static final int CLIENTS = 2;

	public static void main(String[] args) {
		String[] argsClient = {"-am", "client"};
		List<Thread> clients = new ArrayList<Thread>();
		
		for (int i = 0; i < CLIENTS; ++i){
			Thread t = new Thread(new Runnable(){

				@Override
				public void run() {
					Main.main(argsClient);
				}				
			});
			t.start();
			clients.add(t);
		}
		
		for (Thread t : clients){
			try {
				t.join();
			} catch (InterruptedException e) {
			}
		}
	}

}
