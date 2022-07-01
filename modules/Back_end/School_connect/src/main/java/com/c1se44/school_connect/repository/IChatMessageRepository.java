package com.c1se44.school_connect.repository;

import com.c1se44.school_connect.entity.chatMessageEntity;
import com.c1se44.school_connect.entity.roomChatEntity;
import com.c1se44.school_connect.entity.userEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IChatMessageRepository extends JpaRepository<chatMessageEntity,Long> {
	Page<chatMessageEntity> findByRoomChatMessageId(roomChatEntity roomChatId, Pageable pageable);
	@Query(value = "select * from chat_message where room_id=? AND id>? ORDER BY create_date ",nativeQuery = true)
	List<chatMessageEntity> findByRoomChatMessageIdAndIdChatBigThan(Long roomChatId,Long chatId);
	@Query(value = "select m from chatMessageEntity m where m.roomChatMessageId=?1 AND m.messageId>?2 ORDER BY m.createDate ")
	List<chatMessageEntity> findByRoomChatMessageIdAndIdChatBigThan2(roomChatEntity roomChatId,Long chatId);
	@Modifying
	@Transactional
	@Query(value = "Delete from chatMessageEntity where messageId=?1 and userSendMessageId=?2")
	void deleteByMessageIdAndUserSendMessageId(Long messageId,userEntity userId);
	chatMessageEntity findByMessageId(Long messageId);
	Boolean existsByMessageIdAndUserSendMessageId(Long chatId, userEntity user);
}
