package supertv.cluster.api;

/**
 * Created by tanerka on 17.10.2017.
 */
public class ClusterMessage<T> {

    private T data;

    private String clusterNodeId;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getClusterNodeId() {
        return clusterNodeId;
    }

    public void setClusterNodeId(String clusterNodeId) {
        this.clusterNodeId = clusterNodeId;
    }
}
