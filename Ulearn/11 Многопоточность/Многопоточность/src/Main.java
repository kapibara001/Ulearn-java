// Using fully-qualified class names for all non-java.lang types below.

public class Main {
    private static final String START_URL = "https://www.lenta.ru/";
    private static final String DOMAIN = "www.lenta.ru";
    private static final String OUTPUT_FILE = "sitemap.txt";

    // Use a cached thread pool for compatibility with older JDKs that
    // don't include virtual threads (Executors.newVirtualThreadPerTaskExecutor()).
    private static final java.util.concurrent.ExecutorService executor = java.util.concurrent.Executors.newCachedThreadPool();
    private static final java.util.Set<String> visited = java.util.concurrent.ConcurrentHashMap.newKeySet();
    private static final java.util.concurrent.ConcurrentLinkedQueue<CrawlTask> queue = new java.util.concurrent.ConcurrentLinkedQueue<>();
    private static final java.util.Map<String, Integer> sitemap = new java.util.concurrent.ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        queue.add(new CrawlTask(normalize(START_URL), 0));
        visited.add(normalize(START_URL));
        sitemap.put(normalize(START_URL), 0);

        while (!queue.isEmpty()) {
            CrawlTask task = queue.poll();
            executor.submit(() -> crawl(task.url, task.depth));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(10, java.util.concurrent.TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        writeSitemap();
    }


    private static void crawl(String url, int depth) {
        try {
            Thread.sleep(100 + java.util.concurrent.ThreadLocalRandom.current().nextInt(51));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            org.jsoup.nodes.Document doc = org.jsoup.Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (compatible; URFU-Student-Bot/1.0)")
                    .timeout(10_000)
                    .get();

            org.jsoup.select.Elements links = doc.select("a[href]");

            for (org.jsoup.nodes.Element link : links) {
                String absUrl = link.absUrl("href");

                if (absUrl.contains("#")) continue;
                if (!absUrl.startsWith("http")) continue;
                if (!absUrl.contains(DOMAIN)) continue;
                if (!absUrl.startsWith("https://www.lenta.ru/") &&
                    !absUrl.startsWith("http://www.lenta.ru/")) continue;

                String normalized = normalize(absUrl);

                if (visited.add(normalized)) {
                    int childDepth = depth + 1;
                    sitemap.put(normalized, childDepth);
                    queue.add(new CrawlTask(normalized, childDepth));
                }
            }
        } catch (java.io.IOException e) {}
    }


    private static void writeSitemap() {
        try {
            java.util.List<java.util.Map.Entry<String, Integer>> sorted = sitemap.entrySet().stream()
                    .sorted(java.util.Comparator.comparingInt(java.util.Map.Entry::getValue))
                    .toList();

            StringBuilder sb = new StringBuilder();
            for (var entry : sorted) {
                sb.append("\t".repeat(Math.max(0, entry.getValue())))
                  .append(entry.getKey())
                  .append("\n");
            }

            java.nio.file.Files.writeString(java.nio.file.Paths.get(OUTPUT_FILE), sb.toString());
        } catch (java.io.IOException e) {}
    }

    private static String normalize(String url) {
        return url.split("\\?")[0].split("#")[0].replaceAll("/+$", "");
    }

    // Внутренний класс для задачи
    private record CrawlTask(String url, int depth) {}
}