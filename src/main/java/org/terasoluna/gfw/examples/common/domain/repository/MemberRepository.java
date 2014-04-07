package org.terasoluna.gfw.examples.common.domain.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.terasoluna.gfw.examples.common.domain.model.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Member findOne(String id);
}
