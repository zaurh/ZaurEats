package com.example.zaureats.presentation.screen.auth.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthTextField(
    modifier: Modifier = Modifier,
    errorTf: Boolean = false,
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    passwordVisibility: VisualTransformation? = null,
    leadingIcon: ImageVector? = null,
    placeHolder: String,
    trailingIcon: @Composable () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    TextField(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .fillMaxWidth()
            .border(
                1.dp,
                color = if (errorTf) Color.Red else Color.DarkGray,
                CircleShape
            ),
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp
        ),
        keyboardActions = KeyboardActions(onDone = {
            onDone()
        }),
        singleLine = true,
        visualTransformation = passwordVisibility ?: VisualTransformation.None,
        leadingIcon = leadingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                )
            }
        },
        placeholder = {
            Text(
                text = placeHolder,
                color = Color.Gray,
                modifier = Modifier.alpha(0.5f)
            )
        },
        trailingIcon = {
            trailingIcon()
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        keyboardOptions = keyboardOptions
    )
}