package supertv.cluster.api;

import java.util.Collection;
import java.util.Set;

/**
 * Created by tanerka on 20.10.2017.
 */
public interface ClusterMap {


    public Object get(Object key);

    public Object put(Object key, Object value);

    public Object remove(Object key);

    public boolean containsKey(Object key);

    public void clear();

    public Collection values();

    public Set keySet();

}
