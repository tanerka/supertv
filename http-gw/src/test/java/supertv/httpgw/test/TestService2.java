package supertv.httpgw.test;

import supertv.cluster.api.ClusterRestService;
import supertv.cluster.impl.HzClusterService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tanerka on 25.10.2017.
 */
public class TestService2 {

    public static void main(String args[]){
        Set<ClusterRestService> restServices = new HashSet<>();
        restServices.add(new ClusterRestService("channel/list", "4.1.0", 8080));
        restServices.add(new ClusterRestService("channel/add", "4.1.0", 8080));
        restServices.add(new ClusterRestService("ott/remove", "2.2.0", 8080));
        restServices.add(new ClusterRestService("subscriber/list", "1.2.0", 8080));
        restServices.add(new ClusterRestService("subscriber/add", "1.2.0", 8080));
        HzClusterService.getInstance().registerNode("test2", restServices, false);
    }
}
