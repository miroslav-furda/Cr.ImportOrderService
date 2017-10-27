package sk.flowy.importorder.ftpserverlistener;

import lombok.extern.log4j.Log4j;
import org.apache.ftpserver.ConnectionConfigFactory;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Log4j
@Component
public class FtpServer {

    @Autowired
    private FtpServerListener ftpServerListener;

    @PostConstruct
    public void startFtpServer() {
        try {
            FtpServerFactory ftpServerFactory = new FtpServerFactory();

            ListenerFactory listenerFactory = new ListenerFactory();
            // set the port of the listener
            listenerFactory.setPort(5000);
//            TODO change server address
//            listenerFactory.setServerAddress("127.0.0.1");
            listenerFactory.setIdleTimeout(3000);


            ftpServerFactory.addListener("default", listenerFactory.createListener());
            ftpServerFactory.getFtplets().put(ftpServerListener.getClass().getName(), ftpServerListener);

            ConnectionConfigFactory configFactory = new ConnectionConfigFactory();
            ftpServerFactory.setConnectionConfig(configFactory.createConnectionConfig());

            PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
            userManagerFactory.setFile(new File("ftpusers.properties"));
            ftpServerFactory.setUserManager(userManagerFactory.createUserManager());

            BaseUser user = new BaseUser();
            user.setName("admin");
            user.setPassword("admin");
            UserManager userManager = ftpServerFactory.getUserManager();

            List<Authority> authorities = new ArrayList<>();
            authorities.add(new WritePermission());
            user.setAuthorities(authorities);

            userManager.save(user);

            org.apache.ftpserver.FtpServer server = ftpServerFactory.createServer();
            server.start();
            log.info("FTP server is running!");
        } catch (FtpException e) {
            log.error("Export creation failed ", e);
        }
    }
}
