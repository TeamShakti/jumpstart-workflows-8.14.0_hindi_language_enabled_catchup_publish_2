package com.irdeto.jumpstart.domain.taskhandler;


public class ExternalProcessMonitorConfiguration {
	private boolean pollingEnabled;
	private Integer pollingPeriod;
	private String pollingCommand;
	private boolean timeoutEnabled;
	private Integer timeoutPeriod;
	
	public ExternalProcessMonitorConfiguration() {
		super();
	}
	
	public ExternalProcessMonitorConfiguration(boolean pollingEnabled,
			String pollingCommand, Integer pollingPeriod,
			boolean timeoutEnabled, Integer timeoutPeriod) {
		super();
		setPollingEnabled(pollingEnabled);
		setPollingPeriod(pollingPeriod);
		setPollingCommand(pollingCommand);
		setTimeoutEnabled(timeoutEnabled);
		setTimeoutPeriod(timeoutPeriod);
	}

	public boolean getPollingEnabled() {
		return pollingEnabled;
	}

	public void setPollingEnabled(boolean pollingEnabled) {
		this.pollingEnabled = pollingEnabled;
	}

	public Integer getPollingPeriod() {
		return pollingPeriod;
	}

	public void setPollingPeriod(Integer pollingPeriod) {
		this.pollingPeriod = pollingPeriod;
	}

	public String getPollingCommand() {
		return pollingCommand;
	}

	public void setPollingCommand(String pollingCommand) {
		this.pollingCommand = pollingCommand;
	}

	public boolean getTimeoutEnabled() {
		return timeoutEnabled;
	}

	public void setTimeoutEnabled(boolean timeoutEnabled) {
		this.timeoutEnabled = timeoutEnabled;
	}

	public Integer getTimeoutPeriod() {
		return timeoutPeriod;
	}

	public void setTimeoutPeriod(Integer timeoutPeriod) {
		this.timeoutPeriod = timeoutPeriod;
	}
}
