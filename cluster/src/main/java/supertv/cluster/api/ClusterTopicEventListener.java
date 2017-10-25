package supertv.cluster.api;

/**
 * Created by tanerka on 17.10.2017.
 */
public interface ClusterTopicEventListener<T> {

    public void onMessage(ClusterMessage<T> message) ;
}
