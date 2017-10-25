package supertv.cluster.api;

/**
 * Created by tanerka on 17.10.2017.
 */
public interface ClusterNodeListener {


    void clusterNodeAdded(ClusterNodeEvent clusterNodeEvent);

    void clusterNodeRemoved(ClusterNodeEvent clusterNodeEvent);

    void clusterNodeAttributeChanged(ClusterNodeAttibuteEvent clusterNodeAttibuteEvent);

}
