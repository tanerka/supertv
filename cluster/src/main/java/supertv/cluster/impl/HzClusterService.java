package supertv.cluster.impl;

import com.hazelcast.config.Config;
import com.hazelcast.config.MemberAttributeConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;
import supertv.cluster.api.ClusterInstance;
import supertv.cluster.api.ClusterRestService;
import supertv.cluster.api.ClusterService;

import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by tanerka on 17.10.2017.
 */
@Singleton
public class HzClusterService implements ClusterService {


    public static final String ATT_NODE_TYPE = "ATT_NODE_TYPE";
    public static final String ATT_MASTERSHIP = "ATT_MASTERSHIP";
    public static final String ATT_REST_SERVICES = "ATT_REST_SERVICES";

    private static ClusterService clusterService;



    public static ClusterService getInstance(){
        if(clusterService==null){
            HzClusterService hzClusterService = new HzClusterService();
            clusterService = hzClusterService;
        }
        return clusterService;
    }


    @Override
    public ClusterInstance registerNode(String nodeType, Set<ClusterRestService> restServices, boolean needMastership) {
        Config config = new Config();
        MemberAttributeConfig attributeConfig = new MemberAttributeConfig();
        Map<String, Object> attributeMap = new HashMap<String, Object>();
        attributeMap.put(ATT_NODE_TYPE, nodeType);
        if(restServices!=null) {
            attributeMap.put(ATT_REST_SERVICES, restServices);
        }
        attributeConfig.setAttributes(attributeMap);
        config.setMemberAttributeConfig(attributeConfig);
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
        Member member = hazelcastInstance.getCluster().getLocalMember();
        HzClusterInstance hzClusterInstance = new HzClusterInstance(hazelcastInstance, this, member.getUuid(), nodeType, needMastership);
        return hzClusterInstance;
    }




}
