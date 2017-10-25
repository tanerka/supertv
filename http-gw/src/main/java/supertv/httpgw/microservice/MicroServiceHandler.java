package supertv.httpgw.microservice;

import supertv.cluster.api.*;
import supertv.cluster.impl.HzClusterNode;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tanerka on 25.10.2017.
 */
public class MicroServiceHandler {

    private static MicroServiceHandler instance;
    private ClusterInstance cluster;
    private String servetBasePath;
    private ConcurrentHashMap<String, ConcurrentHashMap<String, String>> pathNodeIdUrlMap = new ConcurrentHashMap<>();


    public static MicroServiceHandler getInstance() {
        if(instance==null)
            instance = new MicroServiceHandler();
        return instance;
    }

    public void setClusterInstance(ClusterInstance clusterInstance){
        this.cluster = clusterInstance;
        this.cluster.registerClusterNodeListener(new HttpGwClusterNodeListener());
    }

    public void setServetBasePath(String path){
        this.servetBasePath = path;
    }

    private String calculatePath(String nodeType, ClusterRestService restService){
        StringBuilder sb = new StringBuilder();
        sb.append("/").append(nodeType).append("/").append(restService.getVersion()).append("/").append(restService.getPath());
        return sb.toString();
    }

    public String getTargetUrl(String incomingUrl){
        String targetNodeType = getTargetNodeType(incomingUrl);
        if(targetNodeType==null || targetNodeType.isEmpty()){
            System.out.println("Target Node type not found");
            return null;
        }
        ConcurrentHashMap<String, String> nodeIdToUrlMap = pathNodeIdUrlMap.get(incomingUrl);
        if(nodeIdToUrlMap==null){
            System.out.println("No target found for given url : "+incomingUrl);
            return null;
        }

        Boolean isMastershipNeeded = cluster.isMastershipNeeded(targetNodeType);
        if(isMastershipNeeded){
            ClusterNode selectedNode = cluster.getMasterNode(targetNodeType);
            if(selectedNode==null){
                System.out.println("Target Master Node not found for type : "+targetNodeType);
                return null;
            }
            String selectedNodeId = selectedNode.getId();
            String url = nodeIdToUrlMap.get(selectedNodeId);
            System.out.println("Selected MASTER url: "+url);
            return url;
        }else {
            Random rand = new Random();
            int randomIndex = rand.nextInt(nodeIdToUrlMap.size());
            String url = (String)nodeIdToUrlMap.values().toArray()[randomIndex];
            System.out.println("Selected RANDOM url: "+url);
            return url;
        }
    }

    private String getTargetNodeType(String incomingUrl){
        return incomingUrl.split("/")[1];
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<String, String>> getServiceMap() {
        return this.pathNodeIdUrlMap;
    }

    public class HttpGwClusterNodeListener implements ClusterNodeListener {

        @Override
        public void clusterNodeAdded(ClusterNodeEvent clusterNodeEvent) {
            String nodeId = clusterNodeEvent.getClusterNodeId();
            System.out.println("clusterNodeAdded event received : "+nodeId);
            HzClusterNode node = (HzClusterNode)cluster.getClusterNodeById(nodeId);
            String nodeHost = node.getMember().getAddress().getHost();
            Set<ClusterRestService> restServices = cluster.getServiceListByNodeId(nodeId);
            for(ClusterRestService restService : restServices){
                String path = calculatePath(node.getNodeType(), restService);
                ConcurrentHashMap<String, String> nodeIdUrlMap = pathNodeIdUrlMap.get(path);
                if(nodeIdUrlMap==null){
                    nodeIdUrlMap = new ConcurrentHashMap<>();
                    pathNodeIdUrlMap.put(path, nodeIdUrlMap);
                }
                String serviceUrl = "http://"+nodeHost+":"+restService.getHttpPort()+"/"+restService.getPath();
                nodeIdUrlMap.put(nodeId, serviceUrl);
            }
        }

        @Override
        public void clusterNodeRemoved(ClusterNodeEvent clusterNodeEvent) {
            String nodeId = clusterNodeEvent.getClusterNodeId();
            System.out.println("clusterNodeRemoved event received : "+nodeId);
            HzClusterNode node = (HzClusterNode)cluster.getClusterNodeById(nodeId);
            Set<ClusterRestService> restServices = cluster.getServiceListByNodeId(nodeId);
            for(ClusterRestService restService : restServices){
                String path = calculatePath(node.getNodeType(), restService);
                ConcurrentHashMap<String, String> nodeIdUrlMap = pathNodeIdUrlMap.get(path);
                if(nodeIdUrlMap!=null){
                    nodeIdUrlMap.remove(nodeId);
                }
            }
        }

        @Override
        public void clusterNodeAttributeChanged(ClusterNodeAttibuteEvent clusterNodeAttibuteEvent) {

        }
    }



}
