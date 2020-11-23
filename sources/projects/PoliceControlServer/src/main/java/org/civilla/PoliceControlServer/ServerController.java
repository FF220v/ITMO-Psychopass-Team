package org.civilla.PoliceControlServer;

import org.civilla.common.Helpers;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServerController {
    @RequestMapping(value = "/message", method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> messageHandler(@RequestBody String payload,
                                                 @RequestHeader(value = "X-request-id", required = false) String requestId){
        JSONArray responses = new JSONArray();
        JSONArray messages = (JSONArray) Helpers.stringToJson(payload).get("values");
        for (Object msg: messages) {
            JSONObject msg_ = (JSONObject) msg;
            TelegramMessage telegMsg = new TelegramMessage((long) msg_.get("chat_id"), (String) msg_.get("message"));

            PoliceControlMsgProc proc = new PoliceControlMsgProc(telegMsg, requestId);
            String result = proc.processMessage();

            JSONObject resp = new JSONObject();
            resp.put("incoming_msg", telegMsg.text);
            resp.put("chat_id", telegMsg.chat_id);
            resp.put("result", result);
            responses.put(resp);
        }
        JSONObject response = new JSONObject();
        response.put("values", responses);

        return new ResponseEntity<>(response.toJSONString(), HttpStatus.OK);
    }
}
