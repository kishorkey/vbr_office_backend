package com.VbrOffice.vbr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.VbrOffice.vbr.TestProj.testproject;

@SpringBootApplication
public class VbrOfficeApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(VbrOfficeApplication.class, args);
//		Counter c = new Counter();
//
//		Runnable obj1 = () -> {
////    		for(int i=1;i<=1000;i++)
//			for (int i = 1; i <= 10000; i++) {
//				c.increment();
//			}
//		};
//
//		Runnable obj2 = () -> {
////    		for(int i=1;i<=1000;i++)
//			for (int i = 1; i <= 10000; i++) {
//				c.increment();
//			}
//		};
//
//		Thread t1 = new Thread(obj1);
//		Thread t2 = new Thread(obj2);
//
//		t1.start();
//		t2.start();
//
//		t1.join();
//		t2.join();
//
//		System.out.println(c.count);

	}

}

class Counter {
	int count;

//	public void increment()
	public synchronized void increment() {
		count++;
	}
}
