package com.firman.movie.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Animation
import androidx.compose.material.icons.rounded.AutoStories
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.Castle
import androidx.compose.material.icons.rounded.EmojiEmotions
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.FamilyRestroom
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Gavel
import androidx.compose.material.icons.rounded.HistoryEdu
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material.icons.rounded.NightsStay
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.RocketLaunch
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material.icons.rounded.Theaters
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.firman.movie.feature.browse.domain.model.Genre

@Composable
fun GenreCard(
    genre: Genre,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val genreIcon = getGenreIcon(genre.id)
    val genreGradient = getGenreGradient(genre.id)

    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = genreGradient
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = genreIcon,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.08f),
                modifier = Modifier
                    .size(72.dp)
                    .align(Alignment.Center)
                    .padding(bottom = 12.dp)
            )

            Icon(
                imageVector = genreIcon,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.15f),
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
            )

            Text(
                text = genre.name,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }
    }
}

private fun getGenreIcon(genreId: Int): ImageVector {
    return when (genreId) {
        28 -> Icons.Rounded.Bolt
        12 -> Icons.Rounded.Explore
        16 -> Icons.Rounded.Animation
        35 -> Icons.Rounded.EmojiEmotions
        80 -> Icons.Rounded.Gavel
        99 -> Icons.Rounded.Videocam
        18 -> Icons.Rounded.Theaters
        10751 -> Icons.Rounded.FamilyRestroom
        14 -> Icons.Rounded.Castle
        36 -> Icons.Rounded.HistoryEdu
        27 -> Icons.Rounded.NightsStay
        10402 -> Icons.Rounded.MusicNote
        9648 -> Icons.Rounded.Psychology
        10749 -> Icons.Rounded.Favorite
        878 -> Icons.Rounded.RocketLaunch
        10770 -> Icons.Rounded.Videocam
        53 -> Icons.Rounded.WarningAmber
        10752 -> Icons.Rounded.LocalFireDepartment
        37 -> Icons.Rounded.SelfImprovement
        else -> Icons.Rounded.AutoStories
    }
}

private fun getGenreGradient(genreId: Int): List<Color> {
    return when (genreId) {
        28 -> listOf(Color(0xFF1A0A0A), Color(0xFF2D1515))
        12 -> listOf(Color(0xFF0A1A15), Color(0xFF152D25))
        16 -> listOf(Color(0xFF1A0A1A), Color(0xFF2D152D))
        35 -> listOf(Color(0xFF1A1A0A), Color(0xFF2D2D15))
        80 -> listOf(Color(0xFF0A0A1A), Color(0xFF15152D))
        99 -> listOf(Color(0xFF0F1A15), Color(0xFF1A2D25))
        18 -> listOf(Color(0xFF1A100A), Color(0xFF2D1A15))
        10751 -> listOf(Color(0xFF0A1A1A), Color(0xFF152D2D))
        14 -> listOf(Color(0xFF100A1A), Color(0xFF1A152D))
        36 -> listOf(Color(0xFF1A150A), Color(0xFF2D2515))
        27 -> listOf(Color(0xFF0A0A0A), Color(0xFF1A0A0F))
        10402 -> listOf(Color(0xFF1A0A15), Color(0xFF2D1525))
        9648 -> listOf(Color(0xFF0A0F1A), Color(0xFF15192D))
        10749 -> listOf(Color(0xFF1A0A10), Color(0xFF2D151A))
        878 -> listOf(Color(0xFF0A101A), Color(0xFF151A2D))
        53 -> listOf(Color(0xFF0F0A0A), Color(0xFF1A1515))
        10752 -> listOf(Color(0xFF0F0F0A), Color(0xFF1A1A15))
        37 -> listOf(Color(0xFF1A150F), Color(0xFF2D251A))
        else -> listOf(Color(0xFF0A1016), Color(0xFF152028))
    }
}
