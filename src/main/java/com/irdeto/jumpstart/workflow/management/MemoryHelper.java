package com.irdeto.jumpstart.workflow.management;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class MemoryHelper {
	public static String getMemoryState() {
		MemoryMXBean memoryMxBean = ManagementFactory.getMemoryMXBean();
		MemoryUsage heapMemoryUsage = memoryMxBean.getHeapMemoryUsage();
		MemoryUsage nonHeapMemoryUsage = memoryMxBean.getNonHeapMemoryUsage();
		StringBuffer usageString = new StringBuffer();
		usageString.append("\nHeap:\tCommitted:\t").append(heapMemoryUsage.getCommitted())
			.append("\tInit:\t").append(heapMemoryUsage.getInit())
			.append("\tMax:\t").append(heapMemoryUsage.getMax())
			.append("\tUsed:\t").append(heapMemoryUsage.getUsed());
		usageString.append("\nNonHeap:\tCommitted:\t").append(nonHeapMemoryUsage.getCommitted())
			.append("\tInit:\t").append(nonHeapMemoryUsage.getInit())
			.append("\tMax:\t").append(nonHeapMemoryUsage.getMax())
			.append("\tUsed:\t").append(nonHeapMemoryUsage.getUsed());
		
		return usageString.toString();
	}
	
}
