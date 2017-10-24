package supertv.cluster.api;

import java.util.Set;

/**
 * Created by tanerka on 18.10.2017.
 */
public interface ClusterInstance {

    Set<ClusterNode> getAllNodesByType(String type);
    Set<ClusterNode> getAllClusterNodes();
    ClusterNode getClusterNodeById(String nodeId);
    ClusterNode getMasterNode(String type);
    ClusterNode getLocalNode();

    ClusterMap getMap(String mapName);
    ClusterTopic getTopic(String topicName);

    String registerClusterNodeListener(ClusterNodeListener listener);
    void unregisterClusterNodeListener(String listenerId);

    void shutdown();
}
