package org.civilla.PoliceControlServer;

import org.civilla.common.Logging;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServerController {
    @RequestMapping(value = "/messageHandler", method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public String testHandler(@RequestBody String payload){
        Logging.log.info(payload);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Logging.log.info("Completed");
        return "{\"received\": \"ok\"}";
    }
}
