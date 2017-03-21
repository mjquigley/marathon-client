package mesosphere.marathon.client.model.v2;

import java.util.Collection;

import mesosphere.client.common.ModelUtils;

public class ReadinessCheck {
	private String name;
	private String protocol;
	private String path;
	private String portName;
	private Integer intervalSeconds;
	private Integer timeoutSeconds;
	private Collection<Integer> httpStatusCodesForReady;
	private boolean preserveLastResponse;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public Integer getIntervalSeconds() {
		return intervalSeconds;
	}

	public void setIntervalSeconds(Integer intervalSeconds) {
		this.intervalSeconds = intervalSeconds;
	}

	public Integer getTimeoutSeconds() {
		return timeoutSeconds;
	}

	public void setTimeoutSeconds(Integer timeoutSeconds) {
		this.timeoutSeconds = timeoutSeconds;
	}

	public Collection<Integer> getHttpStatusCodesForReady() {
		return httpStatusCodesForReady;
	}

	public void setHttpStatusCodesForReady(Collection<Integer> httpStatusCodesForReady) {
		this.httpStatusCodesForReady = httpStatusCodesForReady;
	}

	public boolean isPreserveLastResponse() {
		return preserveLastResponse;
	}

	public void setPreserveLastResponse(boolean preserveLastResponse) {
		this.preserveLastResponse = preserveLastResponse;
	}

	@Override
	public String toString() {
		return ModelUtils.toString(this);
	}
}
