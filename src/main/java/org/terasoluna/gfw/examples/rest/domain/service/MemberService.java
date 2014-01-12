package org.terasoluna.gfw.examples.rest.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.gfw.examples.common.domain.model.Member;
import org.terasoluna.gfw.examples.common.domain.repository.MemberSearchCriteria;

public interface MemberService {

    Page<Member> searchMembers(MemberSearchCriteria criteria, Pageable pageable);

    Member getMember(String memberId);

    Member createMember(Member newMember);

    Member updateMember(String memberId, Member newMember);

    void deleteMember(String memberId);

}
