package sk.codexa.cr.importorder.ftpserverlistener;

import lombok.extern.log4j.Log4j;
import org.apache.ftpserver.ftplet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import sk.codexa.cr.importorder.service.OrderConfirmationDbImport;

import java.io.IOException;

@Log4j
@Controller
public class FtpServerListener extends DefaultFtplet {

    @Autowired
    private OrderConfirmationDbImport orderConfirmationDbImport;

    @Override
    public FtpletResult onLogin(FtpSession session, FtpRequest request) throws FtpException, IOException {
        log.info(session.getUser().getName() + "- Logged in");
        return super.onLogin(session, request);
    }

    @Override
    public FtpletResult onDisconnect(FtpSession session) throws FtpException, IOException {
        log.info(session.getUser().getName() + "- Disconnected");
        return super.onDisconnect(session);
    }

    @Override
    public FtpletResult onUploadStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        log.info(session.getUser().getName() + "Started Uploading File " + request.getArgument());
        return super.onUploadStart(session, request);
    }

    @Override
    public FtpletResult onUploadEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        log.info("Finished Uploading " + request.getArgument());

//        TODO FLOW-38
        orderConfirmationDbImport.importFile(request.getArgument());
        return super.onUploadEnd(session, request);
    }


}
