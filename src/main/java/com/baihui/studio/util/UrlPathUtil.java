package com.baihui.studio.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * URL路径工具类
 */
public class UrlPathUtil {

    /**
     * URL追加参数
     * 1.在无参URL后追加?
     * 2.在有参URL后追加&
     * 3.通过url中含有?来判断参数情况
     */
    public static String appendParam(String url, String param) {
        if (StringUtils.isEmpty(param)) {
            return url;
        }
        if (url.endsWith("?") || url.endsWith("&")) {
            return url + param;
        }
        return url + (url.indexOf("?") == -1 ? "?" : "&") + param;
    }

    /**
     * 转换成原始的参数字符串
     * 1.没有参数，返回空字符串
     */
    public static String toStringParams(Map<String, String[]> parameterMap) {
        StringBuffer params = new StringBuffer();

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            for (String value : entry.getValue()) {
                params.append(String.format("&%s=%s", entry.getKey(), value));
            }
        }
        return params.length() > 0 ? params.substring(1) : params.toString();
    }

}
