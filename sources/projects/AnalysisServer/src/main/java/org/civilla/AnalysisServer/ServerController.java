package org.civilla.AnalysisServer;

import org.civilla.common.Logging;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServerController {

    protected static PsychopassDataProcessor psychopassDataProcessor = new PsychopassDataProcessor();

    @RequestMapping(value = "/analyse", method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> messageHandler(@RequestBody String payload,
                                                 @RequestHeader(value = "X-request-id", required = false) String requestId){
        long startTime = System.currentTimeMillis();
        Logging.log.info(String.join(" ", "Received request", requestId, payload));
        psychopassDataProcessor.analyse();
        //Logging.log.info(String.join(" ", "Sending response", requestId, stringResponse, "time taken:", Long.toString(System.currentTimeMillis() - startTime), "ms"));
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}