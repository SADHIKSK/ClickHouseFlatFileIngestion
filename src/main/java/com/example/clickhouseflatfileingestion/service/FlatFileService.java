
public class FlatFileService {

    public List<String[]> parseCSV(MultipartFile file, char delimiter) throws IOException {
        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream()))
                .withCSVParser(new CSVParserBuilder().withSeparator(delimiter).build())
                .build()) {
            return reader.readAll();
        }
    }
}
