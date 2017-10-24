package supertv.httpgw;



import javax.ejb.EJB;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by tanerka on 24.10.2017.
 */
public class GatewayServlet extends HttpServlet {

    @EJB
    AsyncRequestProcessor asyncRequestProcessor;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        execute(request, response);
    }

    private void execute(HttpServletRequest request, HttpServletResponse response) {
        AsyncContext asyncContext = request.startAsync();
        asyncRequestProcessor.forwardRequest(asyncContext);

    }
}
