package com.guli.blog.util;

import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

/**
 * @author 叶子
 * @Description 请设置
 * @PackageName com.guli.blog.util
 * @DevelopmentTools IntelliJ IDEA
 * @Data 2021/3/24 星期三 18:02
 */
@Component
public class StringUtil {

    /**
     * 将html文本转义，避免数据库存储问题
     * @param html
     * @return
     */
    public static String encode(String html){
        return HtmlUtils.htmlEscape(html);
    }

    /**
     * 将转义过的html文本重新转义为html文本
     * @param target
     * @return
     */
    public static String decode(String target){
        return HtmlUtils.htmlUnescape(target);
    }
}
