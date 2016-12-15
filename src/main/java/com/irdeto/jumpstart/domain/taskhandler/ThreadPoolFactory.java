package com.irdeto.jumpstart.domain.taskhandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.manager.task.BeanUtil;

public class ThreadPoolFactory {
	private static final Logger logger = LoggerFactory.getLogger(ThreadPoolFactory.class);
	
	private static ThreadPoolExecutor threadPool;
	
	private static final String LOCK = "TaskHandlerThreadPool";

	public static ThreadPoolExecutor getThreadPoolExecutor() throws Exception {
		setupThreadPool();
		return threadPool;
	}

	private static void setupThreadPool() throws Exception {
		synchronized (LOCK) {
			if (ThreadPoolFactory.threadPool == null) {
				int threadPoolSize = Integer.parseInt(BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.TASK_HANDLER_THREAD_POOL_SIZE_KEY));
				int threadPoolMaxSize = Integer.parseInt(BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.TASK_HANDLER_THREAD_POOL_MAX_SIZE_KEY));
				int threadPoolKeepAliveTime = Integer.parseInt(BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.TASK_HANDLER_THREAD_POOL_KEEP_ALIVE_TIME_KEY));
				if (threadPoolMaxSize < threadPoolSize) {
					throw new Exception("Property " + JumpstartPropertyKey.TASK_HANDLER_THREAD_POOL_MAX_SIZE_KEY + " should be >= property " + JumpstartPropertyKey.TASK_HANDLER_THREAD_POOL_SIZE_KEY + "."
							+ "  Actual " + JumpstartPropertyKey.TASK_HANDLER_THREAD_POOL_MAX_SIZE_KEY + "=" + threadPoolMaxSize + ", " + JumpstartPropertyKey.TASK_HANDLER_THREAD_POOL_SIZE_KEY + "=" + threadPoolSize);
				}
				SynchronousQueue<Runnable> threadPoolQueue = new SynchronousQueue<Runnable>(true);
				ThreadPoolFactory.threadPool = new ThreadPoolExecutor(threadPoolSize, threadPoolMaxSize, threadPoolKeepAliveTime, TimeUnit.SECONDS, threadPoolQueue);
				threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
			}
		}
	}
	public static <T> void executeLoop(List<? extends T> list, Object enclosingObject, Class<? extends ItemRunnable<T>> runnableClass, ConcurrentHashMap<Integer, Object> resultMap) throws Exception {
		executeLoop(list, enclosingObject, runnableClass, null, resultMap);
	}
	
	public static <T> void executeLoop(List<? extends T> list, Object enclosingObject, Class<? extends ItemRunnable<T>> runnableClass, ConcurrentHashMap<String, Object> dataMap, ConcurrentHashMap<Integer, Object> resultMap) throws Exception {
		final List<Throwable> throwableList = Collections.synchronizedList(new ArrayList<Throwable>());
		final CountDownLatch latch = new CountDownLatch(list.size());
		for (int i = 0; i < list.size(); i++) {
			T object = list.get(i);
			final ItemRunnable<T> runnable = runnableClass.getDeclaredConstructor(enclosingObject.getClass()).newInstance(enclosingObject);
			runnable.setItem(object);
			runnable.setPosition(i);
			runnable.setResultMap(resultMap);
			runnable.setDataMap(dataMap);
			if (throwableList.size() == 0 && !runnable.breakingCondition()) {
				ThreadPoolFactory.getThreadPoolExecutor().execute(new Runnable() {
					@Override
					public void run() {
						try {
							runnable.run();
						} catch (Throwable t) {
							throwableList.add(t);
						} finally {
							latch.countDown();
						}
					}
				});
			} else {
				latch.countDown();
			}
		}
		latch.await();
		if (!throwableList.isEmpty()) {
			for (Throwable throwable: throwableList) {
				logger.error("Exception:", throwable);
			}
			throw new Exception(throwableList.get(0));
		}
	}
	
	public static abstract class ItemRunnable<T> {
		private T item;
		private int position;
		private ConcurrentHashMap<String, Object> dataMap;
		private ConcurrentHashMap<Integer, Object> resultMap;

		public T getItem() {
			return item;
		}

		public void setItem(T item) {
			this.item = item;
		}
		
		public abstract void run() throws Throwable;
		
		public boolean breakingCondition() {
			return false;
		}

		public int getPosition() {
			return position;
		}

		public void setPosition(int position) {
			this.position = position;
		}

		public ConcurrentHashMap<Integer, Object> getResultMap() {
			return resultMap;
		}

		public void setResultMap(ConcurrentHashMap<Integer, Object> resultMap) {
			this.resultMap = resultMap;
		}

		public ConcurrentHashMap<String, Object> getDataMap() {
			return dataMap;
		}

		public void setDataMap(ConcurrentHashMap<String, Object> dataMap) {
			this.dataMap = dataMap;
		}
	}
}
