package edu.hm;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.webapp.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Start the application without an AppServer like tomcat.
 * @author <a mailto:axel.boettcher@hm.edu>Axel B&ouml;ttcher</a>
 *
 */
public class JettyStarter {

    public static final String APP_URL = "/";
    public static final int PORT = 8082;
    public static final String WEBAPP_DIR = "./src/main/webapp/";

    /**
     * Deploy local directories using Jetty without needing a container-based deployment.
     * @param args unused
     * @throws Exception might throw for several reasons.
     */
    public static void main(String... args) throws Exception {
        Server jetty = new Server(PORT);

        Runnable runnable = () -> {};

        jetty.setHandler(new AbstractHandler(){

            @Override
            public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
                new WebAppContext(WEBAPP_DIR, APP_URL).handle(s, request, httpServletRequest, httpServletResponse);
                runnable.run();
            }
        });
        jetty.start();
        System.out.println("Jetty listening on port " + PORT);
        jetty.join();
    }
}
