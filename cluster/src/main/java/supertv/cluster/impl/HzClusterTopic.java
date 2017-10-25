package supertv.cluster.impl;

import com.hazelcast.core.ITopic;
import supertv.cluster.api.ClusterTopic;
import supertv.cluster.api.ClusterTopicEventListener;

import java.io.Serializable;

/**
 * Created by tanerka on 17.10.2017.
 */
public class HzClusterTopic implements ClusterTopic {

    ITopic iTopic;
    String name;
    ClusterTopicEventListener clusterTopicEventListener;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void publish(Serializable object) {
        iTopic.publish(object);
    }

    @Override
    public String registerListener(ClusterTopicEventListener listener) {
        return iTopic.addMessageListener(new HzClusterTopicEventListener(listener));
    }

    @Override
    public void unregisterListener(String listenerId) {
        iTopic.removeMessageListener(listenerId);
    }

    public ITopic getiTopic() {
        return iTopic;
    }

    public void setiTopic(ITopic iTopic) {
        this.iTopic = iTopic;
    }

    public void setName(String name){
        this.name = name;
    }
}
