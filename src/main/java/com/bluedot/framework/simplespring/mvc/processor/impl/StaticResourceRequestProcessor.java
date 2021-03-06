package com.bluedot.framework.simplespring.mvc.processor.impl;


import com.bluedot.framework.simplespring.mvc.RequestProcessorChain;
import com.bluedot.framework.simplespring.mvc.processor.RequestProcessor;
import com.bluedot.framework.simplespring.util.LogUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * @author xxbb
 */
public class StaticResourceRequestProcessor implements RequestProcessor {
    private static final String DEFAULT_TOMCAT_SERVLET = "default";
    private static final String STATIC_RESOURCE_PREFIX = "/static/";
    private static final String ASSETS_RESOURCE_PREFIX = "/assets/";
    private static final String HTML_RESOURCE_SUFFIX = ".html";
    private static final String VUE_RESOURCE_SUFFIX = ".vue";
    private static final String JSP_RESOURCE_SUFFIX = ".jsp";

    /**
     * tomcat默认的请求派发器
     */
    RequestDispatcher defaultDispatcher;

    public StaticResourceRequestProcessor(ServletContext servletContext) {
        this.defaultDispatcher = servletContext.getNamedDispatcher(DEFAULT_TOMCAT_SERVLET);
        if (null == defaultDispatcher) {
            LogUtil.getLogger().error("StaticResourceRequestProcessor constructor error");
            throw new RuntimeException("There is no default tomcat servlet");
        }
        LogUtil.getLogger().debug("The default servlet for static resource is {}", DEFAULT_TOMCAT_SERVLET);
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        //通过请求路径判断是否为静态资源
        if (isStaticResource(requestProcessorChain.getRequestPath())) {
            //如果是静态资源，则交给default servlet处理
            LogUtil.getLogger().debug("static requestPath: {}", requestProcessorChain.getRequestPath());
            defaultDispatcher.forward(requestProcessorChain.getReq(), requestProcessorChain.getResp());
            return false;
        }
        return true;


    }

    /**
     * 判断该路径是否为静态资源路径
     *
     * @param requestPath 请求路径
     * @return 否为静态资源路径
     */
    private boolean isStaticResource(String requestPath) {
        return requestPath.startsWith(STATIC_RESOURCE_PREFIX)
                || requestPath.startsWith(ASSETS_RESOURCE_PREFIX)
                || requestPath.endsWith(HTML_RESOURCE_SUFFIX)
                || requestPath.endsWith(VUE_RESOURCE_SUFFIX)
                || requestPath.endsWith("index.jsp");
    }
}
