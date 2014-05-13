package com.baihui.studio.utils.serial;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * TierSerial工具类
 */
public class TierSerials {


    /**
     * 解析排序号获取TierSerial
     * //TODO 关于解析时传入count的严格限定
     */
    public static TierSerial parse(long serial, int length) {
        TierSerial tierSerial = new TierSerial();

        //转换为string
        String serialstr = String.valueOf(serial);
        int remainder = serialstr.length() % length;
        if (remainder != 0) {
            int differ = length - remainder;
            serialstr = StringUtils.repeat("0", differ) + serialstr;
        }

        //设置count和length
        tierSerial.setCount(serialstr.length() / length);
        tierSerial.setLength(length);
        tierSerial.setValues(new ArrayList<Integer>(tierSerial.getCount()));
        for (int i = 0; i < serialstr.length(); i = i + length) {
            tierSerial.getValues().add(Integer.parseInt(serialstr.substring(i, i + length)));
        }

        /*设置value和level*/
        //设置默认的value和level为values的最后一个
        tierSerial.setLevel(tierSerial.getCount());
        //设置实际找到的value和level，实际未找到即=最后一个=默认的
        for (int i = 0; i < tierSerial.getValues().size(); i++) {
            if (tierSerial.getValues().get(i) == 0) {
                tierSerial.setLevel(i);
                break;
            }
        }

        return tierSerial;
    }

    public static void increaseValue(TierSerial tierSerial, int number) {
        int newValue = tierSerial.getValue() + number;
        int min = 1;
        int max = (int) Math.pow(10, tierSerial.getLength());
        if (newValue >= min && newValue <= max) {
            tierSerial.getValues().set(tierSerial.getLevel() - 1, newValue);
        } else {
            throw new OverSerialTierContentException(newValue, min, max);
        }
    }

    public static int generatStartSerial(int count, int length) {
        return (int) Math.pow(10, (count - 1) * length);
    }


}
