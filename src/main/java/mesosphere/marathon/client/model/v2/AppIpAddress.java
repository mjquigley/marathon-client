package mesosphere.marathon.client.model.v2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import mesosphere.client.common.ModelUtils;

public class AppIpAddress {
	private Collection<String> groups;
	private Map<String, String> labels = new HashMap<>();
	private DiscoveryInfo discoveryInfo;
	private String networkName;

	public Collection<String> getGroups() {
		return groups;
	}

	public void setGroups(Collection<String> groups) {
		this.groups = groups;
	}

	public void addGroup(String option) {
		if (groups == null) {
			groups = new ArrayList<String>();
		}
		groups.add(option);
	}

	public Map<String, String> getLabels() {
		return labels;
	}

	public void setLabels(Map<String, String> labels) {
		this.labels = labels;
	}

	public DiscoveryInfo getDiscoveryInfo() {
		return discoveryInfo;
	}

	public void setDiscoveryInfo(DiscoveryInfo discoveryInfo) {
		this.discoveryInfo = discoveryInfo;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	@Override
	public String toString() {
		return ModelUtils.toString(this);
	}
}
