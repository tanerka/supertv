package supertv.cluster.api;

import java.io.Serializable;

/**
 * Created by tanerka on 17.10.2017.
 */
public interface ClusterTopic {

    String getName();
    void publish(Serializable object);
    String registerListener(ClusterTopicEventListener listener);
    void unregisterListener(String listenerId);
}
