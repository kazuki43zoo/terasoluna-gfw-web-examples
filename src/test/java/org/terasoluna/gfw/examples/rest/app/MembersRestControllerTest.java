package org.terasoluna.gfw.examples.rest.app;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.terasoluna.gfw.examples.rest.api.members.MemberResource;

public class MembersRestControllerTest {

    @Rule
    public TestName testName = new TestName();

    final RestTemplate restTemplate = new RestTemplate();

    final ResponseEntityDumpHelper dumpHelper = new ResponseEntityDumpHelper(testName);

    String baseUri = "http://localhost:8080/terasoluna-gfw-web-examples/api";

    @Test
    public void testGetMembers() throws JsonGenerationException, JsonMappingException, IOException {
        String uri = baseUri + "/members";
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);
        dumpHelper.dumpResponseEntity(response);
    }

    @Test
    public void testGetMembers_Pagenation1() throws JsonGenerationException, JsonMappingException,
            IOException {
        String uri = baseUri + "/members?page=1&size=5";
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);
        dumpHelper.dumpResponseEntity(response);
        assertThat(response.getBody().size(), is(5));
    }

    @Test
    public void testGetMembers_Pagenation2() throws JsonGenerationException, JsonMappingException,
            IOException {
        String uri = baseUri + "/members?page=2&size=3";
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);
        dumpHelper.dumpResponseEntity(response);
        assertThat(response.getBody().size(), is(3));
    }

    @Test
    public void testHeadMembers() throws JsonGenerationException, JsonMappingException, IOException {
        String uri = baseUri + "/members";

        ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.HEAD, null,
                (Class<Void>) null);
        dumpHelper.dumpResponseEntity(response);

        assertThat(response.getHeaders().getContentType(),
                is(MediaType.parseMediaType("application/json;charset=UTF-8")));
        assertThat(response.getHeaders().getContentLength(), not(0L));

    }

    @Test
    public void testCreateMember() throws JsonGenerationException, JsonMappingException,
            IOException {
        ResponseEntity<MemberResource> createdResponse = createMember();
        dumpHelper.dumpResponseEntity(createdResponse);

        ResponseEntity<Map> response = restTemplate.getForEntity(createdResponse.getHeaders()
                .getLocation(), Map.class);
        dumpHelper.dumpResponseEntity(response);

    }

    @Test
    public void testOptionsMembers() throws JsonGenerationException, JsonMappingException,
            IOException {
        String uri = baseUri + "/members";

        ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.OPTIONS, null,
                (Class<Void>) null);
        dumpHelper.dumpResponseEntity(response);

        assertThat(response.getHeaders().getAllow().size(), is(4));
        assertTrue(response.getHeaders().getAllow().contains(HttpMethod.GET));
        assertTrue(response.getHeaders().getAllow().contains(HttpMethod.HEAD));
        assertTrue(response.getHeaders().getAllow().contains(HttpMethod.POST));
        assertTrue(response.getHeaders().getAllow().contains(HttpMethod.OPTIONS));
    }

    @Test
    public void testOptions_usingPostMethod() throws JsonGenerationException, JsonMappingException,
            IOException {
        String uri = baseUri + "/members?_method=OPTIONS";

        ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.POST, null,
                (Class<Void>) null);
        dumpHelper.dumpResponseEntity(response);

        assertThat(response.getHeaders().getAllow().size(), is(4));
        assertTrue(response.getHeaders().getAllow().contains(HttpMethod.GET));
        assertTrue(response.getHeaders().getAllow().contains(HttpMethod.HEAD));
        assertTrue(response.getHeaders().getAllow().contains(HttpMethod.POST));
        assertTrue(response.getHeaders().getAllow().contains(HttpMethod.OPTIONS));
    }

    @Test
    public void testGetMember() throws JsonGenerationException, JsonMappingException, IOException {
        String uri = baseUri + "/members/{memberId}";

        ResponseEntity<MemberResource> responseOfCreated = createMember();
        MemberResource resource = responseOfCreated.getBody();

        Map<String, String> urlVariables = Collections.singletonMap("memberId",
                resource.getMemberId());

        HttpHeaders headers = new HttpHeaders();
        headers.setIfNoneMatch(responseOfCreated.getHeaders().getETag());
        ResponseEntity<MemberResource> response = restTemplate.exchange(uri, HttpMethod.GET,
                new HttpEntity<Void>(headers), MemberResource.class, urlVariables);
        dumpHelper.dumpResponseEntity(response);

        assertThat(response.getBody().getMemberId(), is(resource.getMemberId()));

    }

    @Test
    public void testHeadMember() throws JsonGenerationException, JsonMappingException, IOException {
        String uri = baseUri + "/members/{memberId}";

        MemberResource resource = createMember().getBody();

        Map<String, String> urlVariables = Collections.singletonMap("memberId",
                resource.getMemberId());

        ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.HEAD, null,
                (Class<Void>) null, urlVariables);
        dumpHelper.dumpResponseEntity(response);
        assertThat(response.getHeaders().getContentType(),
                is(MediaType.parseMediaType("application/json;charset=UTF-8")));
        assertThat(response.getHeaders().getContentLength(), not(0L));
    }

    @Test
    public void testUpdateMember() throws JsonGenerationException, JsonMappingException,
            IOException {
        String uri = baseUri + "/members/{memberId}";

        MemberResource resource = createMember().getBody();

        resource.setPhoneNumber("08012347864");

        Map<String, String> urlVariables = Collections.singletonMap("memberId",
                resource.getMemberId());

        ResponseEntity<Void> updatedResponse = restTemplate.exchange(uri, HttpMethod.PUT,
                new HttpEntity<MemberResource>(resource), (Class<Void>) null, urlVariables);
        dumpHelper.dumpResponseEntity(updatedResponse);

        ResponseEntity<MemberResource> response = restTemplate.getForEntity(uri,
                MemberResource.class, urlVariables);
        dumpHelper.dumpResponseEntity(response);

        assertThat(response.getBody().getMemberId(), is(resource.getMemberId()));
        assertThat(response.getBody().getPhoneNumber(), is("08012347864"));

    }

    @Test
    public void testUpdateMember_twice() throws JsonGenerationException, JsonMappingException,
            IOException {
        String uri = baseUri + "/members/{memberId}";

        MemberResource resource = createMember().getBody();

        resource.setPhoneNumber("08012347864");

        Map<String, String> urlVariables = Collections.singletonMap("memberId",
                resource.getMemberId());

        ResponseEntity<Void> updatedResponse = restTemplate.exchange(uri, HttpMethod.PUT,
                new HttpEntity<MemberResource>(resource), (Class<Void>) null, urlVariables);
        dumpHelper.dumpResponseEntity(updatedResponse);

        updatedResponse = restTemplate.exchange(uri, HttpMethod.PUT,
                new HttpEntity<MemberResource>(resource), (Class<Void>) null, urlVariables);
        dumpHelper.dumpResponseEntity(updatedResponse);

        ResponseEntity<MemberResource> response = restTemplate.getForEntity(uri,
                MemberResource.class, urlVariables);
        dumpHelper.dumpResponseEntity(response);

        assertThat(response.getBody().getMemberId(), is(resource.getMemberId()));
        assertThat(response.getBody().getPhoneNumber(), is("08012347864"));
    }

    @Test
    public void testDeleteMember() throws JsonGenerationException, JsonMappingException,
            IOException {
        String uri = baseUri + "/members/{memberId}";

        MemberResource resource = createMember().getBody();

        Map<String, String> urlVariables = Collections.singletonMap("memberId",
                resource.getMemberId());

        ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.DELETE, null,
                (Class<Void>) null, urlVariables);
        dumpHelper.dumpResponseEntity(response);

        try {
            restTemplate.getForEntity(uri, MemberResource.class, urlVariables);
            fail("HttpStatusCodeException is not occurred.");
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
                throw e;
            }
            dumpHelper.dumpResponseEntity(new ResponseEntity<String>(e.getResponseBodyAsString(), e
                    .getResponseHeaders(), e.getStatusCode()));
        }

    }

    @Test
    public void testDeleteMember_twice() throws JsonGenerationException, JsonMappingException,
            IOException {
        String uri = baseUri + "/members/{memberId}";

        MemberResource resource = createMember().getBody();

        Map<String, String> urlVariables = Collections.singletonMap("memberId",
                resource.getMemberId());

        ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.DELETE, null,
                (Class<Void>) null, urlVariables);
        dumpHelper.dumpResponseEntity(response);

        response = restTemplate.exchange(uri, HttpMethod.DELETE, null, (Class<Void>) null,
                urlVariables);
        dumpHelper.dumpResponseEntity(response);

        try {
            restTemplate.getForEntity(uri, MemberResource.class, urlVariables);
            fail("HttpStatusCodeException is not occurred.");
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
                throw e;
            }
            dumpHelper.dumpResponseEntity(new ResponseEntity<String>(e.getResponseBodyAsString(), e
                    .getResponseHeaders(), e.getStatusCode()));
        }

    }

    @Test
    public void testOptionsMember() throws JsonGenerationException, JsonMappingException,
            IOException {
        String uri = baseUri + "/members/{memberId}";

        MemberResource resource = createMember().getBody();

        Map<String, String> urlVariables = Collections.singletonMap("memberId",
                resource.getMemberId());

        ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.OPTIONS, null,
                (Class<Void>) null, urlVariables);
        dumpHelper.dumpResponseEntity(response);

        assertThat(response.getHeaders().getAllow().size(), is(5));
        assertTrue(response.getHeaders().getAllow().contains(HttpMethod.GET));
        assertTrue(response.getHeaders().getAllow().contains(HttpMethod.HEAD));
        assertTrue(response.getHeaders().getAllow().contains(HttpMethod.PUT));
        assertTrue(response.getHeaders().getAllow().contains(HttpMethod.DELETE));
        assertTrue(response.getHeaders().getAllow().contains(HttpMethod.OPTIONS));
    }

    @Test
    public void testValidationErrorOccurred() throws JsonGenerationException, JsonMappingException,
            IOException {
        String uri = baseUri + "/members";

        try {
            MemberResource newResource = new MemberResource();
            newResource.setFirstName("123456789012345678901234567890123456789012345678901");
            restTemplate.postForEntity(uri, newResource, MemberResource.class);
            fail("HttpStatusCodeException is not occurred.");
        } catch (HttpStatusCodeException e) {
            dumpHelper.dumpResponseEntity(new ResponseEntity<String>(e.getResponseBodyAsString(), e
                    .getResponseHeaders(), e.getStatusCode()));
            assertThat(e.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        }
    }

    @Test
    public void testTypeMismatchErrorOccurredCausedByEnumMismatch() throws JsonGenerationException,
            JsonMappingException, IOException {
        String uri = baseUri + "/members";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            restTemplate.postForEntity(uri,
                    new HttpEntity<String>("{\"gender\":\"hoge\"}", headers), MemberResource.class);
            fail("HttpStatusCodeException is not occurred.");
        } catch (HttpStatusCodeException e) {
            dumpHelper.dumpResponseEntity(new ResponseEntity<String>(e.getResponseBodyAsString(), e
                    .getResponseHeaders(), e.getStatusCode()));
            assertThat(e.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        }
    }

    @Test
    public void testJsonErrorOccurredCausedByUnknownPropertyIsSpecified()
            throws JsonGenerationException, JsonMappingException, IOException {
        String uri = baseUri + "/members";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            restTemplate.postForEntity(uri, new HttpEntity<String>("{\"hoge\":\"hoge\"}", headers),
                    MemberResource.class);
            fail("HttpStatusCodeException is not occurred.");
        } catch (HttpStatusCodeException e) {
            dumpHelper.dumpResponseEntity(new ResponseEntity<String>(e.getResponseBodyAsString(), e
                    .getResponseHeaders(), e.getStatusCode()));
            assertThat(e.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        }
    }

    @Test
    public void testJsonFormatErrorOccurredCaused() throws JsonGenerationException,
            JsonMappingException, IOException {
        String uri = baseUri + "/members";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            restTemplate.postForEntity(uri, new HttpEntity<String>("{gender:\"MEN\"}", headers),
                    MemberResource.class);
            fail("HttpStatusCodeException is not occurred.");
        } catch (HttpStatusCodeException e) {
            dumpHelper.dumpResponseEntity(new ResponseEntity<String>(e.getResponseBodyAsString(), e
                    .getResponseHeaders(), e.getStatusCode()));
            assertThat(e.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        }
    }

    @Test
    public void testMembers_MethodNowAllowedErrorOccurred() throws JsonGenerationException,
            JsonMappingException, IOException {
        String uri = baseUri + "/members";

        try {
            restTemplate.delete(uri);
            fail("HttpStatusCodeException is not occurred.");
        } catch (HttpStatusCodeException e) {
            dumpHelper.dumpResponseEntity(new ResponseEntity<String>(e.getResponseBodyAsString(), e
                    .getResponseHeaders(), e.getStatusCode()));
            assertThat(e.getStatusCode(), is(HttpStatus.METHOD_NOT_ALLOWED));
        }
    }

    @Test
    public void testGetMember_ResourceNotFound() throws JsonGenerationException,
            JsonMappingException, IOException {
        String uri = baseUri + "/members/{memberId}";

        Map<String, String> urlVariables = Collections.singletonMap("memberId", "hoge");
        try {
            restTemplate.getForEntity(uri, MemberResource.class, urlVariables);
            fail("HttpStatusCodeException is not occurred.");
        } catch (HttpStatusCodeException e) {
            dumpHelper.dumpResponseEntity(new ResponseEntity<String>(e.getResponseBodyAsString(), e
                    .getResponseHeaders(), e.getStatusCode()));
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
        }
    }

    @Test
    public void testHeadMember_ResourceNotFound() throws JsonGenerationException,
            JsonMappingException, IOException {
        String uri = baseUri + "/members/{memberId}";

        Map<String, String> urlVariables = Collections.singletonMap("memberId", "hoge");

        try {
            restTemplate.exchange(uri, HttpMethod.HEAD, null, (Class<Void>) null, urlVariables);
            fail("HttpStatusCodeException is not occurred.");
        } catch (HttpStatusCodeException e) {
            dumpHelper.dumpResponseEntity(new ResponseEntity<String>(e.getResponseBodyAsString(), e
                    .getResponseHeaders(), e.getStatusCode()));
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
        }
    }

    @Test
    public void testUpdateMember_ResourceNotFound() throws JsonGenerationException,
            JsonMappingException, IOException {
        String uri = baseUri + "/members/{memberId}";

        MemberResource resource = createMember().getBody();

        resource.setPhoneNumber("08012347864");

        Map<String, String> urlVariables = Collections.singletonMap("memberId", "hoge");

        try {
            restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<MemberResource>(resource),
                    MemberResource.class, urlVariables);
            fail("HttpStatusCodeException is not occurred.");
        } catch (HttpStatusCodeException e) {
            dumpHelper.dumpResponseEntity(new ResponseEntity<String>(e.getResponseBodyAsString(), e
                    .getResponseHeaders(), e.getStatusCode()));
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
        }
    }

    @Test
    public void testOptionsMember_ResourceNotFound() throws JsonGenerationException,
            JsonMappingException, IOException {
        String uri = baseUri + "/members/{memberId}";

        Map<String, String> urlVariables = Collections.singletonMap("memberId", "hoge");

        try {
            restTemplate.exchange(uri, HttpMethod.OPTIONS, null, (Class<Void>) null, urlVariables);
            fail("HttpStatusCodeException is not occurred.");
        } catch (HttpStatusCodeException e) {
            dumpHelper.dumpResponseEntity(new ResponseEntity<String>(e.getResponseBodyAsString(), e
                    .getResponseHeaders(), e.getStatusCode()));
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
        }

    }

    @Test
    public void testHttpMediaTypeNotSupportedException() throws JsonGenerationException,
            JsonMappingException, IOException {
        String uri = baseUri + "/members";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            restTemplate.postForEntity(uri, new HttpEntity<String>(
                    "<memberResource></memberResource>", headers), MemberResource.class);
            fail("HttpStatusCodeException is not occurred.");
        } catch (HttpStatusCodeException e) {
            dumpHelper.dumpResponseEntity(new ResponseEntity<String>(e.getResponseBodyAsString(), e
                    .getResponseHeaders(), e.getStatusCode()));
            assertThat(e.getStatusCode(), is(HttpStatus.UNSUPPORTED_MEDIA_TYPE));
        }
    }

    @Test
    public void testHttpMediaTypeNotAcceptableException() throws JsonGenerationException,
            JsonMappingException, IOException {
        String uri = baseUri + "/members/{memberId}.xml";

        MemberResource resource = createMember().getBody();

        Map<String, String> urlVariables = Collections.singletonMap("memberId",
                resource.getMemberId());
        try {
            restTemplate.getForEntity(uri, MemberResource.class, urlVariables);
            fail("HttpStatusCodeException is not occurred.");
        } catch (HttpStatusCodeException e) {
            dumpHelper.dumpResponseEntity(new ResponseEntity<String>(e.getResponseBodyAsString(), e
                    .getResponseHeaders(), e.getStatusCode()));
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_ACCEPTABLE));
        }
    }

    private ResponseEntity<MemberResource> createMember() {
        String uri = baseUri + "/members";

        HttpHeaders h = new HttpHeaders();
        h.add("X-Forwarded-Host", "localhost.local:5678");

        MemberResource newResource = new MemberResource();
        newResource.setFirstName("Kazuki");
        newResource.setLastName("Shimizu");
        newResource.setGender("MAN");
        newResource.setEmailAddress("kazuki.shimizu@test.com");
        newResource.setPhoneNumber("09012345678");
        newResource.setAddress("Japan Tokyo, Toshima Nagasaki 2-34-5 WELC2 202");
        ResponseEntity<MemberResource> response = restTemplate.postForEntity(uri,
                new HttpEntity<MemberResource>(newResource, h), MemberResource.class);
        return response;

    }

}
