package com.example.spotifysearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.example.spotifyapi.RxSchedulersOverrideRule;
import com.example.spotifyapi.viewmodel.auth.AuthActivityRepository;
import com.example.spotifysearch.model.auth.Token;
import com.example.spotifysearch.network.ApiService;
import com.example.spotifysearch.states.AuthResource;
import com.example.spotifysearch.utils.Const;
import com.example.spotifysearch.utils.PrefUtils;
import com.example.spotifysearch.viewmodel.auth.AuthActivityViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import io.reactivex.Single;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class AuthViewModelTest {

    private Uri spotifyUri = Uri.parse("mohsen://mohsenspotifyredirect.com/mohsen/?code=NApCCg..BkWtQ&state=profile%2Factivity");


    private String authCode = "NApCCg..BkWtQ";
    private String accessToken = "NgCXRK...MzYjw";
    private String refreshToken = "NgAagA...Um_SHo";


    @Rule
    public final RxSchedulersOverrideRule overrideSchedulersRule = new RxSchedulersOverrideRule();

    @Mock
    ApiService apiService;

    private AuthActivityViewModel authActivityViewModel;


    @Mock
    Context context;

    @Mock
    SharedPreferences sharedPreferences;


    @Mock
    PrefUtils prefUtils;

    @Mock
    SharedPreferences.Editor editor;

    @Mock
    AuthActivityRepository repository;

    private MutableLiveData<AuthResource> authResource;

    @Before
    public void setUp() {

        //Init Mocks
        MockitoAnnotations.initMocks(this);

        //Init ViewModel
        repository = new AuthActivityRepository(apiService);
        authActivityViewModel = new AuthActivityViewModel(repository);

        //Mock shared preferences
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences);

        //Mock PrefUtils
        prefUtils = new PrefUtils(sharedPreferences, editor);

    }


    @Test
    public void testAuthRedirectFailed() {

        authResource = authActivityViewModel.getAuthorizationStatus();
        when(sharedPreferences.getString(eq(Const.AUTHORIZATION_CODE_KEY), anyString())).thenReturn(authCode);
        when(apiService.authorize(Const.AUTHORIZATION_CODE_KEY, PrefUtils.getTocken(Const.AUTHORIZATION_CODE_KEY), Const.REDIRECT_URL, Const.CLIENT_ID, Const.CLIENT_SECRET)).thenReturn(Single.error(new Throwable("Api error")));
        assertEquals(authResource.getValue().status, AuthResource.AuthStatus.LOADING);
        authActivityViewModel.getCridentials(spotifyUri);
        assertEquals(authResource.getValue().message, AuthResource.error("Api error").message);
    }

    @Test
    public void testAuthRedirectSuccessfull() {
        authResource = authActivityViewModel.getAuthorizationStatus();
        when(sharedPreferences.getString(eq(Const.AUTHORIZATION_CODE_KEY), anyString())).thenReturn(authCode);
        when(apiService.authorize(Const.AUTHORIZATION_CODE_KEY, PrefUtils.getTocken(Const.AUTHORIZATION_CODE_KEY), Const.REDIRECT_URL, Const.CLIENT_ID, Const.CLIENT_SECRET)).thenReturn(Single.just(new Token()));
        assertEquals(authResource.getValue().status, AuthResource.AuthStatus.LOADING);
        authActivityViewModel.getCridentials(spotifyUri);
        assertEquals(authResource.getValue().message, AuthResource.authorized("Authorized Successfully").message);
    }

    @Test
    public void testLogin() {
        when(sharedPreferences.getString(eq(Const.ACCESS_TOKEN_KEY), anyString())).thenReturn(accessToken);
        when(sharedPreferences.getString(eq(Const.REFRESH_TOKEN_KEY), anyString())).thenReturn(refreshToken);
        authActivityViewModel.checkAutorizationStatus();
        assertEquals(authActivityViewModel.getAuthorizationStatus().getValue().message, AuthResource.login("Login Successful").message);

    }
}
