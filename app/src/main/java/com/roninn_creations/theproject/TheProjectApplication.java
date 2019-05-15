package com.roninn_creations.theproject;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roninn_creations.theproject.activities.LoginActivity;
import com.roninn_creations.theproject.activities.PlacesActivity;
import com.roninn_creations.theproject.models.Place;
import com.roninn_creations.theproject.models.Review;
import com.roninn_creations.theproject.models.User;
import com.roninn_creations.theproject.services.AuthService;
import com.roninn_creations.theproject.services.IService;
import com.roninn_creations.theproject.services.PlacesService;
import com.roninn_creations.theproject.network.RequestHandler;
import com.roninn_creations.theproject.services.ReviewsService;
import com.roninn_creations.theproject.network.Volley;

public class TheProjectApplication extends Application {

    private static Gson gson;
    private static RequestHandler requestHandler;
    private static IService<Place> placesService;
    private static IService<Review> reviewsService;
    private static AuthService authService;
    private static User user;
    private static LoginManager facebookLoginManager;
    private static SharedPreferences sharedPreferences;

    private GoogleSignInClient googleSignInClient;

    @Override
    public void onCreate(){
        super.onCreate();

        sharedPreferences = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String jwt = sharedPreferences.getString(
                getString(R.string.jwt_preference_key), null);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(getString(R.string.date_format)).create();
        gson = gsonBuilder.create();
        Volley volley = new Volley(this);
        requestHandler = new RequestHandler(getString(R.string.base_url),
                volley.getRequestQueue(),
                jwt);
        placesService = new PlacesService(getString(R.string.places_path), gson, requestHandler);
        reviewsService = new ReviewsService(getString(R.string.reviews_path), gson, requestHandler);
        authService = new AuthService(getString(R.string.auth_path), gson, requestHandler);

        GoogleSignInOptions options = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PROFILE), new Scope(Scopes.EMAIL))
                .requestServerAuthCode(getString(R.string.google_app_id))
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, options);
        facebookLoginManager = LoginManager.getInstance();
    }

    public void login(User user, String token){
        TheProjectApplication.user = user;
        requestHandler.setToken(token);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.jwt_preference_key), token);
        editor.apply();
        Intent placesIntent = new Intent(this, PlacesActivity.class);
        this.startActivity(placesIntent);
    }

    public void logOut(){
        user = null;
        requestHandler.setToken(null);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(getString(R.string.jwt_preference_key));
        editor.apply();
        googleSignInClient.signOut();
        facebookLoginManager.logOut();
        Intent loginIntent = new Intent(this, LoginActivity.class);
        this.startActivity(loginIntent);
    }

    public GoogleSignInClient getGoogleSignInClient(){
        return this.googleSignInClient;
    }

    public static Gson getGson(){
        return gson;
    }

    public static RequestHandler getRequestHandler(){
        return requestHandler;
    }

    public static IService<Place> getPlacesService(){
        return placesService;
    }

    public static IService<Review> getReviewsService(){
        return reviewsService;
    }

    public static AuthService getAuthService(){
        return authService;
    }

    public static User getUser(){
        return user;
    }

    public static void setUser(User user){
        TheProjectApplication.user = user;
    }

    public static LoginManager getFacebookLoginManager(){
        return facebookLoginManager;
    }
}
