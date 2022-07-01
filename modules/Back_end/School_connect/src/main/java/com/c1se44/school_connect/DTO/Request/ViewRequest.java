package com.c1se44.school_connect.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewRequest {
	private int page;
	// tổng số trang
	private int totalPage;
	private int limitAdmin=5;
	private int limitUser=10;
	private String sortBy;
}
