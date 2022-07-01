package com.c1se44.school_connect.entity;

import com.c1se44.school_connect.DTO.Request.ReportRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "report_room")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class reportChatEntity extends baseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long reportRoomId;
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "Fk_user_chat_report"))
	private userEntity userChatReportId;
	
	@OneToOne
	@JoinColumn(name = "room_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "Fk_chat_report"))
	private roomChatEntity roomChatReportId;
	
	@Column(name = "reason")
	private String reason;
	@OneToOne
	@JoinColumn(name = "message_report", referencedColumnName = "id", foreignKey = @ForeignKey(name = "Fk_message_report"))
	private chatMessageEntity messageReport;
	
	@Column(name = "userReport")
	private String userReport;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusName status;
	
	public reportChatEntity(userEntity user, roomChatEntity forums,chatMessageEntity messsage ,ReportRequest reportRequest) {
		this.userChatReportId = user;
		this.roomChatReportId = forums;
		this.reason = reportRequest.getReason();
		this.messageReport = messsage;
		this.userReport = reportRequest.getUserNameReport();
		this.status=StatusName.have_not_been_censored;
	}
}


