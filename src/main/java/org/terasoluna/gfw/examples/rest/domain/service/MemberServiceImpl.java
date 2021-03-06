package org.terasoluna.gfw.examples.rest.domain.service;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.examples.common.domain.model.Member;
import org.terasoluna.gfw.examples.common.domain.repository.MemberRepository;
import org.terasoluna.gfw.examples.common.domain.repository.MemberSearchCriteria;

@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    @Inject
    MemberRepository memberRepository;

    @Inject
    Mapper beanMapper;

    @Override
    public Page<Member> searchMembers(MemberSearchCriteria criteria, Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    @Override
    public Member getMember(String memberId) {
        final Member member = memberRepository.findOne(memberId);
        if (member == null) {
            throw new ResourceNotFoundException("Member is not found. memberId [" + memberId + "].");
        }
        return member;
    }

    @Override
    public Member createMember(Member newMember) {
        memberRepository.save(newMember);
        return newMember;
    }

    @Override
    public Member updateMember(String memberId, Member newMember) {
        final Member member = getMember(memberId);
        beanMapper.map(newMember, member);
        memberRepository.save(member);
        return member;
    }

    @Override
    public void deleteMember(String memberId) {
        final Member member = memberRepository.findOne(memberId);
        if (member != null) {
            memberRepository.delete(member);
        }
    }

}
