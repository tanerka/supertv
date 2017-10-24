package supertv.cluster.core;

import com.hazelcast.cluster.MemberAttributeOperationType;
import com.hazelcast.core.Member;
import com.hazelcast.core.MemberAttributeEvent;
import com.hazelcast.core.MembershipEvent;
import com.hazelcast.core.MembershipListener;
import supertv.cluster.api.ClusterNodeAttibuteEvent;
import supertv.cluster.api.ClusterNodeEvent;
import supertv.cluster.api.ClusterNodeListener;


/**
 * Created by tanerka on 17.10.2017.
 */
public class HzClusterNodeListener implements MembershipListener {

    private ClusterNodeListener clusterNodeListener;


    public HzClusterNodeListener(ClusterNodeListener clusterNodeListener) {
        this.clusterNodeListener = clusterNodeListener;
    }

    @Override
    public void memberAdded(MembershipEvent membershipEvent) {
        //System.out.println("XXXX memberAdded");
        Member member = membershipEvent.getMember();
        ClusterNodeEvent event = new ClusterNodeEvent();
        event.setClusterNodeId(member.getUuid());
        event.setEventType(ClusterNodeEvent.EventType.NODE_ADDED);
        clusterNodeListener.clusterNodeAdded(event);
    }

    @Override
    public void memberRemoved(MembershipEvent membershipEvent) {
       // System.out.println("XXXX memberRemoved");
        Member member = membershipEvent.getMember();
        ClusterNodeEvent event = new ClusterNodeEvent();
        event.setClusterNodeId(member.getUuid());
        event.setEventType(ClusterNodeEvent.EventType.NODE_REMOVED);
        clusterNodeListener.clusterNodeRemoved(event);
    }

    @Override
    public void memberAttributeChanged(MemberAttributeEvent memberAttributeEvent) {
        Member member = memberAttributeEvent.getMember();
        ClusterNodeAttibuteEvent event = new ClusterNodeAttibuteEvent();
        ClusterNodeAttibuteEvent.EventType eventType = ClusterNodeAttibuteEvent.EventType.PUT;
        if(memberAttributeEvent.getOperationType().equals(MemberAttributeOperationType.REMOVE)){
            eventType = ClusterNodeAttibuteEvent.EventType.REMOVE;
        }
        event.setEventType(eventType);
        event.setClusterNodeId(member.getUuid());
        event.setKey(memberAttributeEvent.getKey());
        event.setValue(memberAttributeEvent.getValue());
        clusterNodeListener.clusterNodeAttributeChanged(event);
    }
}
