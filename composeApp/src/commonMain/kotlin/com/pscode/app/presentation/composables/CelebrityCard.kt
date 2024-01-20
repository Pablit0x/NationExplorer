package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes
import com.pscode.app.domain.model.CelebrityOverview
import com.pscode.app.presentation.screens.countries.detail.CardState
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun CelebrityCard(
    celebrity: CelebrityOverview?, onClick: () -> Unit, cardState: CardState, modifier: Modifier
) {
    if (celebrity != null) {


        ElevatedCard(modifier = modifier, shape = RoundedCornerShape(10)) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth().noRippleClickable(onClick),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Famous Person from that country"
                        )
                        Text(
                            text = SharedRes.string.most_famous_person,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    if (cardState == CardState.EXPENDED) {
                        Icon(imageVector = Icons.Default.KeyboardArrowUp, "Close tidbit")
                    } else {
                        Icon(imageVector = Icons.Default.KeyboardArrowDown, "Show tidbit")
                    }

                }

                if (cardState == CardState.EXPENDED) {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(200.dp).padding(vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        KamelImage(
                            resource = asyncPainterResource(celebrity.imageUrl),
                            contentDescription = "Celebrity Photo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.clip(shape = RoundedCornerShape(100))
                                .size(width = 100.dp, height = 100.dp)
                        )

                        Column(
                            modifier = Modifier.fillMaxSize().height(100.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = celebrity.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = celebrity.description,
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}