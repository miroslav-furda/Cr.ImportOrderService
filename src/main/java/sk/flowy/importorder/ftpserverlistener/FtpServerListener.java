package sk.flowy.importorder.ftpserverlistener;

import lombok.extern.log4j.Log4j;
import org.apache.ftpserver.filesystem.nativefs.impl.NativeFtpFile;
import org.apache.ftpserver.ftplet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import sk.flowy.importorder.service.OrderConfirmationDbImport;

import java.io.File;
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
        FtpFile fptFile = session.getFileSystemView().getFile(request.getArgument());
        File importFile = ((NativeFtpFile) fptFile).getPhysicalFile();
        if (importFile != null) {
            if (orderConfirmationDbImport.importFile(importFile)) {
                log.info("Data from input file was saved into database.");
            }
        }
        log.info("Data from input file can't save into database");
        return super.onUploadEnd(session, request);
    }
}
