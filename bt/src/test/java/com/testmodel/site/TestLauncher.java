package com.testmodel.site;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class TestLauncher {
	private final static int Users = 3;
	 
	
	@Test
	public void runner() {
		//Get start time
		long initial_time = System.currentTimeMillis();
		Vector<Long> timings = new Vector<Long>();
		
		//initialize thread Pool
		ExecutorService executor = Executors.newFixedThreadPool(Users);
		for (int i = 0; i < Users; i++) {
			Runnable ptest = new PerformanceTest(timings);
			executor.execute(ptest);
		}
		
		executor.shutdown();
		while(!executor.isTerminated()){}
		long total = 0;
		long end_time=System.currentTimeMillis();	
		for(long i : timings){
			total += i; 
		}
		
		System.out.println("Total execution time for the test: "+(end_time - initial_time));
		
		System.out.println("Average response time per page: " + total/timings.size()); 
	}
}
