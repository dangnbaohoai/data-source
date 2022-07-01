package com.c1se44.school_connect.entity.relationship;

import com.c1se44.school_connect.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "member_notify")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class memberNotify extends baseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long notifyId;
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "user_id",referencedColumnName="id", foreignKey=@ForeignKey(name = "Fk_user_notify"))
	private userEntity userNotifyId;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "notify_id",referencedColumnName="id", foreignKey=@ForeignKey(name = "Fk_notify_user"))
	private notifyEntity notifyUserId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private StatusName status;
	
	public memberNotify(userEntity user,notifyEntity notifyEntity){
		this.notifyUserId=notifyEntity;
		this.userNotifyId=user;
		this.status=StatusName.unseen;
	}
}