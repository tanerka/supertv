package supertv.httpgw;



import supertv.cluster.api.ClusterInstance;
import supertv.cluster.api.ClusterService;
import supertv.httpgw.microservice.MicroServiceHandler;

import javax.ejb.EJB;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;


/**
 * Created by tanerka on 24.10.2017.
 */
public class GatewayServlet extends HttpServlet {

    private static final String NODE_TYPE_HTTP_GW = "HTTP_GW";

    @EJB
    AsyncRequestProcessor asyncRequestProcessor;

    @EJB
    ClusterService clusterService;


    @Override
    public void init() throws ServletException {
        System.out.println("Initializing Http Gateway ...");
        ClusterInstance clusterInstance = clusterService.registerNode(NODE_TYPE_HTTP_GW, new HashSet<>(), false);
        MicroServiceHandler.getInstance().setClusterInstance(clusterInstance);
        System.out.println("getContextPath : "+getServletContext().getContextPath());
        System.out.println("getServletContextName : "+getServletContext().getServletContextName());
        System.out.println("getServerInfo : "+getServletContext().getServerInfo());



        MicroServiceHandler.getInstance().setServetBasePath(getServletContext().getContextPath());
        System.out.println("Http Gateway initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        execute(request, response);
    }

    private void execute(HttpServletRequest request, HttpServletResponse response) {
        AsyncContext asyncContext = request.startAsync();
        asyncRequestProcessor.forwardRequest(asyncContext);
    }


    @Override
    public void destroy()  {
        System.out.println("Http Gateway destroyed");
    }
}
