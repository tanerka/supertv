package supertv.httpgw.microservice;

/**
 * Created by tanerka on 25.10.2017.
 */
public class MicroServiceUrl {

    private String nodeId;
    private String httpUrl;

    public MicroServiceUrl(String nodeId, String httpUrl) {
        this.nodeId = nodeId;
        this.httpUrl = httpUrl;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public boolean equals(Object object) {
        if(object instanceof MicroServiceUrl){
            return this.getNodeId().equals((((MicroServiceUrl) object).getNodeId()));
        }else {
            return super.equals(object);
        }
    }

    public int hashCode() {
        return this.getNodeId().hashCode();
    }

    public String toString(){
        return this.nodeId+"   ||  "+httpUrl;
    }
}
