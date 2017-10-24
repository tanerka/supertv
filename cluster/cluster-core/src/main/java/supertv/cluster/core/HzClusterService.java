package supertv.cluster.core;

import com.hazelcast.config.Config;
import com.hazelcast.config.MemberAttributeConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;
import supertv.cluster.api.ClusterInstance;
import supertv.cluster.api.ClusterService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanerka on 17.10.2017.
 */
public class HzClusterService implements ClusterService {


    public static final String ATT_NODE_TYPE = "ATT_NODE_TYPE";
    public static final String ATT_MASTERSHIP = "ATT_MASTERSHIP";

    private static ClusterService clusterService;



    public static ClusterService getInstance(){
        if(clusterService==null){
            HzClusterService hzClusterService = new HzClusterService();
            clusterService = hzClusterService;
        }
        return clusterService;
    }


    @Override
    public ClusterInstance registerNode(String nodeType, Map<String, Object> attributes, boolean needMastership) {
        Config config = new Config();
        MemberAttributeConfig attributeConfig = new MemberAttributeConfig();
        Map<String, Object> attributeMap = new HashMap<String, Object>();
        attributeMap.put(ATT_NODE_TYPE, nodeType);
        for(String key : attributes.keySet()){
            attributeMap.put(key, attributes.get(key));
        }
        attributeConfig.setAttributes(attributeMap);
        config.setMemberAttributeConfig(attributeConfig);
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
        Member member = hazelcastInstance.getCluster().getLocalMember();
        HzClusterInstance hzClusterInstance = new HzClusterInstance(hazelcastInstance, this, member.getUuid(), nodeType, needMastership);
        return hzClusterInstance;
    }




}
