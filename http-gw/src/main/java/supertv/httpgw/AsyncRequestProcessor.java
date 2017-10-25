package supertv.httpgw;

import org.apache.commons.lang3.StringUtils;
import supertv.httpgw.microservice.MicroServiceHandler;
import supertv.httpgw.microservice.MicroServiceUrl;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tanerka on 24.10.2017.
 */
@Stateless
public class AsyncRequestProcessor {

    @Asynchronous
    public void forwardRequest(AsyncContext asyncContext) {
        try {
            ConcurrentHashMap<String, ConcurrentHashMap<String, String>> pathNodeIdUrlMap  = MicroServiceHandler.getInstance().getServiceMap();
            for(String key  : pathNodeIdUrlMap.keySet()){
                asyncContext.getResponse().getWriter().println(key);
                asyncContext.getResponse().getWriter().println("---------------------------");
                ConcurrentHashMap<String, String> urls = pathNodeIdUrlMap.get(key);
                for(String nodeId : urls.keySet()){
                    asyncContext.getResponse().getWriter().println(nodeId+"  -  "+urls.get(nodeId));
                }
                asyncContext.getResponse().getWriter().println();
            }
            asyncContext.getResponse().getWriter().println("DONE! ");

            HttpServletRequest httpReq = (HttpServletRequest)asyncContext.getRequest();
            System.out.println("PATH  :"+httpReq.getServletPath());
            System.out.println("URI  :"+httpReq.getRequestURI());
            System.out.println("CONTEXT PATH  :"+httpReq.getContextPath());

            String targetPath = StringUtils.substringAfter(httpReq.getRequestURI(), httpReq.getServletPath());
            ConcurrentHashMap<String, String> urls = pathNodeIdUrlMap.get(targetPath);
            if(urls==null || urls.isEmpty()){
                asyncContext.getResponse().getWriter().println("NO TARGET FOUND FOR PATH  :"+targetPath);
            }else {
                asyncContext.getResponse().getWriter().println("TARGET FOUND FOR PATH  :"+targetPath);
                for(String url : urls.values()){
                    asyncContext.getResponse().getWriter().println(url+"?"+httpReq.getQueryString());
                }
            }
            asyncContext.complete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HttpRequestInfo findTargetHttpRequest(HttpServletRequest httpReq){
        ConcurrentHashMap<String, ConcurrentHashMap<String, String>> pathNodeIdUrlMap  = MicroServiceHandler.getInstance().getServiceMap();
        String targetPath = StringUtils.substringAfter(httpReq.getRequestURI(), httpReq.getServletPath());
        ConcurrentHashMap<String, String> urls = pathNodeIdUrlMap.get(targetPath);
        if(urls==null || urls.isEmpty()){
            System.out.println("NO TARGET FOUND FOR PATH  :"+targetPath);
        }else {
            return null;
        }
        return null;
    }

    private class HttpRequestInfo {
        String nodeId;
        String method;
        String queryParams;
        String url;

    }



}
