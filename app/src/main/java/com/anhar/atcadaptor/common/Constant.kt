package com.anhar.atcadaptor.common

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.text.format.DateUtils
import android.util.Base64
import android.util.Patterns
import androidx.compose.ui.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.domain.model.bottomBar.BottomBar
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.random.Random

object Constant {
    const val APP_ENTRY = "appEntry"
    const val APP_LANG = "appLang"


    val mainActivityBottomBarItems = listOf(

        BottomBar(
            title = "Calculation",
            selectedIcon = R.drawable.ic_calculate,
            unselectedIcon = R.drawable.ic_calculate,
            screen = 0
        ),
        BottomBar(
            title = "Alert",
            selectedIcon = R.drawable.ic_alert_notification,
            unselectedIcon = R.drawable.ic_alert_notification,
            screen = 1
        ),
        BottomBar(
            title = "Home",
            selectedIcon = R.drawable.ic_home,
            unselectedIcon = R.drawable.ic_home,
            screen = 2
        ),
        BottomBar(
            title = "Scanner",
            selectedIcon = R.drawable.ic_qr_code,
            unselectedIcon =R.drawable.ic_qr_code,
            screen = 3
        ),
        BottomBar(
            title = "Profile",
            selectedIcon = R.drawable.ic_support,
            unselectedIcon = R.drawable.ic_support,
            screen = 4
        ),


        )

    val lANG_LIST = listOf(
        Pair(R.string.english,"en"),
        Pair(R.string.arabic ,"ar")
    )

    fun checkUserName(name: String, context: Context): Resource<Boolean> {
        val regex = "^[A-Za-z][A-Za-z0-9_]*( [A-Za-z0-9_]+)*$"
        val p: Pattern = Pattern.compile(regex)
        val trimName = name.trim()
        val m: Matcher = p.matcher(name)
        return when {
            name.isBlank() || trimName.isEmpty() -> {
                Resource.Error(context.getString(R.string.userNameEmptyErrorMsg), false)
            }

            !m.matches() -> {
                Resource.Error(context.getString(R.string.userNameWrongPatternErrorMsg), false)
            }

            else -> {
                Resource.Successful(true)
            }
        }
    }

    fun checkPhone(phone: String, context: Context): Resource<Boolean> {
        val regex = "^(00213|\\+213|0)(5|6|7)[0-9]{8}$"
        val p: Pattern = Pattern.compile(regex)
        val trimPhone = phone.trim()
        val m: Matcher = p.matcher(phone)
        return when {
            phone.isBlank() || trimPhone.isEmpty() -> {
                Resource.Error(context.getString(R.string.phoneEmptyErrorMsg), false)
            }

            !m.matches() -> {
                Resource.Error(context.getString(R.string.phoneWrongPatternErrorMsg), false)
            }

            else -> {
                Resource.Successful(true)
            }
        }
    }

    fun checkEmail(email: String, context: Context): Resource<Boolean> {
        val trimEmail = email.trim()

        return when {
            trimEmail.isBlank() || trimEmail.isEmpty() -> {
                Resource.Error(context.getString(R.string.emailEmptyErrorMsg), false)
            }

            !Patterns.EMAIL_ADDRESS.matcher(trimEmail).matches() -> {
                Resource.Error(context.getString(R.string.emailWrongPatternErrorMsg), false)
            }

            else -> {
                Resource.Successful(true)
            }
        }
    }

    fun checkPassword(password: String, context: Context): Resource<Boolean> {
        val trimPassword = password.trim()

        return when {
            trimPassword.isBlank() || trimPassword.isEmpty() -> {
                Resource.Error(context.getString(R.string.passwordEmptyErrorMsg), false)
            }

            trimPassword.length < 6 -> {
                Resource.Error(context.getString(R.string.passwordWrongPatternErrorMsg), false)
            }

            else -> {
                Resource.Successful(true)
            }
        }
    }

    fun setLocal(lang: String, context: Context) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()

        config.setLocale(locale)
        context.resources.updateConfiguration(
            config,
            context.resources.displayMetrics
        )
    }

    fun formatDate(time : Long) :String{
        val date = Date(time *1000)
        val dateFormatted = SimpleDateFormat("dd/MM/yyyy HH:mm:ss" , Locale.US)
            .format(date)
        return dateFormatted
    }

    fun getRelativeTime(time: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)

        val date: Date = dateFormat.parse(time) ?: return "Invalid date"

        val timestamp = date.time

        val now = System.currentTimeMillis()

        return DateUtils.getRelativeTimeSpanString(
            timestamp,
            now,
            DateUtils.MINUTE_IN_MILLIS
        ).toString().toEnglishNumbers()
    }

    fun Double.format(digits: Int) = "%.${digits}f".format(this)

    private fun String.toEnglishNumbers(): String {
        val arabicNumbers = arrayOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')
        val englishNumbers = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

        var result = this
        for (i in arabicNumbers.indices) {
            result = result.replace(arabicNumbers[i], englishNumbers[i])
        }

        return result
    }

    fun randomColor(mainBrightness: Int = 50): Color {
        val random = Random.Default
        val red = random.nextInt(mainBrightness, 256)
        val green = random.nextInt(mainBrightness, 256)
        val blue = random.nextInt(mainBrightness, 256)
        return Color(red, green, blue)

    }

    fun generateQRCode(content: String, size: Int = 512): Bitmap {
        val bitMatrix: BitMatrix =
            MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        return bitmap
    }

    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}