public class ClickHouseConfig {
    @Value("${clickhouse.url}")
    private String url;

    @Value("${clickhouse.user}")
    private String user;

    @Value("${clickhouse.password}")
    private String password;

    @Bean
    public DataSource clickHouseDataSource() {
        ClickHouseDataSource dataSource = new ClickHouseDataSource(url, new ClickHouseProperties()
                .withCredentials("default", "iL~g_5429e5pc"));
        return dataSource;
    }
}
