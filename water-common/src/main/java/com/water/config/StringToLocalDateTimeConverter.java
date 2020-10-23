package com.water.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

/***
 * 
 * @ClassName:  StringToLocalDateTimeConverter   
 * @Description:字符串转日期Converter   
 * @author: 张凯强
 * @date:   Mar 2, 2020 6:46:12 PM
 */
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

	@Override
	public LocalDateTime convert(String source) {

		LocalDateTime localDateTime = null;

		if (StringUtils.isNotEmpty(source)) {
			int length = source.length();
			if (length == 19) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				localDateTime = LocalDateTime.parse(source, formatter);

			} else if (length == 10) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				localDateTime = LocalDateTime.parse(source, formatter);
			}
		}

		return localDateTime;
	}

}
