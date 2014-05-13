package com.baihui.studio.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * URL规则
 *
 * @author xiayouxue
 * @date 2014/5/10
 */
public class UrlRule {

    /**
     * 将uri转换为页面跳转形式
     */
    public static String toPage(String uri) {
        String funcUrl = uri;
        String regex = "(to([a-zA-Z]+))Page";
        if (!uri.matches(regex)) {
            int index = uri.indexOf(".");
            String[] parts = uri.substring(0, index).split("/");
            parts[parts.length - 1] = "to" + StringUtils.capitalize(parts[parts.length - 1]) + "Page";
            funcUrl = StringUtils.join(parts, "/") + funcUrl.substring(index);
        }
        return funcUrl;
    }

    /**
     * 将uri转换为执行形式
     */
    public static String exec(String uri) {
        String funcUrl = uri;
        Pattern pattern = Pattern.compile("(to([a-zA-Z]+))Page");
        Matcher matcher = pattern.matcher(uri);
        if (matcher.find()) {
            StringBuffer funcUrlBuffer = new StringBuffer();
            matcher.appendReplacement(funcUrlBuffer, StringUtils.uncapitalize(matcher.group(2)));
            matcher.appendTail(funcUrlBuffer);
            funcUrl = funcUrlBuffer.toString();
        }
        return funcUrl;
    }
}
