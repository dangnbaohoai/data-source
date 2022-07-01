package com.c1se44.school_connect.entity.relationship;

import com.c1se44.school_connect.entity.*;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "member_event")
@Data
public class memberEvent extends baseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "user_id",referencedColumnName="id", foreignKey=@ForeignKey(name = "Fk_user_event"))
	private userEntity userEventId;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "event_id",referencedColumnName="id", foreignKey=@ForeignKey(name = "Fk_event_user"))
	private postsEntity eventUserId;
	
	@Column(name = "date_of_event")
	private LocalDate dateOfEvent;
	
}
