package com.project.xmpp.controller;

import com.project.xmpp.model.User;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.debugger.SmackDebuggerFactory;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.impl.JidCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/xmpp")
public class XmppController {

    Logger logger = LoggerFactory.getLogger(XmppController.class);
    AbstractXMPPConnection connection;

    @PostMapping("/createuser")
    public ResponseEntity createUser(@RequestBody User user){

        try {

            XMPPTCPConnectionConfiguration configuration = XMPPTCPConnectionConfiguration.builder()
                    .setUsernameAndPassword(user.getUsername(), user.getPassword())
                    .setHostAddress(InetAddress.getByName("127.0.0.1"))
                    .setXmppDomain(JidCreate.domainBareFrom("localhost"))
                    .setResource("Admin")
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .setCompressionEnabled(true)
                    .setConnectTimeout(300000)
                    .build();

            connection = new XMPPTCPConnection(configuration);
            connection.connect();
            logger.info("connection successful!");
            connection.login();
            logger.info("login successful!");
            connection.disconnect();
            logger.info("disconnected");
        }catch (Exception ex){
            ex.printStackTrace();
            logger.warn(ex.getMessage());
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("User Successfully Created.");
    }
}
