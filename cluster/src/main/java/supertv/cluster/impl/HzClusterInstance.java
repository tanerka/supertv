package supertv.cluster.impl;

import com.hazelcast.core.*;
import supertv.cluster.api.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tanerka on 18.10.2017.
 */
public class HzClusterInstance implements ClusterInstance {

    private HazelcastInstance hzInstance;
    private HzClusterService clusterService;
    private String nodeId;
    private String nodeType;
    private boolean needMastership;

    public HzClusterInstance(HazelcastInstance hzInstance, HzClusterService clusterService, String nodeId, String nodeType, boolean needMastership) {
        this.hzInstance = hzInstance;
        this.clusterService = clusterService;
        this.nodeId = nodeId;
        this.nodeType = nodeType;
        this.needMastership = needMastership;
        IMap map = hzInstance.getMap("NeedMastershipMap");
        map.put(nodeType, needMastership);
        hzInstance.getCluster().addMembershipListener(new InternalHzMemberListener());
        mastershipCheck();
    }

    @Override
    public Set<ClusterNode> getAllNodesByType(String type) {
        Set<Member> members = hzInstance.getCluster().getMembers();
        HashSet<ClusterNode> clusterNodes = new HashSet<>();
        for(Member member : members){
            String nodeType =   member.getStringAttribute(HzClusterService.ATT_NODE_TYPE);
            if(nodeType!=null && nodeType.equals(type)){
                clusterNodes.add(createClusterNode(member));
            }
        }
        return clusterNodes;
    }

    @Override
    public Set<ClusterNode> getAllClusterNodes() {
        Set<Member> members = hzInstance.getCluster().getMembers();
        Set<ClusterNode> clusterSet = new HashSet<>();
        for(Member member : members){
            HzClusterNode hzNode = createClusterNode(member);
            clusterSet.add(hzNode);
        }
        return clusterSet;
    }

    @Override
    public ClusterNode getClusterNodeById(String nodeId) {
        Set<Member> members = hzInstance.getCluster().getMembers();
        for(Member member : members){
            if(member.getUuid().equals(nodeId)){
                return createClusterNode(member);
            }
        }
        return null;
    }

    @Override
    public Set<ClusterRestService> getServiceListByNodeId(String nodeId) {
        HzClusterNode node = (HzClusterNode)getClusterNodeById(nodeId);

        if(node==null){
            System.out.println("====== No node found with id : "+nodeId);
            return null;
        }
        Set<ClusterRestService> restServices = (Set<ClusterRestService>)node.getMember().getAttributes().get(HzClusterService.ATT_REST_SERVICES);
        if(restServices==null){
            System.out.println("====== Rest services is null: "+restServices);
        }else {
            System.out.println("====== Rest services size: "+restServices.size());
        }

        return restServices;

    }

    @Override
    public ClusterNode getMasterNode(String type) {
        Set<ClusterNode> custerNodeSet = getAllNodesByType(type);
        for(ClusterNode node : custerNodeSet){
            if(node.getMastership()!=null && node.getMastership().equals(ClusterNode.Mastership.MASTER)){
                return node;
            }
        }
        return null;
    }

    @Override
    public ClusterNode getLocalNode() {
        Member member =  hzInstance.getCluster().getLocalMember();
        return createClusterNode(member);
    }

    @Override
    public ClusterMap getMap(String mapName) {
        return new HzClusterMap(hzInstance.getMap(mapName));
    }

    @Override
    public Boolean isMastershipNeeded(String nodeType) {
        IMap map = hzInstance.getMap("NeedMastershipMap");
        Object obj = map.get(nodeType);
        return obj == null ? Boolean.FALSE : (Boolean)obj;
    }

    @Override
    public ClusterTopic getTopic(String topicName) {
        ITopic iTopic = hzInstance.getTopic(topicName);
        HzClusterTopic clusterTopic = new HzClusterTopic();
        clusterTopic.setName(topicName);
        clusterTopic.setiTopic(iTopic);
        return clusterTopic;
    }

    @Override
    public String registerClusterNodeListener(ClusterNodeListener clusterNodeListener) {
        return hzInstance.getCluster().addMembershipListener(new HzClusterNodeListener(clusterNodeListener));
    }

    @Override
    public void unregisterClusterNodeListener(String listenerId) {
        hzInstance.getCluster().removeMembershipListener(listenerId);
    }


    @Override
    public void shutdown() {
        hzInstance.shutdown();
    }

    private void mastershipCheck() {
        if(!needMastership){
            hzInstance.getCluster().getLocalMember().setStringAttribute(HzClusterService.ATT_MASTERSHIP, ClusterNode.Mastership.EQUAL.toString());
            return;
        }

        Set<ClusterNode> nodeSet = getAllNodesByType(this.nodeType);
        ClusterNode master = null;
        for(ClusterNode node : nodeSet){

            if(node.getMastership()!=null && node.getMastership().equals(ClusterNode.Mastership.MASTER) ){
                master = node;
                break;
            }

            if(master==null){
                master = node;
            }else {
                if(master.getId().compareTo(node.getId())>0){
                    master = node;
                }
            }

        }

        if(master!=null && master.getId().equals(this.nodeId)){
            System.out.println("Local node is master for type :"+nodeType);
            hzInstance.getCluster().getLocalMember().setStringAttribute(HzClusterService.ATT_MASTERSHIP, ClusterNode.Mastership.MASTER.toString());
        }else {
            System.out.println("Local node is slave for type : "+nodeType);
            hzInstance.getCluster().getLocalMember().setStringAttribute(HzClusterService.ATT_MASTERSHIP, ClusterNode.Mastership.SLAVE.toString());
        }
    }

    private HzClusterNode createClusterNode(Member member){
        String nodeType = member.getStringAttribute(HzClusterService.ATT_NODE_TYPE);
        if(nodeType==null){
            return null;
        }
        String mastershipAttribute = member.getStringAttribute(HzClusterService.ATT_MASTERSHIP);
        ClusterNode.Mastership mastership = null;
        if(mastershipAttribute!=null) {
            mastership = ClusterNode.Mastership.valueOf(mastershipAttribute);
        }
        HzClusterNode hzClusterNode = new HzClusterNode();
        hzClusterNode.setMember(member);
        hzClusterNode.setNodeType(nodeType);
        hzClusterNode.setMastership(mastership);
        return hzClusterNode;
    }

    public class InternalHzMemberListener implements MembershipListener {

        @Override
        public void memberAdded(MembershipEvent membershipEvent) {

        }

        @Override
        public void memberRemoved(MembershipEvent membershipEvent) {
            String memberNodeType = membershipEvent.getMember().getStringAttribute(HzClusterService.ATT_NODE_TYPE);
            if(memberNodeType!=null && memberNodeType.equals(nodeType)){
                mastershipCheck();
            }
        }

        @Override
        public void memberAttributeChanged(MemberAttributeEvent memberAttributeEvent) {

        }
    }



}
