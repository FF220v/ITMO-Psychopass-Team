package org.civilla.kubernetes;

import org.civilla.common.Helpers;
import org.json.simple.JSONObject;


public class KubeConfigLoader {

    public static String POLICE_CONTROL_SERVER = "policecontrolserver";

    public static JSONObject getBotSecrets() {
        return Helpers.readJson("mounted_volumes/bot_credentials/bot_credentials.json");
    }

    public static JSONObject servicesUrls() {
        return Helpers.readJson("mounted_volumes/services_urls/services_urls.json");
    }

}
