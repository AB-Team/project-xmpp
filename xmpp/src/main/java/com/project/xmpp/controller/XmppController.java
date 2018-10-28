package com.project.xmpp.controller;

import com.project.xmpp.model.User;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/xmpp")
public class XmppController {

    Logger logger = LoggerFactory.getLogger(XmppController.class);
    AbstractXMPPConnection connection;

    @PostMapping("/createuser")
    public ResponseEntity createUser(@RequestBody User user){
        Map response = new HashMap(3, 1);

        try {

            XMPPTCPConnectionConfiguration configuration = XMPPTCPConnectionConfiguration.builder()
                    .setUsernameAndPassword(user.getUsername(), user.getPassword())
//                    .setServiceName("localhost")
                    .setHost("localhost")
                    .setPort(8222)
                    .setXmppDomain("localhost")
//                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled) // Do not disable TLS except for test purposes!
//                    .setDebuggerEnabled(true)
                    .build();

            connection = new XMPPTCPConnection(configuration);
            connection.connect().login();
        }catch (Exception ex){
            ex.printStackTrace();
            logger.warn(ex.getMessage());
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("User Successfully Created.");
    }
}
