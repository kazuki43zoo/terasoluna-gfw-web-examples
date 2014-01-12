package org.terasoluna.gfw.examples.rest.app;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.rules.TestName;
import org.springframework.http.ResponseEntity;

public class ResponseEntityDumpHelper {
    final ObjectWriter jsonWriter = new ObjectMapper()
            .writerWithDefaultPrettyPrinter();
    final TestName testName;

    public ResponseEntityDumpHelper(TestName testName) {
        this.testName = testName;
    }

    public void dumpResponseEntity(ResponseEntity<?> response)
            throws JsonGenerationException, JsonMappingException, IOException {
        System.out.println("---- dump start " + testName.getMethodName()
                + "----");
        System.out.println(jsonWriter.writeValueAsString(response
                .getStatusCode()));
        System.out
                .println(jsonWriter.writeValueAsString(response.getHeaders()));
        if (response.getBody() instanceof String) {
            System.out.println(response.getBody());
        } else {
            System.out
                    .println(jsonWriter.writeValueAsString(response.getBody()));
        }
        System.out.println("---- dump end ----");

    }

}
