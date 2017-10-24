package supertv.cluster.core;

import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
import supertv.cluster.api.ClusterMessage;
import supertv.cluster.api.ClusterTopicEventListener;


/**
 * Created by tanerka on 17.10.2017.
 */
public class HzClusterTopicEventListener implements MessageListener {

    private ClusterTopicEventListener clusterNodeListener;
    private HzClusterService clusterService;

    public HzClusterTopicEventListener(ClusterTopicEventListener clusterNodeListener) {
        this.clusterNodeListener = clusterNodeListener;
    }

    @Override
    public void onMessage(Message message) {
        ClusterMessage clusterMessage = new ClusterMessage();
        clusterMessage.setData(message.getMessageObject());
        clusterMessage.setClusterNodeId(message.getPublishingMember().getUuid());
        clusterNodeListener.onMessage(clusterMessage);
    }
}
