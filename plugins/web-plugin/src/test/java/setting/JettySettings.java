package setting;

import cucumber.api.java.Before;
import java.lang.management.ManagementFactory;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Runtime.getRuntime;

public class JettySettings {

    private static final Logger LOG = LoggerFactory.getLogger(JettySettings.class);

    private final String WAR_PATH = getClass().getClassLoader().getResource("test-web-app.war").getFile();
    private static int PORT = 8181;

    private static boolean dunit = true;

    private Server server;

    @Before(order = 999)
    public void startJetty() throws Exception {

        if (dunit) {
            // stop jetty after all features
            getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    server.stop();
                } catch (Exception e) {
                    LOG.error("Error with stopping jetty server", e);
                }
            }));

            // start jetty
//            Random r = new Random( System.currentTimeMillis());
//            server = new Server(((1 + r.nextInt(2)) * 10000 + r.nextInt(10000)));
            server = new Server(PORT);

            MBeanContainer mbContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
            server.addBean(mbContainer);

            WebAppContext webapp = new WebAppContext();
            webapp.setContextPath("/");
            webapp.setWar(WAR_PATH);

            server.setHandler(webapp);
            server.start();
            dunit = false;
        }
    }

}
