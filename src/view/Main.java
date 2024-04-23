package view;

import java.util.concurrent.Semaphore;

import controller.PlayerController;

public class Main {
	
	public static int rewards = 1;

	public static void main(String[] args) {
		
		Semaphore mutex = new Semaphore(1);
		PlayerController[] players = new PlayerController[10];
		
		for(int i = 0; i < 10; i++) 
			players[i] = new PlayerController(mutex);
			
		for(PlayerController player : players)
			player.start();
	}

}
