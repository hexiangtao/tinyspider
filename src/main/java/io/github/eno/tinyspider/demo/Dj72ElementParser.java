package io.github.eno.tinyspider.demo;

import io.github.eno.tinyspider.page.ElementParser;
import io.github.eno.tinyspider.util.StringUtil;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author hexiangtao
 * @date 2022/6/11 15:07
 */
public class Dj72ElementParser implements ElementParser {

    public static final String MP_URL = "//http://www.72dj.com/flash/mp.js";

    public static final String PREFIX = "https://p21.72dj.com:37373/m4adj/";

    private static final Pattern REGEX = Pattern.compile(".*danceFilePath=\"(.*)\".*");

    @Override
    public String select(Element ele) {
        String body = ele.data();
        if (StringUtil.isBlank(body)) {
            return "";
        }
        String[] lines = body.split(";");
        String line = Arrays.stream(lines).filter(Objects::nonNull).filter(o -> o.contains("danceFilePath")).findFirst().orElse("");
        if (StringUtil.isBlank(line)) {
            return "";
        }
        String danceFilePath = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
        return danceFilePath + ".m4a";
    }


}
