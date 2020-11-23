package org.civilla.kubernetes;

import org.civilla.common.Helpers;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class KubeConfigLoader {

    public static String POLICE_CONTROL_SERVER = "policecontrolserver";

    public static JSONObject getBotSecrets() {
        return Helpers.readJson("kubernetes/bot_credentials/bot_credentials.json");
    }

    public static JSONObject servicesUrls() {
        return Helpers.readJson("kubernetes/service_urls/services_urls.json");
    }

}
