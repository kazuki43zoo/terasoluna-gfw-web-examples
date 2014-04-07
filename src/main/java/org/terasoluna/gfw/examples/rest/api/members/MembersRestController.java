package org.terasoluna.gfw.examples.rest.api.members;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.groups.Default;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.terasoluna.gfw.examples.common.domain.model.Member;
import org.terasoluna.gfw.examples.common.domain.repository.MemberSearchCriteria;
import org.terasoluna.gfw.examples.rest.api.common.http.ResponseCache;
import org.terasoluna.gfw.examples.rest.api.common.http.ResponseCache.CacheType;
import org.terasoluna.gfw.examples.rest.api.common.http.ResponseUtils;
import org.terasoluna.gfw.examples.rest.api.common.resource.ApiValidationGroups.PostResources;
import org.terasoluna.gfw.examples.rest.api.common.resource.ApiValidationGroups.PutResource;
import org.terasoluna.gfw.examples.rest.api.common.resource.hateoas.Link;
import org.terasoluna.gfw.examples.rest.api.common.resource.hateoas.LinkBuilder;
import org.terasoluna.gfw.examples.rest.api.common.resource.hateoas.LinksSupportedPageResource;
import org.terasoluna.gfw.examples.rest.domain.service.MemberService;

/**
 * Controller to be provided RESTful Web Service API of member resource.
 */
@RequestMapping(value = "members")
@Controller
public class MembersRestController {

    @Inject
    MemberService memberService;

    @Inject
    LinkBuilder linkBuilder;

    @Inject
    Mapper beanMapper;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.HEAD })
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ResponseCache(cacheType = CacheType.NO_CACHE)
    public LinksSupportedPageResource<MemberResource> getMembers(
            @Validated MembersSearchQuery query, Pageable pageable) {

        // search collection of member.
        MemberSearchCriteria criteria = beanMapper.map(query, MemberSearchCriteria.class);
        Page<Member> pageOfMember = memberService.searchMembers(criteria, pageable);

        // create response resource.
        List<MemberResource> memberResources = new ArrayList<MemberResource>();
        for (Member member : pageOfMember.getContent()) {
            MemberResource memberResource = beanMapper.map(member, MemberResource.class);
            memberResource.addLink(linkBuilder.self(memberResource.getMemberId()));
            memberResources.add(memberResource);
        }
        LinksSupportedPageResource<MemberResource> responseResource = new LinksSupportedPageResource<>(
                new PageImpl<>(memberResources, pageable, pageOfMember.getTotalElements()),
                linkBuilder.self());

        return responseResource;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseCache
    public HttpEntity<MemberResource> postMembers(@RequestBody @Validated({ Default.class,
            PostResources.class }) MemberResource newResource) {

        // create a new member.
        Member member = beanMapper.map(newResource, Member.class);
        Member createdMember = memberService.createMember(member);

        // create response resource.
        MemberResource responseResource = beanMapper.map(createdMember, MemberResource.class);
        Link linkOfCreatedResource = linkBuilder.created(responseResource.getMemberId());
        responseResource.addLink(linkOfCreatedResource);

        return ResponseUtils.createHttpEntityOfPost(responseResource,
                linkOfCreatedResource.getHref());
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Void> optionsMembers() {
        return ResponseUtils.createHttpEntityOfOptions(HttpMethod.GET, HttpMethod.HEAD,
                HttpMethod.POST);
    }

    @RequestMapping(value = "{memberId}", method = { RequestMethod.GET, RequestMethod.HEAD })
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ResponseCache
    public MemberResource getMember(@PathVariable("memberId") String memberId) {

        // search the specified member.
        Member member = memberService.getMember(memberId);

        // create response resource.
        MemberResource responseResource = beanMapper.map(member, MemberResource.class);
        responseResource.addLink(linkBuilder.self());

        return responseResource;
    }

    @RequestMapping(value = "{memberId}", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ResponseCache
    public MemberResource putMember(
            @PathVariable("memberId") String memberId,
            @RequestBody @Validated({ Default.class, PutResource.class }) MemberResource updatedResource) {

        // update the specified member.
        Member member = beanMapper.map(updatedResource, Member.class);
        Member updatedMember = memberService.updateMember(memberId, member);

        // create response resource.
        MemberResource responseResource = beanMapper.map(updatedMember, MemberResource.class);
        responseResource.addLink(linkBuilder.self());

        return responseResource;
    }

    @RequestMapping(value = "{memberId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseCache(cacheType = CacheType.NO_CACHE)
    public void deleteMember(@PathVariable("memberId") String memberId) {

        // delete the specified member.
        memberService.deleteMember(memberId);

    }

    @RequestMapping(value = "{memberId}", method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Void> optionsMember(@PathVariable("memberId") String memberId) {

        // search the specified member.
        memberService.getMember(memberId);

        return ResponseUtils.createHttpEntityOfOptions(HttpMethod.GET, HttpMethod.HEAD,
                HttpMethod.PUT, HttpMethod.DELETE);
    }

}
