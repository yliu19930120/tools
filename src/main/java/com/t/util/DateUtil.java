package com.t.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
* @author Liu Yonghua
* @Email liuyonghua@xinpibao.com
* @date 2018年6月6日  下午3:08:45
 */
public class DateUtil {

	public static String dateToStr(Date date,String forMat) {
		SimpleDateFormat sdf = new SimpleDateFormat(forMat);
		return sdf.format(date);
	}
}
