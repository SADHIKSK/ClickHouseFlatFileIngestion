public class MainController {

    @Autowired
    private FlatFileService flatFileService;

    @Autowired
    private IngestionService ingestionService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadCSV(@RequestParam("file") MultipartFile file,
                            @RequestParam("delimiter") char delimiter,
                            @RequestParam("tableName") String tableName,
                            @RequestParam("columns") List<String> columns,
                            Model model) throws IOException, SQLException {

        List<String[]> data = flatFileService.parseCSV(file, delimiter);
        int count = ingestionService.ingestCSVToClickHouse(data, tableName, columns);
        model.addAttribute("message", count + " records ingested.");
        return "result";
    }

    @PostMapping("/export")
    public String exportCSV(@RequestParam("query") String query, Model model) throws Exception {
        File output = new File("output.csv");
        int count = ingestionService.exportClickHouseToCSV(query, output);
        model.addAttribute("message", count + " records exported to output.csv.");
        return "result";
    }
}
