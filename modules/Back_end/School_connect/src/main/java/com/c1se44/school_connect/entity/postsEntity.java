package com.c1se44.school_connect.entity;

import com.c1se44.school_connect.DTO.Request.PostRequest;
import com.c1se44.school_connect.entity.relationship.memberEvent;
import com.c1se44.school_connect.entity.relationship.postForum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "post")
@NoArgsConstructor
public class postsEntity extends baseEntity implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long postId;
  
  @Column(name = "content", columnDefinition = "LONGTEXT")
  private String content;
  
  @Column(name = "name_event")
  private String nameEvent;
 
  @Column(name = "date_of_event")
  private LocalDate dateOfEvent;
 
  @Column(name = "time_of_event")
  private Time timeOfEvent;
  
  @Column(name = "address_of_event")
  private String addressOfEvent;
  
  @Column(name = "number_like")
  private int numberLike;
  
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "user_post", referencedColumnName = "id", foreignKey = @ForeignKey(name = "Fk_user_post"))
  private userEntity userPostId;
  
  @OneToMany(mappedBy = "postImageId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<imagePostEntity> linkImage = new ArrayList<>();
  
  @OneToMany(mappedBy = "postForumsId", cascade = CascadeType.ALL)
  private List<postForum> postForums = new ArrayList<>();
  ;
  @OneToMany(mappedBy = "eventUserId", cascade = CascadeType.ALL)
  private List<memberEvent> memberEvent = new ArrayList<>();
  
  @OneToOne(mappedBy = "postReportId", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  private reportPostEntity reportPostEntity;
  
  @OneToMany(mappedBy = "postNotify", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  private List<notifyEntity> notifyEntity;
  
  @OneToMany(mappedBy = "postCommentId", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  private List<commentEntity> commentEntity = new ArrayList<>();
  
  public postsEntity(PostRequest postRequest) {
    this.content = postRequest.getContent();
    this.numberLike=0;
    if (postRequest.getAddressOfEvent() != null) {
      this.addressOfEvent = postRequest.getAddressOfEvent();
    }
    if (postRequest.getDateOfEvent() != null) {
      this.dateOfEvent = postRequest.getDateOfEvent();
    }
    if (postRequest.getTimeOfEvent() !=null){
      this.timeOfEvent = postRequest.getTimeOfEvent();
    }
    if(postRequest.getNameEvent() !=null){
      this.nameEvent= postRequest.getNameEvent();
    }
  }
}
