package com.c1se44.school_connect.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "roles")

public class roleEntity extends baseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long roleId;
	
	@Column(name = "name_role")
	private String name;
	
	@Enumerated(EnumType.STRING)
	@NaturalId
	@Column(name = "code_role")
	private RoleName code;
	
	@ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
	private List<userEntity> rolesUser =new ArrayList<>();
	
	public roleEntity(String name, RoleName codeRole) {
		this.name = name;
		this.code = codeRole;
	}
	
	public roleEntity() {}
}
