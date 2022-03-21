package htl.diplomarbeit.menuebestellung.api

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import htl.diplomarbeit.menuebestellung.composable.*
import htl.diplomarbeit.menuebestellung.dataClasses.Bestellung
import htl.diplomarbeit.menuebestellung.dataClasses.Menue
import htl.diplomarbeit.menuebestellung.dataClasses.Oeffnungszeiten
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.util.concurrent.CountDownLatch

class ApiService {


}

val client = OkHttpClient()
var menues: Collection<Menue> = mutableStateListOf<Menue>()
var menuesFilteredByDate: Collection<Menue> = mutableStateListOf<Menue>()
var bestellungen: List<Bestellung> = mutableStateListOf<Bestellung>()
var oeffnungszeiten: Collection<Oeffnungszeiten> = mutableStateListOf<Oeffnungszeiten>()


fun InitMenues(): Collection<Menue> {

    val url = ApiObject.initMenuesUrl

    val request = Request.Builder()
        .url(url)
        .get()
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val gson = GsonBuilder().create()

                val collectionType: Type =
                    object : TypeToken<Collection<Menue?>?>() {}.type


                menues = gson.fromJson(response.body!!.string(), collectionType)
            }
        }
    })

    return listOf<Menue>()
}

fun InitBestellungen(currUser: String): Collection<Bestellung> {

    if (currUser == "") {
        bestellungen = bestellungen - bestellungen
    } else {

        val url = ApiObject.bestellungUrl + currUser

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val gson = GsonBuilder().create()

                    val collectionType: Type =
                        object : TypeToken<Collection<Bestellung?>?>() {}.type
                    bestellungen = gson.fromJson(response.body!!.string(), collectionType)
                }
            }
        })

        InitBestellungen(bestellungErsteller.value)
    }

    return listOf<Bestellung>()
}


@RequiresApi(Build.VERSION_CODES.O)
fun getMenuesForDate(date: String) {
    if (date <= LocalDateTime.now().toString().take(10) && LocalDateTime.now().hour >= 9) {
        menueIsInThePast.value = true
    } else {
        menueIsInThePast.value = false
    }
    menuesFilteredByDate = menuesFilteredByDate - menuesFilteredByDate
    menues.sortedBy { menue -> menue.date }.sortedBy { menue -> menue.code }.forEach { menu ->
        if (menu.date == date) {
            menuesFilteredByDate = menuesFilteredByDate.plusElement(menu)
        }
    }

}



fun getOeffnungszeiten() {
    val url = ApiObject.oeffnungszeiten

    val request = Request.Builder()
        .url(url)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val gson = GsonBuilder().create()

                val collectionType: Type =
                    object : TypeToken<Collection<Oeffnungszeiten?>?>() {}.type
                oeffnungszeiten = gson.fromJson(response.body!!.string(), collectionType)
            }
        }
    })
}

fun postBestellung() {
    val url = ApiObject.bestellungUrl

    val dto = JSONObject()
    dto.put("amount", bestellungCount.value.toString())
    dto.put("comment", bestellungComment.value)
    dto.put("menueId", bestellungMenueId.value.toString())
    dto.put("orderedBy", bestellungErsteller.value)
    dto.put("orderedFor", bestellungFuer.value)
    dto.put("personalNummer", currUserPersonalNumber.value.toString())
    dto.put("timeId", bestellungZeitId.value.toString())

    val mediaType = String.format("application/json; charset=utf-8").toMediaType()
    val requestBody = dto.toString().toRequestBody(mediaType)

    val requestDto = Request.Builder()
        .url(url)
        .post(requestBody)
        .build()

    println(requestDto)


    client.newCall(requestDto).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                response.headers.get("orderedBy")?.let { it1 -> InitBestellungen(it1) }
            }
        }
    })

}


fun deleteBestellung(menuId: Int) {
    val url = ApiObject.bestellungUrl + "?id=" + menuId
    val body =
        RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            Gson().toJson("")
        )
    val request = Request.Builder()
        .url(url)
        .put(body)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                }

                InitBestellungen(currUser.value)
            }
        }
    })

}


var accessToken = mutableStateOf("")


fun checkAccessToken(): Boolean {

    val url = ApiObject.keycloak

    var body2 = FormBody.Builder()
        .add("client_id", "menu-app")
        .add("grant_type", "password")
        .add("client_secret", "501a7f23-3154-4559-bce2-0dd996767574")
        .add("scope", "openid")
        .add("password", currUserPassword.value)
        .add("username", currUser.value)
        .build()
    val request = Request.Builder()
        .url(url)
        .post(body2)
        .build()
    val countDownLatch = CountDownLatch(1)
    client.newCall(request).enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (response.isSuccessful) {
                    accessToken.value = response.body!!.string()
                } else {
                    throw IOException("Unexpected code $response")
                }
            }
            countDownLatch.countDown()
        }

        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            countDownLatch.countDown()

        }
    })
    countDownLatch.await()
    return accessToken.value != ""

}