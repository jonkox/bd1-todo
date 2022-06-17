package tec.bd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zaxxer.hikari.HikariConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tec.bd.Social.authentication.AuthenticationClient;
import tec.bd.Social.authentication.AuthenticationClientImpl;
import tec.bd.Social.authentication.AuthenticationResource;
import tec.bd.Social.datasource.DBManager;
import tec.bd.Social.datasource.HikariDBManager;
import tec.bd.Social.repository.RatingsRepository;
import tec.bd.Social.repository.RatingsRepositoryImpl;
import tec.bd.Social.repository.ReviewsRepository;
import tec.bd.Social.repository.ReviewsRepositoryImpl;
import tec.bd.Social.service.RatingsService;
import tec.bd.Social.service.RatingsServiceImpl;
import tec.bd.Social.service.ReviewsServices;
import tec.bd.Social.service.ReviewsServicesImpl;
import tec.bd.Social.todoapp.AuthenticationTodoRecord;
import tec.bd.Social.todoapp.AuthenticationTodoRecordImpl;

public class WebApplicationContext {

    private AuthenticationClient authenticationClient;
    private AuthenticationTodoRecord authenticationTodoRecord;

    private RatingsRepository ratingsRepository;
    private RatingsService ratingsService;

    private ReviewsRepository reviewsRepository;
    private ReviewsServices reviewsServices;
    private DBManager dbManager;

    private WebApplicationContext() {

    }

    public static WebApplicationContext init() {
        WebApplicationContext webAppContext = new WebApplicationContext();
        initAuthenticationClient(webAppContext);
        initDBManager(webAppContext);
        initRatingsRepository(webAppContext);
        initRatingsService(webAppContext);
        initAuthenticationTodoRecord(webAppContext);
        initReviewRepository(webAppContext);
        initReviewService(webAppContext);

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

    private static void initAuthenticationTodoRecord(WebApplicationContext webApplicationContext) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tec.bd.Social.todoapp.AuthenticationResource authenticationResource = retrofit.create(tec.bd.Social.todoapp.AuthenticationResource.class);
        webApplicationContext.authenticationTodoRecord = new AuthenticationTodoRecordImpl(authenticationResource);
    }


    public static void initRatingsRepository(WebApplicationContext webApplicationContext) {
        var dbManager = webApplicationContext.dbManager;
        webApplicationContext.ratingsRepository = new RatingsRepositoryImpl(dbManager);

    }

    public static void initRatingsService(WebApplicationContext webApplicationContext) {
        var ratingsRepository = webApplicationContext.ratingsRepository;
        webApplicationContext.ratingsService = new RatingsServiceImpl(ratingsRepository);

    }

    public static void initReviewRepository(WebApplicationContext webApplicationContext) {
        var dbManager = webApplicationContext.dbManager;
        webApplicationContext.reviewsRepository = new ReviewsRepositoryImpl(dbManager);

    }

    public static void initReviewService(WebApplicationContext webApplicationContext) {
        var reviewsRepository = webApplicationContext.reviewsRepository;
        webApplicationContext.reviewsServices = new ReviewsServicesImpl(reviewsRepository) {
        };

    }

    public ReviewsRepository getReviewsRepository() {
        return reviewsRepository;
    }

    public void setReviewsRepository(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    public ReviewsServices getReviewsServices() {
        return reviewsServices;
    }

    public void setReviewsServices(ReviewsServices reviewsServices) {
        this.reviewsServices = reviewsServices;
    }

    public AuthenticationClient getAuthenticationClient(){return this.authenticationClient;}

    public RatingsService getRatingsService(){return this.ratingsService;}

    public AuthenticationTodoRecord getAuthenticationTodoRecord(){return this.authenticationTodoRecord;}



    private static void initDBManager(WebApplicationContext webApplicationContext) {
        HikariConfig hikariConfig = new HikariConfig();
        var jdbcUrl = System.getenv("JDBC_SOCIAL_DB_URL");
        var username = System.getenv("JDBC_SOCIAL_DB_USERNAME");
        var password = System.getenv("JDBC_SOCIAL_DB_PASSWORD");
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.addDataSourceProperty("connectionTestQuery", "SELECT 1");
        hikariConfig.addDataSourceProperty("maximumPoolSize", "4");
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        DBManager dbManager = new HikariDBManager(hikariConfig);
        webApplicationContext.dbManager = dbManager;
    }

    public Gson getGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
    }
}
