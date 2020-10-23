package com.water.config;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import cn.hutool.core.date.DateUtil;

/***
 * 
 * @ClassName:  StringToDateConverter   
 * @Description:字符串转日期Converter   
 * @author: 张凯强
 * @date:   Mar 2, 2020 6:45:50 PM
 */
public class StringToDateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {

		Date date = null;

		if (StringUtils.isNotEmpty(source)) {
			int length = source.length();
			if (length == 19) {
				date = DateUtil.parse(source, "yyyy-MM-dd HH:mm:ss");

			} else if (length == 10) {
				date = DateUtil.parse(source, "yyyy-MM-dd");
			}
		}

		return date;
	}

}
