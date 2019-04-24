package cn.mastercom;

import java.util.Date;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 100; i++) {
			Thread.sleep(1000);
			System.out.println("Current Time is " + new Date().toString());
		}
	}

}
