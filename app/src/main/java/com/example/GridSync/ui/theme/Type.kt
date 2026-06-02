package com.example.GridSync.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.GridSync.R

@Composable
fun getTypography(): Typography {
    return Typography(
        bodyLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = dimensionResource(id = R.dimen.text_size_body).value.sp,
            lineHeight = dimensionResource(id = R.dimen.line_height_body).value.sp,
            letterSpacing = dimensionResource(id = R.dimen.letter_spacing_body).value.sp
        ),
        titleLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = dimensionResource(id = R.dimen.text_size_title).value.sp,
            lineHeight = dimensionResource(id = R.dimen.line_height_title).value.sp,
            letterSpacing = dimensionResource(id = R.dimen.letter_spacing_title).value.sp
        ),
        labelSmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = dimensionResource(id = R.dimen.text_size_label).value.sp,
            lineHeight = dimensionResource(id = R.dimen.line_height_label).value.sp,
            letterSpacing = dimensionResource(id = R.dimen.letter_spacing_label).value.sp
        )
    )
}
