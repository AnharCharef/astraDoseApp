package com.anhar.atcadaptor.presentation.mainApp.components

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.anhar.atcadaptor.common.Dimens.BottomBarHeight
import com.anhar.atcadaptor.common.Dimens.SmallPadding
import com.anhar.atcadaptor.domain.model.bottomBar.BottomBar



@Stable
@Composable
fun CustomBottomBar(
    bottomBarItems : List<BottomBar>,
    currentScreen : Int,
    modifier: Modifier = Modifier,
    onChangeNav: (Int) -> Unit
) {

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(BottomBarHeight)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onBackground),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(SmallPadding)
    ) {

        items(bottomBarItems.size , key = { bottomBarItems[it].title}){ current->
            if(current == 2){
                BottomBarItem(
                    currentScreen = currentScreen,
                    item = bottomBarItems[current],
                    onChangeNav = { onChangeNav(current) },
                )
            }else {
                BottomBarItem(
                    currentScreen = currentScreen,
                    item = bottomBarItems[current],
                    onChangeNav = { onChangeNav(current) },
                )
            }
        }
    }


}

fun Modifier.shadow(
    color: Color = Color.Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0f.dp,
    modifier: Modifier = Modifier
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width + spreadPixel)
            val bottomPixel = (this.size.height + spreadPixel)

            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint
            )
        }
    }
)