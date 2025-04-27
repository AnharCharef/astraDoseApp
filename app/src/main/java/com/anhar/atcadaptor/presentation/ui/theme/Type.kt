package com.anhar.atcadaptor.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.anhar.atcadaptor.R


val Cairo = FontFamily(
    Font(R.font.cairo_black,FontWeight.Black ),
    Font(R.font.cairo_bold,FontWeight.Bold),
    Font(R.font.cairo_semibold,FontWeight.SemiBold),
    Font(R.font.cairo_regular,FontWeight.Normal),
    Font(R.font.cairo_light,FontWeight.Light),
    Font(R.font.cairo_extralight,FontWeight.ExtraLight)
)

// Set of Material typography styles to start with
val Typography = Typography(

    headlineMedium = TextStyle(
        textAlign = TextAlign.Center,
        fontFamily = Cairo,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.0.sp // don't touch
    ),

    titleSmall = TextStyle(
        textAlign = TextAlign.Center,
        fontFamily = Cairo,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 26.sp // don't touch
    ),

    titleMedium = TextStyle(
        textAlign = TextAlign.Center,
        fontFamily = Cairo,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 26.sp // don't touch
    ),

    bodyLarge = TextStyle(
        fontFamily = Cairo,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 20.sp
    ),
    //Other default text styles to override
    displaySmall = TextStyle(
        fontSize = 20.sp,
        fontFamily = Cairo,
        fontWeight = FontWeight.Normal,
        lineHeight = 30.sp // don't touch
    ),
    displayMedium = TextStyle(
        fontSize = 32.sp,
        fontFamily = Cairo,
        fontWeight = FontWeight.Normal,
        lineHeight = 42.sp, // don't touch
    ),
    bodySmall = TextStyle(
        fontSize = 14.sp,
        fontFamily = Cairo,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
    ),
    bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = Cairo,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp, // don't touch
    ),
    labelSmall = TextStyle(
        fontSize = 13.sp,
        fontFamily = Cairo,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp, // don't touch
    ),


    )


// Set of Material typography styles to start with
//val Typography = Typography(
//    bodyLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    )
//    /* Other default text styles to override
//    titleLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 22.sp,
//        lineHeight = 28.sp,
//        letterSpacing = 0.sp
//    ),
//    labelSmall = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Medium,
//        fontSize = 11.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp
//    )
//    */
//)