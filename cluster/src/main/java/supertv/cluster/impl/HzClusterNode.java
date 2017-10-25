package supertv.cluster.impl;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;
import supertv.cluster.api.ClusterNode;

import java.util.Map;

/**
 * Created by tanerka on 17.10.2017.
 */
public class HzClusterNode implements ClusterNode {

    String nodeType;
    Mastership mastership = ClusterNode.Mastership.EQUAL;
    Member member;
    HazelcastInstance hzInstance;


    @Override
    public String getId() {
        return member.getUuid();
    }

    @Override
    public String getNodeType() {
        return nodeType;
    }

    @Override
    public Mastership getMastership() {
        return mastership;
    }

    @Override
    public Map<String, Object> getAttributeMap() {
        return member.getAttributes();
    }

    public void setMember(Member member){
        this.member = member;
    }

    public void setNodeType(String nodeType){
        this.nodeType = nodeType;
    }

    public void setMastership(Mastership mastership) {
        this.mastership = mastership;
    }

    public Member getMember() {return this.member;}

    public HazelcastInstance getHzInstance() {
        return hzInstance;
    }

    public void setHzInstance(HazelcastInstance hzInstance) {
        this.hzInstance = hzInstance;
    }
}
