package com.droidknights.app2021.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.droidknights.app2021.core.ui.compose.layout.Tag
import com.droidknights.app2021.core.ui.compose.util.toColor
import com.droidknights.app2021.shared.ext.color
import com.droidknights.app2021.shared.model.Session
import com.droidknights.app2021.shared.model.User
import com.google.accompanist.flowlayout.FlowRow

@Composable
internal fun DetailScreen(
    modifier: Modifier = Modifier,
    session: Session
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            FlowRow(mainAxisSpacing = 6.dp, crossAxisSpacing = 6.dp) {
                Tag(text = session.level.name, color = session.level.color.toColor())
                session.tags.forEach { tag ->
                    Tag(text = tag.name, color = tag.color.toColor())
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = session.title,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(18.dp))
            ProfilesInfo(speakers = session.speakers)
        }
        Divider(modifier = Modifier.height(8.dp), color = "#F5F5F5".toColor())

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "내용",
                color = "#2F2E32".toColor(),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = session.content.joinToString(separator = System.lineSeparator()),
                color = "#9B9B9B".toColor(),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun ProfilesInfo(
    modifier: Modifier = Modifier,
    speakers: List<User>
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        ProfileImages(speakers)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = speakers.joinToString(separator = " · ", transform = { it.name }))
    }
}

@Composable
private fun ProfileImages(
    speakers: List<User>
) {
    ProfileOverLayout {
        repeat(speakers.size) { index ->
            Image(
                painter = rememberImagePainter(
                    data = speakers[index].photoUrl,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.ic_android_92b9e9_24)
                        error(R.drawable.ic_android_92b9e9_24)
                    }
                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .background(color = Color.White)
                    .border(2.dp, color = Color.White, shape = CircleShape),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun ProfileOverLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        val layoutWidth = placeables.sumOf { it.width } - if (placeables.size > 1) {
            (placeables.size - 1) * 11.dp.toPx().toInt()
        } else {
            0
        }

        layout(
            width = layoutWidth,
            height = placeables.maxOfOrNull { it.height } ?: 0
        ) {
            var xPosition = 0

            placeables.forEach { placeable ->
                placeable.placeRelative(x = xPosition, y = 0)
                xPosition += 24.dp.toPx().toInt()
            }
        }
    }
}