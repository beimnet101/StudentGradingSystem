//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.example.config;


import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@ApplicationScoped
public class ConfigurationProducer {
    private Properties properties;

    public ConfigurationProducer() {
    }

    @PostConstruct
    public void init() {
        this.properties = new Properties();
        InputStream stream = ConfigurationProducer.class.getResourceAsStream("/application.properties");
        if (stream == null) {
            throw new RuntimeException("Cannot find application.properties configuration file.");
        } else {
            try {
                this.properties.load(stream);
            } catch (IOException var3) {
                throw new RuntimeException("Configuration file cannot be loaded.");
            }
        }
    }

    @Produces
    @Configurable
    public String produceString(InjectionPoint ip) {
        return this.properties.getProperty(this.getKey(ip));
    }

    @Produces
    @Configurable
    public Integer produceInteger(InjectionPoint ip) {
        return Integer.valueOf(this.properties.getProperty(this.getKey(ip)));
    }

    @Produces
    @Configurable
    public Long produceLong(InjectionPoint ip) {
        return Long.valueOf(this.properties.getProperty(this.getKey(ip)));
    }

    @Produces
    @Configurable
    public Boolean produceBoolean(InjectionPoint ip) {
        return Boolean.valueOf(this.properties.getProperty(this.getKey(ip)));
    }

    private String getKey(InjectionPoint ip) {
        return ((Configurable)ip.getAnnotated().getAnnotation(Configurable.class)).value();
    }
}
