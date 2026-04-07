package com.ho.edcustom.repository;

import com.ho.edcustom.DTO.LikeDTO;
import com.ho.edcustom.entity.Like;
import com.ho.edcustom.entity.Member;
import com.ho.edcustom.entity.SharedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    Optional<Like> findByMemberAndSharedItem(Member member, SharedItem sharedItem);
    //@Query("SELECT new com.ho.edcustom.DTO.LikeDTO(l.id, l.member.id, l.sharedItem.id) " +
    //        "FROM Like l WHERE l.member.id = :memberId")
    @Query("SELECT new com.ho.edcustom.DTO.LikeDTO(l.member.id, l.sharedItem.id) " +
            "FROM Like l WHERE l.member.id = :memberId")
    List<LikeDTO> findDTOByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT l.sharedItem FROM Like l WHERE l.member.id = :memberid")
    List<SharedItem> findSharedItemsByMemberId(@Param("memberid") Long memberid);

}
