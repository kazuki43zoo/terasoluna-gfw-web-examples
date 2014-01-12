package org.terasoluna.gfw.examples.rest.app.members;

import javax.inject.Inject;
import javax.validation.groups.Default;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriTemplate;
import org.terasoluna.gfw.examples.common.domain.model.Member;
import org.terasoluna.gfw.examples.common.domain.repository.MemberSearchCriteria;
import org.terasoluna.gfw.examples.rest.app.common.RestResponseUtils;
import org.terasoluna.gfw.examples.rest.app.members.MemberResource.MemberCreating;
import org.terasoluna.gfw.examples.rest.app.members.MemberResource.MemberUpdating;
import org.terasoluna.gfw.examples.rest.domain.service.MemberService;

/**
 * Controller to be provided API of member resource.
 */
@RequestMapping("members")
@Controller
public final class MembersRestController {

    /**
     * Response entity for OPTIONS "/members" URI.
     */
    private static final ResponseEntity<Void> RESPONSE_ENTITY_FOR_OPTIONS_MEMBERS_URI = RestResponseUtils
            .createEntityOfOptions(HttpMethod.GET, HttpMethod.HEAD,
                    HttpMethod.POST);

    /**
     * Response entity for OPTIONS "/members/{memberId}" URI.
     */
    private static final ResponseEntity<Void> RESPONSE_ENTITY_FOR_OPTIONS_MEMBER_URI = RestResponseUtils
            .createEntityOfOptions(HttpMethod.GET, HttpMethod.HEAD,
                    HttpMethod.PUT, HttpMethod.DELETE);

    /**
     * Service of member entity.
     */
    @Inject
    MemberService memberService;

    /**
     * Conversion helper of member resource.
     */
    @Inject
    MemberResourceConversionHelper conversionHelper;

    private final UriTemplate uriTemplate = new UriTemplate(
            "http://localhost:8080/terasoluna-gfw-web-examples/rest/members/{memberId}");

    /**
     * Get collection of member resource.
     * <p>
     * Handle the request of GET and HEAD "/members" URI.
     * </p>
     * 
     * @return the entity to be written to the response.
     */
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.HEAD })
    public final ResponseEntity<MembersResource> getMembers(
            final @Validated MembersSearchQuery query, final Pageable pageable) {

        // search collection of member.
        final MemberSearchCriteria criteria = conversionHelper
                .toMemberSearchCriteria(query);
        final Page<Member> page = memberService.searchMembers(criteria,
                pageable);

        // create response resource.
        final MembersResource responseResource = conversionHelper
                .toMembersResource(page);

        // return the entity to be written to the response.
        return new ResponseEntity<MembersResource>(responseResource,
                HttpStatus.OK);
    }

    /**
     * Create a new member resource.
     * <p>
     * Handle the request of POST "/members" URI.
     * </p>
     * 
     * @param newResource
     *            new resource to be created.
     * 
     * @return the entity to be written to the response.
     */
    @RequestMapping(method = RequestMethod.POST)
    public final ResponseEntity<MemberResource> createMember(
            @RequestBody @Validated({ Default.class, MemberCreating.class }) final MemberResource newResource) {

        // create a new member.
        final Member member = conversionHelper.toMember(newResource);
        final Member createdMember = memberService.createMember(member);

        // create response resource.
        final MemberResource responseResource = conversionHelper
                .toMemberResource(createdMember);

        // return the entity to be written to the response.
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uriTemplate.expand(responseResource
                .getMemberId()));
        return new ResponseEntity<MemberResource>(responseResource,
                responseHeaders, HttpStatus.CREATED);
    }

    /**
     * Check up allowed method of "/members" URI.
     * <p>
     * Handle the request of OPTIONS "/members" URI.
     * </p>
     * 
     * @return the entity to be written to the response.
     */
    @RequestMapping(method = RequestMethod.OPTIONS)
    public final ResponseEntity<Void> optionsMembers() {
        // return the entity to be written to the response.
        return RESPONSE_ENTITY_FOR_OPTIONS_MEMBERS_URI;
    }

    /**
     * Get a member resource.
     * <p>
     * Handle the request of GET and HEAD "/members/{memberId}" URI.
     * </p>
     * 
     * @param memberId
     *            identify of member resource to be searched.
     * @return the entity to be written to the response.
     */
    @RequestMapping(value = "{memberId}", method = { RequestMethod.GET,
            RequestMethod.HEAD })
    public final ResponseEntity<MemberResource> getMember(
            final @PathVariable("memberId") String memberId) {

        // search the specified member.
        final Member member = memberService.getMember(memberId);

        // create response resource.
        final MemberResource responseResource = conversionHelper
                .toMemberResource(member);

        // return the entity to be written to the response.
        return new ResponseEntity<MemberResource>(responseResource,
                HttpStatus.OK);
    }

    /**
     * Update a specified member resource.
     * <p>
     * Handle the request of PUT "/members/{memberId}" URI.
     * </p>
     * 
     * @param memberId
     *            identify of member resource to be updated
     * @param updatedResource
     *            new resource to be updated.
     * 
     * @return the entity to be written to the response.
     */
    @RequestMapping(value = "{memberId}", method = RequestMethod.PUT)
    public final ResponseEntity<Void> updateMember(
            final @PathVariable("memberId") String memberId,
            final @RequestBody @Validated({ Default.class, MemberUpdating.class }) MemberResource updatedResource) {

        // update the specified member.
        final Member member = conversionHelper.toMember(updatedResource);
        memberService.updateMember(memberId, member);

        // return the entity to be written to the response.
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    /**
     * Delete a specified member resource.
     * <p>
     * Handle the request of DELETE "/members/{memberId}" URI.
     * </p>
     * 
     * @param memberId
     *            identify of member resource to be updated
     * 
     * @return the entity to be written to the response.
     */
    @RequestMapping(value = "{memberId}", method = RequestMethod.DELETE)
    public final ResponseEntity<Void> deleteMember(
            final @PathVariable("memberId") String memberId) {

        // delete the specified member.
        memberService.deleteMember(memberId);

        // return the entity to be written to the response.
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    /**
     * Check up allowed method of "/members/{memberId}" URI.
     * <p>
     * Handle the request of OPTIONS "/members/{memberId}" URI.
     * </p>
     * 
     * @return the entity to be written to the response.
     */
    @RequestMapping(value = "{memberId}", method = RequestMethod.OPTIONS)
    public final ResponseEntity<Void> optionsMember(
            final @PathVariable("memberId") String memberId) {

        // search the specified member.
        memberService.getMember(memberId);

        // return the entity to be written to the response.
        return RESPONSE_ENTITY_FOR_OPTIONS_MEMBER_URI;
    }

}
