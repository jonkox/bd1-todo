package tec.bd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zaxxer.hikari.HikariConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tec.bd.authentication.AuthenticationClient;
import tec.bd.authentication.AuthenticationClientImpl;
import tec.bd.authentication.AuthenticationResource;

public class WebApplicationContext {

    private AuthenticationClient authenticationClient;

    private WebApplicationContext() {

    }

    public static WebApplicationContext init() {
        WebApplicationContext webAppContext = new WebApplicationContext();
        initAuthenticationClient(webAppContext);

        return webAppContext;
    }

    private static void initAuthenticationClient(WebApplicationContext webApplicationContext) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AuthenticationResource authenticationResource = retrofit.create(AuthenticationResource.class);
        webApplicationContext.authenticationClient = new AuthenticationClientImpl(authenticationResource);
    }

    public Gson getGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
    }

    public AuthenticationClient getAuthenticationClient() {
        return this.authenticationClient;
    }
}
