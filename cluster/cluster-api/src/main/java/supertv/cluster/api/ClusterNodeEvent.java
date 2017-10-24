package supertv.cluster.api;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by tanerka on 17.10.2017.
 */
public class ClusterNodeEvent implements Serializable{
    private static final long serialVersionUID = -2010865371829087371L;

    public enum EventType {
        NODE_ADDED, NODE_REMOVED,
    }

    private String clusterNodeId;

    private EventType eventType;

    private Set<ClusterNode> nodes;


    public String getClusterNodeId() {
        return clusterNodeId;
    }

    public void setClusterNodeId(String clusterNodeId) {
        this.clusterNodeId = clusterNodeId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Set<ClusterNode> getNodes() {
        return nodes;
    }

    public void setNodes(Set<ClusterNode> nodes) {
        this.nodes = nodes;
    }
}
