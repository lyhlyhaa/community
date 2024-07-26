package life.majiang.community.community.provider;

import com.alibaba.fastjson.JSON;
import life.majiang.community.community.dto.AccessTokenDTO;
import life.majiang.community.community.dto.GithubUser;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    private static final Logger logger = LoggerFactory.getLogger(GithubProvider.class);
    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String USER_API_URL = "https://api.github.com/user";

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url(ACCESS_TOKEN_URL)
                .post(body)
                .header("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                logger.error("Failed to get access token. Response code: " + response.code());
                return null;
            }

            String responseBody = response.body().string();
            logger.info("Access token response: " + responseBody);
            return JSON.parseObject(responseBody).getString("access_token");
        } catch (IOException e) {
            logger.error("Exception when getting access token", e);
        }

        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(USER_API_URL)
                .header("Authorization", "token " + accessToken)
                .header("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                logger.error("Failed to get user info. Response code: " + response.code());
                return null;
            }

            String responseBody = response.body().string();
            logger.info("User info response: " + responseBody);
            return JSON.parseObject(responseBody, GithubUser.class);
        } catch (IOException e) {
            logger.error("Exception when getting user info", e);
        }

        return null;
    }
}