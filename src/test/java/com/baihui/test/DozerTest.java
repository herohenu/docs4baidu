package com.baihui.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class DozerTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private DozerBeanMapper dozer = new DozerBeanMapper();
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void before() {

    }

    @Test
    public void initTest() throws JsonProcessingException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("a", "b");
//        Map<String, String> mapCopy = dozer.map(map, Map.class);
//        Assert.assertTrue(mapCopy.containsKey("a"));
//        Assert.assertEquals("测试Map拷贝", mapper.writeValueAsString(map), mapper.writeValueAsString(mapCopy));
    }


    @Test
    public void testEncoding() throws Exception {
        String name = "/adfff fds afsa";
        String encodeName = URLEncoder.encode(name, "utf-8");
        System.out.println(String.format("encodeName=%s", encodeName));
        String encodeEncodeName = URLEncoder.encode(encodeName, "utf-8");
        System.out.println(String.format("encodeEncodeName=%s", encodeEncodeName));
    }
}
