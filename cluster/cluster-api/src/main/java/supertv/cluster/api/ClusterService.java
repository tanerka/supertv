package supertv.cluster.api;

import javax.ejb.Local;
import java.util.Set;

/**
 * Created by tanerka on 17.10.2017.
 */
@Local
public interface ClusterService {

    ClusterInstance registerNode(String nodeType, Set<ClusterRestService> restServices, boolean needMastership);


}
