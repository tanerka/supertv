package supertv.cluster.api;

/**
 * Created by tanerka on 24.10.2017.
 */
public class ClusterRestService {

    enum OPERATION {
        GET, POST, DELETE, PUT
    }

    private String path;
    private String version;
    private OPERATION operation;

    public ClusterRestService(String path, String version, OPERATION operation) {
        this.path = path;
        this.version = version;
        this.operation = operation;
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

    public OPERATION getOperation() {
        return operation;
    }

    public void setOperation(OPERATION operation) {
        this.operation = operation;
    }
}
