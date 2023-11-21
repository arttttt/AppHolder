package com.arttttt.appholder.ui.appslist.lazylist.delegates

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arttttt.appholder.ui.appslist.lazylist.models.AppListItem
import com.arttttt.appholder.ui.appslist.lazylist.models.fromClippableItem
import com.arttttt.appholder.ui.base.dsl.lazyListDelegate
import com.arttttt.appholder.ui.theme.AppTheme
import com.google.accompanist.drawablepainter.rememberDrawablePainter

fun AppListDelegate(
    onClick: (String) -> Unit
) = lazyListDelegate<AppListItem> {

    Row(
        modifier = Modifier
            .fillParentMaxWidth()
            .fromClippableItem(item)
            .background(AppTheme.colors.tertiary)
            .clickable {
                onClick.invoke(item.pkg)
            }
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (item.icon != null) {
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                painter = rememberDrawablePainter(item.icon),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(16.dp))
        }

        Text(
            modifier = Modifier,
            text = item.title,
            fontSize = 18.sp,
        )
    }
}