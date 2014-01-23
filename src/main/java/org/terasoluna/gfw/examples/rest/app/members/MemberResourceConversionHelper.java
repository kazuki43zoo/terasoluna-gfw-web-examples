package org.terasoluna.gfw.examples.rest.app.members;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.examples.common.domain.model.Member;
import org.terasoluna.gfw.examples.common.domain.repository.MemberSearchCriteria;

/**
 * Helper to be provided conversion API for member resource.
 */
@Component
public class MemberResourceConversionHelper {

    /**
     * Mapper of java bean.
     */
    @Inject
    Mapper beanMapper;

    /**
     * Convert to a search criteria of member entity from a search query of
     * member resource.
     * 
     * @param query
     *            search query of members.
     * @return converted search criteria of member entity.
     */
    public final MemberSearchCriteria toMemberSearchCriteria(
            final MembersSearchQuery query) {
        final MemberSearchCriteria criteria = beanMapper.map(query,
                MemberSearchCriteria.class);
        return criteria;
    }

    /**
     * Convert to a collection of member resource from a page object of member
     * entity.
     * 
     * @param page
     *            page object of member.
     * @return converted collection of member resource.
     */
    public final List<MemberResource> toMemberResourceList(final Page<Member> page) {
        List<MemberResource> members = new ArrayList<MemberResource>();
        for (final Member member : page.getContent()) {
            final MemberResource memberResource = beanMapper.map(member,
                    MemberResource.class);
            members.add(memberResource);
        }
        return members;
    }

    /**
     * Convert to a member resource from member entity.
     * 
     * @param member
     *            member entity.
     * @return converted member resource
     */
    public final MemberResource toMemberResource(final Member member) {
        final MemberResource memberResource = beanMapper.map(member,
                MemberResource.class);
        return memberResource;
    }

    /**
     * Convert to a member entity from a member resource.
     * 
     * @param memberResource
     *            member resource.
     * @return converted member entity.
     */
    public final Member toMember(final MemberResource memberResource) {
        final Member member = beanMapper.map(memberResource, Member.class);
        return member;
    }

}
