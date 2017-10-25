package supertv.cluster.api;

import java.io.Serializable;

/**
 * Created by tanerka on 24.10.2017.
 */
public class ClusterRestService implements Serializable{


    private String path;
    private String version;
    private int httpPort;

    public ClusterRestService(String path, String version, int httpPort) {
        this.path = path;
        this.version = version;
        this.httpPort = httpPort;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

}
