package sk.flowy.importorder.ftpserverlistener;

import lombok.extern.log4j.Log4j;
import org.apache.ftpserver.ConnectionConfigFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerConfigurationException;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Log4j
@Component
public class StartFtpServer {

    @Autowired
    private FtpServerListener ftpServerListener;

    @Autowired
    private Environment env;

    @PostConstruct
    public void startFtpServer() {
        try {
            FtpServerFactory ftpServerFactory = new FtpServerFactory();

            ListenerFactory listenerFactory = new ListenerFactory();
            listenerFactory.setPort(Integer.parseInt(env.getProperty("fptServerPort")));
            listenerFactory.setIdleTimeout(Integer.parseInt(env.getProperty("ftpIdleTimeout")));

            ftpServerFactory.addListener("default", listenerFactory.createListener());
            ftpServerFactory.getFtplets().put(ftpServerListener.getClass().getName(), ftpServerListener);

            ConnectionConfigFactory configFactory = new ConnectionConfigFactory();
            ftpServerFactory.setConnectionConfig(configFactory.createConnectionConfig());

            PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
            userManagerFactory.setFile(new File("ftpusers.properties"));
            ftpServerFactory.setUserManager(userManagerFactory.createUserManager());

            BaseUser user = new BaseUser();
            user.setName(env.getProperty("ftpUserName"));
            user.setPassword(env.getProperty("ftpUserPassword"));
            UserManager userManager = ftpServerFactory.getUserManager();

            List<Authority> authorities = new ArrayList<>();
            authorities.add(new WritePermission());
            user.setAuthorities(authorities);

            userManager.save(user);

            FtpServer server = ftpServerFactory.createServer();
            server.start();
            log.info("FTP server is running!");
        } catch (FtpException e) {
            log.error("Start FPT server failed", e);
            throw new FtpServerConfigurationException("Start FPT server failed", e);
        }
    }
}
