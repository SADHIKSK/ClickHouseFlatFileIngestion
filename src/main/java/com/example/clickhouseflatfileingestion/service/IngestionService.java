public class IngestionService {

    @Autowired
    private DataSource clickHouseDataSource;

    public int ingestCSVToClickHouse(List<String[]> csvData, String tableName, List<String> columns) throws SQLException {
        String insertSQL = buildInsertSQL(tableName, columns);
        int count = 0;

        try (Connection conn = clickHouseDataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            for (String[] row : csvData.subList(1, csvData.size())) {
                for (int i = 0; i < columns.size(); i++) {
                    stmt.setString(i + 1, row[i]);
                }
                stmt.addBatch();
                count++;
            }
            stmt.executeBatch();
        }
        return count;
    }

    private String buildInsertSQL(String tableName, List<String> columns) {
        String cols = String.join(", ", columns);
        String placeholders = columns.stream().map(col -> "?").collect(Collectors.joining(", "));
        return "INSERT INTO " + tableName + " (" + cols + ") VALUES (" + placeholders + ")";
    }

    public int exportClickHouseToCSV(String query, File file) throws SQLException, IOException {
        try (Connection conn = clickHouseDataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             CSVWriter writer = new CSVWriter(new FileWriter(file))) {

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();
            String[] header = new String[colCount];

            for (int i = 0; i < colCount; i++) {
                header[i] = meta.getColumnName(i + 1);
            }
            writer.writeNext(header);

            int rowCount = 0;
            while (rs.next()) {
                String[] row = new String[colCount];
                for (int i = 0; i < colCount; i++) {
                    row[i] = rs.getString(i + 1);
                }
                writer.writeNext(row);
                rowCount++;
            }
            return rowCount;
        }
    }
}
