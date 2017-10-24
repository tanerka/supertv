package supertv.cluster.api;

import java.util.Map;

/**
 * Created by tanerka on 17.10.2017.
 */
public interface ClusterNode {

    enum Mastership {
        MASTER, SLAVE, EQUAL
    }

    String getId();

    String getNodeType();

    Mastership getMastership();

    Map<String, Object> getAttributeMap();

}
