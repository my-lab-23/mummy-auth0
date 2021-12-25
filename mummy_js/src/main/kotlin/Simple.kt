import com.example.my_http.MyHTTP
import kotlinx.browser.document
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

@Serializable
data class Data(val access_token: String,
                val id_token: String,
                val scope: String,
                val expires_in: Int,
                val token_type: String)

fun main() { // this: CoroutineScope
    GlobalScope.launch {
        //val token = MyHTTP.take_token()
        //val obj = Json.decodeFromString<Data>(token)
        val obj = ""
        val page = MyHTTP.send_token(obj)
        document.write(page)
    }
}
