package com.c1se44.school_connect.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class baseResponse {
	private int totalPage;
	private String createBy;
	private LocalDateTime createDate;
	private String modifiedBy;
	private LocalDateTime modifiedDate;
	private List<Object> listResult=new ArrayList<>();
}
