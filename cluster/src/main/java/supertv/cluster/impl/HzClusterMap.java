package supertv.cluster.impl;


import supertv.cluster.api.ClusterMap;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by tanerka on 20.10.2017.
 */
public class HzClusterMap implements ClusterMap {

    Map hzMap;
    HzClusterMap(Map hzMap){
        this.hzMap = hzMap;
    }

    @Override
    public Object get(Object key) {
        return hzMap.get(key);
    }

    @Override
    public Object put(Object key, Object value) {
        return hzMap.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return hzMap.remove(key);
    }

    @Override
    public boolean containsKey(Object key) {
        return hzMap.containsKey(key);
    }

    @Override
    public void clear() {
        hzMap.clear();
    }

    @Override
    public Collection values() {
        return hzMap.values();
    }

    @Override
    public Set keySet() {
        return hzMap.keySet();
    }
}
