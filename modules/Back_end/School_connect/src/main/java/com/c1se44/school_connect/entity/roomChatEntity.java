package com.c1se44.school_connect.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room_chat", indexes = @Index(name = "username_index",columnList = "username_recipient"))
@Data
public class roomChatEntity extends baseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long roomId;
	
	@ManyToOne
	@JoinColumn(name = "user_id1", referencedColumnName = "id", foreignKey = @ForeignKey(name = "Fk_user1_chat"))
	private userEntity userChatId1;
	
	@ManyToOne
	@JoinColumn(name = "user_id2", referencedColumnName = "id", foreignKey = @ForeignKey(name = "Fk_user2_chat"))
	private userEntity userChatId2;
	
	@Column(name = "new_message")
	private String newMessage;
	
	@Column(name = "username_recipient")
	private String usernameRecipient;
	
	@Column(name = "message_recipient")
	private String messageRecipient;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status_room")
	private StatusName status;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status_message")
	private StatusName statusMessage;
	
	@OneToOne(mappedBy = "roomChatReportId",fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
	private reportChatEntity reportChatEntity;
	
	@OneToMany(mappedBy = "roomChatMessageId", cascade = CascadeType.ALL)
	private List<chatMessageEntity> chatMessageEntity = new ArrayList<>();
	
}
