package supertv.httpgw.test;

import supertv.cluster.api.ClusterRestService;
import supertv.cluster.impl.HzClusterService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tanerka on 25.10.2017.
 */
public class TestService1 {

    public static void main(String args[]){
        Set<ClusterRestService> restServices = new HashSet<>();
        restServices.add(new ClusterRestService("cod/list", "1.2.0", 8080));
        restServices.add(new ClusterRestService("cod/add", "1.2.0", 8080));
        restServices.add(new ClusterRestService("cod/remove", "1.2.0", 8080));
        restServices.add(new ClusterRestService("epg/list", "1.2.0", 8080));
        restServices.add(new ClusterRestService("epg/add", "1.2.0", 8080));
        HzClusterService.getInstance().registerNode("test1", restServices, false);
    }
}
