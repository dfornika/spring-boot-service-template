package ca.bccdcphl.service.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.Agent;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.InputStreamReader;
import java.util.Objects;

@Component
@Profile("dev")
public class NreplServer {
    static final Logger log = LoggerFactory.getLogger(NreplServer.class);
    // public so that we can access it from Clojure
    public final static int port = 55555;

    @Autowired
    private ApplicationContext ctx;

    private final IFn symbol = Clojure.var("clojure.core", "symbol");
    private final IFn intern = Clojure.var("clojure.core", "intern");
    private final IFn stopReplServer = Clojure.var("user", "stop-repl-server");
    private final Object symUser = symbol.invoke("user");
    public NreplServer () {

    }

    @PostConstruct
    public void start() {
        String res = "";
        res += this.intern.invoke(this.symUser, this.symbol.invoke("spring-app-ctx"), this.ctx);
        res += this.intern.invoke(this.symUser, this.symbol.invoke("nrepl-port"), port);
        res += this.intern.invoke(this.symUser, this.symbol.invoke("nrepl-server"), this);

        // Run the init code and start the server
        IFn loadString = Clojure.var("clojure.core", "load-reader");
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("clojure_repl_init.clj")));
        res += "|" + loadString.invoke(reader);
        log.info("nREPL Server started at port: " + port);
    }
    @PreDestroy
    public void stop() {
        // Here we use `stop-repl-server` defined in user.clj:
        stopReplServer.invoke();
        Agent.soloExecutor.shutdown();
    }

    /** Called from Clojure when we screw up and need to reset vars etc */
    public void reset() {
        // Calling start is enough; the server will not be started again thanks to `defonce`
        start();
    }
}