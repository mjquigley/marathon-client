package mesosphere.marathon.client.model.v2;

import mesosphere.client.common.ModelUtils;

public class Residency {
	private String taskLostBehaviour;
	private Integer relaunchEscalationTimeoutSeconds;

	public String getTaskLostBehaviour() {
		return taskLostBehaviour;
	}

	public void setTaskLostBehaviour(String taskLostBehaviour) {
		this.taskLostBehaviour = taskLostBehaviour;
	}

	public Integer getRelaunchEscalationTimeoutSeconds() {
		return relaunchEscalationTimeoutSeconds;
	}

	public void setRelaunchEscalationTimeoutSeconds(Integer relaunchEscalationTimeoutSeconds) {
		this.relaunchEscalationTimeoutSeconds = relaunchEscalationTimeoutSeconds;
	}

	@Override
	public String toString() {
		return ModelUtils.toString(this);
	}
}
