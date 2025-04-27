package com.anhar.atcadaptor.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.common.Dimens
import com.anhar.atcadaptor.common.Dimens.SmallPadding


@Composable
fun ColumnScope.CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    trailingIcon: ImageVector,
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    isError: Boolean,
    isPassword: Boolean = false,
    showPassword: Boolean = false,
    onShowPassword: ((Boolean) -> Unit)? = null,
    errorMessage: String = ""
) {
    OutlinedTextField(
        modifier = modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth()
            ,
        value = value,
        label = {
            Text(text = label , style = MaterialTheme.typography.bodyMedium)
        },
        placeholder = {
            Text(text = placeholder,style = MaterialTheme.typography.bodyMedium)
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        shape = RoundedCornerShape(40),
        singleLine = true,
        trailingIcon = {
            Row {
                if (isPassword) {
                    Icon(
                        if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null,
                        Modifier
                            .clickable {
                                onShowPassword?.let {
                                    it(!showPassword)
                                }
                            }
                            .padding(horizontal = SmallPadding)
                    )
                }
                Icon(
                    trailingIcon,
                    contentDescription = null,
                    modifier = Modifier.padding(end = Dimens.MediumPadding)
                )

            }

        },
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        isError = isError,
        visualTransformation =
        if (isPassword && !showPassword)
            PasswordVisualTransformation()
        else
            VisualTransformation.None,
        supportingText = if (isError) {{
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }} else null
    )
}


@Composable
fun ColumnScope.CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    trailingIcon: @Composable ()-> Unit,
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    isError: Boolean,
    errorMessage: String = "",
    maxLines : Int = 1
) {

    OutlinedTextField(
        modifier = modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth()
        ,
        value = value,
        maxLines = maxLines,
        label = {
            Text(text = label , style = MaterialTheme.typography.bodyMedium)
        },
        placeholder = {
            Text(text = placeholder , style = MaterialTheme.typography.bodyMedium.copy(
                color = colorResource(id = R.color.body)
            ))
        },
        shape = RoundedCornerShape(SmallPadding),
        singleLine = maxLines == 1 ,
        trailingIcon = trailingIcon,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        isError = isError ,
        supportingText = if (isError) {{
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        }} else null ,

    )
}


