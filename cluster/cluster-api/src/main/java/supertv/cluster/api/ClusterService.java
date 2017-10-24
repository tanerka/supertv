package supertv.cluster.api;

import java.util.Map;

/**
 * Created by tanerka on 17.10.2017.
 */
public interface ClusterService {

    ClusterInstance registerNode(String nodeType, Map<String, Object> attributes, boolean needMastership);


}
