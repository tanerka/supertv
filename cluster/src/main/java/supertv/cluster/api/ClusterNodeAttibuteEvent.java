package supertv.cluster.api;

import java.io.Serializable;

/**
 * Created by tanerka on 17.10.2017.
 */
public class ClusterNodeAttibuteEvent implements Serializable{
    private static final long serialVersionUID = -2110965571829087571L;

    public enum EventType {
        PUT, REMOVE
    }

    private EventType eventType;

    private String key;

    private Object value;

    private String clusterNodeId;

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getClusterNodeId() {
        return clusterNodeId;
    }

    public void setClusterNodeId(String clusterNodeId) {
        this.clusterNodeId = clusterNodeId;
    }
}
