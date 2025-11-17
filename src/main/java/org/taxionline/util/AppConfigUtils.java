package org.taxionline.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppConfigUtils {

    private App app;
    private Server server;
    private DataSource datasource;

    @Getter
    @Setter
    @NoArgsConstructor
    public static final class App {
        private String name;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static final class Server {
        private Integer port;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static final class DataSource {
        private Pool pool;
        private String url;
        private String user;
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static final class Pool {
        private Integer maxSize;
        private Integer minSize;
        private Integer initialSize;
    }
}
