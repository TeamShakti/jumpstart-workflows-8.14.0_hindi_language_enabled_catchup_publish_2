package com.irdeto.jumpstart.domain.taskhandler;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.commons.lang3.BooleanUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.secondstagebean.SecondStageBeanManager;
import com.irdeto.manager.workflow.WorkItemManager;

@Component("externalProcessMonitorManager")
public class ExternalProcessMonitorManagerImpl implements ExternalProcessMonitorManager {
	private static Logger logger = LoggerFactory.getLogger(ExternalProcessMonitorManagerImpl.class);

	private static final String UNIX_PROCESS_CLASS = "java.lang.UNIXProcess";

	private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
	@JsonIgnore
	private Map<Long, TimeoutExternalProcessMonitor> timeoutProcessMonitorMap = new ConcurrentHashMap<>();
	@JsonIgnore
	private Map<Long, PollingExternalProcessMonitor> pollingProcessMonitorMap = new ConcurrentHashMap<>();
	@Resource(name = "workItemManager")
	private WorkItemManager workItemManager;
	@Resource(name = "propertiesManager")
	private PropertiesManager propertiesManager;
	@Resource(name="secondStageBeanManager")
	private SecondStageBeanManager secondStageBeanManager;

	@PostConstruct
	public void setup() {
		try {
			scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(
					Integer.valueOf(propertiesManager.getProperty(JumpstartPropertyKey.COMMAND_LINE_MONITOR_POOL_SIZE_PROPERTY)),
					new RejectedExecutionHandler() {
						@Override
						public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
							logger.error("Unable to monitor command, threadpool capacity exceeded.");
						}
					});
			scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
		} catch (NumberFormatException | PropertyException e) {
			logger.debug("in error setup.. {}",e);
		}
	}

	@PreDestroy
	public void destroy() {
		if (scheduledThreadPoolExecutor != null) {
			scheduledThreadPoolExecutor.shutdown();
			try {
				if (!scheduledThreadPoolExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
					scheduledThreadPoolExecutor.shutdownNow();
				}
			} catch (InterruptedException e) {
				scheduledThreadPoolExecutor.shutdownNow();
			}
		}
	}

	@Override
	public void monitorProcess(Process process, final ExternalProcessMonitorConfiguration processMonitorConfiguration, final long workItemId) {
		// Polling
		if (process.getClass().getName().equals(UNIX_PROCESS_CLASS) && BooleanUtils.isTrue(processMonitorConfiguration.getPollingEnabled())) {
			try {
				Field pidField = process.getClass().getDeclaredField("pid");
				pidField.setAccessible(true);
				final int pid = pidField.getInt(process);
				long pollingPeriod = processMonitorConfiguration.getPollingPeriod().longValue();
				PollingExternalProcessMonitor pollingProcessMonitor = new PollingExternalProcessMonitor();
				pollingProcessMonitor.setProcessMonitorConfiguration(processMonitorConfiguration);
				pollingProcessMonitor.setWorkItemId(workItemId);
				pollingProcessMonitor.setWorkItemManager(workItemManager);
				pollingProcessMonitor.setPid(pid);
				pollingProcessMonitor.setProcessMonitorMap(pollingProcessMonitorMap);
				ScheduledFuture<?> scheduledFuture = scheduledThreadPoolExecutor.scheduleWithFixedDelay(
					pollingProcessMonitor,
					pollingPeriod,
					pollingPeriod,
					TimeUnit.SECONDS);
				pollingProcessMonitor.setScheduledFuture(scheduledFuture);
				pollingProcessMonitorMap.put(workItemId, pollingProcessMonitor);
			} catch (NoSuchFieldException | SecurityException
					| IllegalArgumentException | IllegalAccessException e) {
				logger.error("Unable to monitor process as pid could not be established.", e);
			}
		}
		// Timeout
		if (BooleanUtils.isTrue(processMonitorConfiguration.getTimeoutEnabled())) {
			long timeoutPeriod = processMonitorConfiguration.getTimeoutPeriod().longValue();
			TimeoutExternalProcessMonitor timeoutProcessMonitor = new TimeoutExternalProcessMonitor();
			timeoutProcessMonitor.setProcessMonitorConfiguration(processMonitorConfiguration);
			timeoutProcessMonitor.setWorkItemId(workItemId);
			timeoutProcessMonitor.setWorkItemManager(workItemManager);
			timeoutProcessMonitor.setProcessMonitorMap(timeoutProcessMonitorMap);
			ScheduledFuture<?> scheduledFuture = scheduledThreadPoolExecutor.schedule(
				timeoutProcessMonitor,
				timeoutPeriod,
				TimeUnit.SECONDS);
			timeoutProcessMonitor.setScheduledFuture(scheduledFuture);
			timeoutProcessMonitorMap.put(workItemId, timeoutProcessMonitor);
		}
	}

	@Override
	public void cancel(long workItemId) {
		PollingExternalProcessMonitor pollingProcessMonitor = pollingProcessMonitorMap.get(workItemId);
		if (pollingProcessMonitor != null) {
			pollingProcessMonitor.cancel();
		}
		TimeoutExternalProcessMonitor timeoutProcessMonitor = timeoutProcessMonitorMap.get(workItemId);
		if (timeoutProcessMonitor != null) {
			timeoutProcessMonitor.cancel();
		}
	}
}
