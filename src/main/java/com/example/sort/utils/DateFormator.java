package com.example.sort.utils;

public enum DateFormator {
	YEAR_MONTH_DAY_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),YEAR_MONTH_DAY_HH_MM("yyyyMMdd.HHmm"),HH_MM_SS("HH:mm:ss"), YEAR_MONTH("yyyy-MM"), SPLIT_CHAR("-"), YEAR_MONTH_DAY("yyyy-MM-dd"), MONTH_DAY(
			"MM-dd"), YEAR_MONTH_EU("yyyy/MM"), YEARMONTHDAYHHMMSS("yyyyMMddHHmmss"), MONTH_DAY_YEAR_EU("MM/dd/yyyy"), YEAR_MONTH_DAY_EU("yyyy/MM/dd"), YEAR_MONTH_DAY_H_M_S_EU(
			"yyyy/MM/dd HH:mm:ss");
	private String str;

	private DateFormator(String s) {
		this.str = s;
	}

	@Override
	public String toString() {
		return str;
	}

}
