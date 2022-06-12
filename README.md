# tinyspider

### 使用示例

```java 
@Selector(value = "script[body]", localDir = "C:/Users/edz/Desktop/music", download = true, downloader = Dj72MediaDownloader.class, parser = Dj72ElementParser.class)
@Site(host = "http://www.72dj.com", hostContain = "72dj.com", threads = 10)
public class DemoPageProcessor extends AbstractPageProcessor {
    private final Logger logger = Logger.getLogger(this.getClass());
    @Override
    public void process(Document doc) throws Exception {
        logger.info("process url:{}", doc.location());
        Set<String> musicUrls = select(doc);

    }
    public static void main(String[] args) throws Exception {
        new Scheduler(new DemoPageProcessor()).start();
    }
}

```



