package com.project.loginfirebase.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.loginfirebase.ui.theme.LoginFirebaseTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginFirebaseTheme {
                // A surface container using the 'background' color from the theme

                    RegistrationForm(modifier = Modifier)

            }
        }
    }

}

@Composable
fun RegistrationForm(modifier: Modifier) {
    val viewModel = viewModel<MainViewModel>()
    viewModel.clearValues()
    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is MainViewModel.ValidationEvent.Success -> {
                    Toast.makeText(context, "Registration completed", Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(context, "Registration not completed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Column(verticalArrangement = Arrangement.Center, modifier = Modifier
        .fillMaxSize()
        .background(
            MaterialTheme.colors.primaryVariant)) {
        OutlinedTextField(value = viewModel.state.email,
            onValueChange = {
                viewModel.onEvent(RegistrationFormEvent.EmailChanged(it))

            }, modifier.fillMaxWidth(), isError = viewModel.state.emailError != null,
            placeholder = {
                Text(text = "Email id", style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onSecondary)
            }, leadingIcon = {
                Icon(imageVector = Icons.Default.Star, contentDescription = null)
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ), colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                cursorColor = Color.Black,
                disabledLabelColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ))


        if (viewModel.state.emailError != null) {
            Text(text = state.emailError.toString(),
                color = MaterialTheme.colors.error, modifier = Modifier.align(Alignment.End))
        }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = viewModel.state.password,
            onValueChange = {
                viewModel.onEvent(RegistrationFormEvent.PasswordChanged(it))
            },
            modifier.fillMaxWidth(),
            isError = viewModel.state.passwordError != null,
            placeholder = {
                Text(text = "Password",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onSecondary)
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Password, contentDescription = null)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                cursorColor = Color.Black,
                disabledLabelColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        if (viewModel.state.passwordError != null) {
            Text(text = state.passwordError.toString(),
                color = MaterialTheme.colors.error, modifier = Modifier.align(Alignment.End))
        }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = viewModel.state.repeatedPassword,
            onValueChange = {
                viewModel.onEvent(RegistrationFormEvent.RepeatPasswordChanged(it))
            },
            modifier.fillMaxWidth(),
            isError = viewModel.state.repeatedPasswordError != null,
            placeholder = {
                Text(text = "Repeat Password",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onSecondary)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Password, contentDescription = null)
            },
            visualTransformation = PasswordVisualTransformation(),colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                cursorColor = Color.Black,
                disabledLabelColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        if (viewModel.state.repeatedPasswordError != null) {
            Text(text = state.repeatedPasswordError.toString(),
                color = MaterialTheme.colors.error)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = viewModel.state.acceptedTerms, onCheckedChange = {
                viewModel.onEvent(RegistrationFormEvent.AcceptTerms(it))
            })
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Accept Terms",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSecondary)

            if (viewModel.state.termsError != null) {
                Text(text = state.termsError.toString(),
                    color = MaterialTheme.colors.error, textAlign = TextAlign.End)
            }
            Spacer(modifier = Modifier.width(10.dp))

        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = {
                viewModel.onEvent(RegistrationFormEvent.Submit)
            },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                modifier = Modifier
                    .height(50.dp)
                    .width(120.dp)) {
                Text(text = "Register",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.primaryVariant)
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun RegistrationPreview() {
    RegistrationForm(Modifier.padding(10.dp))
}



