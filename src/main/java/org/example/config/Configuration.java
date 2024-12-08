package org.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import java.util.ResourceBundle;

@Singleton
public class Configuration {
    private static final Logger log = LoggerFactory.getLogger(Configuration.class);;
    private String ruleurl;
    private String queue;
    private String usernameAMQ;
    private String passwordAMQ;
    private String urlACMQ;
    private String queue1;
    private String publicKey;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getQueue1() {
        return queue1;
    }

    public void setQueue1(String queue1) {
        this.queue1 = queue1;
    }

    public String getUsernameAMQ() {
        return usernameAMQ;
    }

    public void setUsernameAMQ(String usernameAMQ) {
        this.usernameAMQ = usernameAMQ;
    }

    public String getPasswordAMQ() {
        return passwordAMQ;
    }

    public void setPasswordAMQ(String passwordAMQ) {
        this.passwordAMQ = passwordAMQ;
    }

    public String getUrlACMQ() {
        return urlACMQ;
    }

    public void setUrlACMQ(String urlACMQ) {
        this.urlACMQ = urlACMQ;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }


    public String getRuleurl() {
        return ruleurl;
    }

    public void setRuleurl(String ruleurl) {
        this.ruleurl = ruleurl;
    }



    @PostConstruct
    public void init() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("application");

            ruleurl = bundle.getString("act.ruleurl");
            queue = bundle.getString("activemq.ms2.queue-name");
            urlACMQ = bundle.getString("activemq.ms2.broker-url");
            usernameAMQ = bundle.getString("activemq.ms2.user");
            passwordAMQ = bundle.getString("activemq.ms2.password");
            queue1 = bundle.getString("act.student.info.activemq.queue");
            publicKey=bundle.getString("publicKey");
        } catch (Exception e) {
            log.info("Exception occurred while reading properties: " + e.getMessage());
        }
    }
}