package com.fss.shopping.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class CaptchaVerification {
    private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaVerification.class);
    public static final String URL = "https://www.google.com/recaptcha/api/siteverify";
    public static final String SECRET = System.getProperty("google.captcha.key");
    private final static String USER_AGENT = "Mozilla/5.0";

    public static boolean verify(String recaptcha, String ip) {
        LOGGER.info("Started captcha verification");
        if (recaptcha == null || recaptcha.isEmpty()) {
            LOGGER.error("captcha is null or empty");
            return false;
        }

        try {
            URL obj = new URL(URL);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            String postParams = "secret=" + SECRET + "&response=" + recaptcha + "&remoteip=" + ip;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            LOGGER.info("Sending request to " + URL);
            LOGGER.info("Post parameters : " + postParams);
            LOGGER.info("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            LOGGER.info("Captcha verification result : " + response.toString());
            JsonNode actualObj = new ObjectMapper().readTree(response.toString());
            return actualObj.get("success").asBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}