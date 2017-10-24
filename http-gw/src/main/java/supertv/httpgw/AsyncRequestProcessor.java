package supertv.httpgw;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.servlet.AsyncContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tanerka on 24.10.2017.
 */
@Stateless
public class AsyncRequestProcessor {

    @Asynchronous
    public void forwardRequest(AsyncContext asyncContext) {
        try {
            Thread.sleep(2000);
            PrintWriter writer = asyncContext.getResponse().getWriter();
            writer.println("REQUEST DATE  :"+new SimpleDateFormat("HH:mm:ss").format(new Date()));
            writer.close();

            asyncContext.complete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
