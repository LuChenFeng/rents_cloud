package pers.lcf.rents.utils;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @ClassName RentsUtil
 * @Deacription TODO
 * @Author lcf
 * @Date 2019/11/12 12:53
 **/
public class RentsUtil {
    public static String[] selectTime(String[] times) {
        if (times[0] != null && !("".equals(times[0]))
                && times[1] != null && !("".equals(times[1]))) {
            Date dateBegin = DateUtil.parse(times[0]);
            Date dateEnd = DateUtil.parse(times[1]);
            if (dateBegin.compareTo(dateEnd) > 0) {
                String date;
                date = times[0];
                times[0] = times[1];
                times[1]= date;
            }
        } else {
            times[0] = null;
            times[1]= null;
        }
        return times;
    }
}
