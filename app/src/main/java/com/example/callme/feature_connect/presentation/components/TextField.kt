package com.example.callme.feature_connect.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
) {
    Box(
        modifier = Modifier
            .clip(
                RoundedCornerShape(8.dp)
            )
            .then(modifier)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 14.dp
                )
        )

        if (value.isEmpty() && !placeholder.isNullOrBlank()) {
            Text(
                text = placeholder,
                color = Color.Gray,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 14.dp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.primaryContainer
            )
            .statusBarsPadding()
    ) {
        TextField(
            value = "Hello world",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(30.dp))
        TextField(
            value = "",
            placeholder = "Type something...",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}
