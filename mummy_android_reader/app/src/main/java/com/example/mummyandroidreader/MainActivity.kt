package com.example.mummyandroidreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.webkit.WebView
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.callback.Callback
import com.auth0.android.result.Credentials
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.authentication.storage.SharedPreferencesStorage

@Serializable
data class Data(val access_token: String,
                val id_token: String,
                val scope: String,
                val expires_in: Int,
                val token_type: String)

class MainActivity : AppCompatActivity() {

    private lateinit var account: Auth0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //

        account = Auth0(
            getString(R.string.com_auth0_client_id),
            getString(R.string.com_auth0_domain)
        )

        val auth0 = Auth0(this)
        val apiClient = AuthenticationAPIClient(auth0)
        val manager = CredentialsManager(apiClient, SharedPreferencesStorage(this))

        //

        fun get_res(credentials: Credentials) {
            manager.saveCredentials(credentials)
            val accessToken = credentials.accessToken
            lifecycleScope.launch {
                val page = MyHTTP.send_token(accessToken)
                val encodedHtml = Base64.encodeToString(page.toByteArray(), Base64.NO_PADDING)
                val webView1 = findViewById(R.id.webView1) as WebView
                webView1.loadData(encodedHtml, "text/html", "base64")
            }
        }

        //

        fun loginWithBrowser() {
            WebAuthProvider.login(account)
                .withScheme("demo")
                .withScope("openid email offline_access")
                .start(this, object : Callback<Credentials, AuthenticationException> {
                    override fun onFailure(exception: AuthenticationException) {
                        println("Error: loginWithBrowser")
                    }
                    override fun onSuccess(credentials: Credentials) {
                        get_res(credentials)
                    }
                })
        }

        //

        val button1 = findViewById(R.id.button1) as Button

        button1.setOnClickListener {
            val loggedIn = manager.hasValidCredentials()
            if(loggedIn==false) {
                loginWithBrowser()
            } else {
                manager.getCredentials(object: Callback<Credentials, CredentialsManagerException> {
                    override fun onSuccess(credentials: Credentials) {
                        get_res(credentials)
                    }
                    override fun onFailure(error: CredentialsManagerException) {
                        println("Error: manager.getCredentials")
                    }
                })
            }
        }
    }
}
