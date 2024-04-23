package controller;

import java.util.concurrent.Semaphore;

import view.Main;

public class PlayerController extends Thread {
	
	private int points;
	private Semaphore mutex;
	
	public PlayerController(Semaphore mutex) {
		points = 0;
		this.mutex = mutex;
	}
	
	@Override
	public void run() {
		while(points < 5)
			if(rollDices()) {
				System.out.println(this + ": gained 1 point");
				points++;
			}
		finish();
	}
	
	private boolean rollDices() {
		int first = (int)(Math.random() * 5) + 1;
		int second = (int)(Math.random() * 5) + 1;
		return ((first + second) == 7)||((first + second) == 11);
	}
	
	private void finish() {
		try {
			mutex.acquire();
			int reward = 0;
			switch(Main.rewards) {
			case 1:
				reward = 5000;
				break;
			case 2:
				reward = 4000;
				break;
			case 3:
				reward = 3000;
			}
			System.out.println(this + ": finished in " + Main.rewards  + " position. Reward: " + reward);
			Main.rewards++;
			mutex.release();
		} catch (InterruptedException e) {
			mutex.release();
		}
	}

	@Override
	public String toString() {
		return getName().replace("Thread-", "Player ");
	}

}
