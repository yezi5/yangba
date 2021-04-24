package com.guli.commonutils;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.springframework.util.StringUtils;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.commonutils
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/25 星期四 21:52
 */
public class HtmlUtils {

    public static String convert(String html)
    {
        if (StringUtils.isEmpty(html))
        {
            return "";
        }

        Document document = Jsoup.parse(html);
        Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
        document.outputSettings(outputSettings);
        document.select("br").append("\\n");
        document.select("p").prepend("\\n");
        document.select("p").append("\\n");
        String newHtml = document.html().replaceAll("\\\\n", "\n");
        String plainText = Jsoup.clean(newHtml, "", Whitelist.none(), outputSettings);
        String result = StringEscapeUtils.unescapeHtml(plainText.trim());
        return result;
    }
}
