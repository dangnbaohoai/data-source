package com.c1se44.school_connect.entity;

import com.c1se44.school_connect.DTO.Request.userRequest;
import com.c1se44.school_connect.entity.relationship.memberEvent;
import com.c1se44.school_connect.entity.relationship.memberForums;
import com.c1se44.school_connect.entity.relationship.memberNotify;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "users", uniqueConstraints = {
            @UniqueConstraint(columnNames = {
                        "userName"
            }),
            @UniqueConstraint(columnNames = {
                        "email"
            }),
            @UniqueConstraint(columnNames = {
                        "code_user"
            })
}// thêm cột không cho phép trùng dữ liệu
)
@NoArgsConstructor
public class userEntity extends baseEntity implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long userid;
  
  // @NaturalId// đây là 1 id tự nhiên
  @Column(name = "code_user", nullable = false)
  private Long code;
  
  @NotBlank// không cho phép trống
  @Column(name = "username")
  private String username;
  
  @NotBlank
  @JsonIgnore// không cho phép truyền dữ liệu ra ngoài client
  @Column(name = "passwords")
  private String password;
  
  @Column(name = "full_name", columnDefinition = "TEXT NULL")
  private String fullName;
  
  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;
  
  @Column(name = "gender")
  private String gender;
  
  @Column(name = "address_of_user")
  private String addressOfUser;
  
  @Column(name = "number_phone")
  private String numberPhone;
  
  @Email
  @Column(name = "email")
  private String email;
  
  @Column(name = "position")
  private String position;
  
  @Column(name = "avatar", columnDefinition = "TEXT")
  private String avatar;
  
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private StatusName status;
  
  @ManyToMany(fetch= FetchType.EAGER)
  @JoinTable(
              name = "users_roles",
              joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "Fk_user_role")),
              inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "Fk_role_user")))
  private Set<roleEntity> roles = new HashSet<>();
  
  @OneToMany(mappedBy = "userPostId", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  private List<postsEntity> ListPosts = new ArrayList<>();
  
  @OneToMany(mappedBy = "censorId", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  private List<forumsEntity> forumsEntity=new ArrayList<>();
  
  @OneToMany(mappedBy = "userForumId", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  private List<memberForums> memberForums = new ArrayList<>();
  
  @OneToMany(mappedBy = "userCommentId", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  private List<commentEntity> commentEntity = new ArrayList<>();
  
  @OneToMany(mappedBy = "userEventId", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  private List<memberEvent> memberEvent = new ArrayList<>();
  
  @OneToMany(mappedBy = "userNotifyId", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  private List<memberNotify> memberNotify = new ArrayList<>();
  
  @OneToMany(mappedBy = "userCommentsReportId", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  private List<reportCommentEntity> reportCommentEntity = new ArrayList<>();
  
  @OneToMany(mappedBy = "userPostReportId", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  private List<reportPostEntity> reportPostEntity = new ArrayList<>();
  
  @OneToMany(mappedBy = "userChatId1", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  private List<roomChatEntity> roomChatEntity1 = new ArrayList<>();
  
  @OneToMany(mappedBy = "userChatId2", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  private List<roomChatEntity> roomChatEntity2 = new ArrayList<>();
  
  @OneToMany(mappedBy = "userChatReportId", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  private List<reportChatEntity> reportChatEntity = new ArrayList<>();
  
  @OneToMany(mappedBy = "userSendMessageId", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  private List<chatMessageEntity> chatMessageEntity = new ArrayList<>();
  
  public userEntity(String fullName, String username, String email, String password) {
    this.username = username;
    this.password = password;
    this.fullName = fullName;
    this.email = email;
  }
  
  public userEntity(userRequest userRequest, String password) {
    this.userid = userRequest.getUserId();
    this.code = userRequest.getCode();
    this.password = password;
    this.username = userRequest.getUserName();
    this.fullName = userRequest.getFullName();
    this.email = userRequest.getEmail();
    this.addressOfUser = userRequest.getAddressOfUser();
    this.gender = userRequest.getGender();
    this.dateOfBirth = userRequest.getDateOfBirth();
    this.status = StatusName.is_active;
    this.avatar = userRequest.getAvatar();
    this.numberPhone = userRequest.getNumberPhone();
    this.position = userRequest.getPosition();
  }
  
  public userEntity(userRequest userRequest) {
    this.userid = userRequest.getUserId();
    this.code = userRequest.getCode();
    this.username = userRequest.getUserName();
    this.fullName = userRequest.getFullName();
    this.email = userRequest.getEmail();
    this.addressOfUser = userRequest.getAddressOfUser();
    this.gender = userRequest.getGender();
    this.dateOfBirth = userRequest.getDateOfBirth();
    this.status = StatusName.is_active;
    this.avatar = userRequest.getAvatar();
    this.numberPhone = userRequest.getNumberPhone();
    this.position = userRequest.getPosition();
  }
  
}
