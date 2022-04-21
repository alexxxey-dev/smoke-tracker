package com.eatmybrain.smoketracker.ui.screens.strain_search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eatmybrain.smoketracker.R
import com.eatmybrain.smoketracker.ui.theme.SmokeTrackerTheme
import com.eatmybrain.smoketracker.util.Constants

@Composable
fun StrainSearchScreen(viewModel: StrainSearchViewModel = hiltViewModel()) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    StrainSearch(
        searchQuery = searchQuery,
        updateQuery = { searchQuery = it },
        onSearch = { viewModel.updateStrainList(searchQuery) }
    )

}

@Composable
fun StrainSearch(
    searchQuery: String,
    updateQuery: (String) -> Unit,
    onSearch: () -> Unit
) {
    Column {
        StrainSearchBar(
            query = searchQuery,
            updateQuery = updateQuery,
            modifier = Modifier
                .padding(top = 16.dp, start = 36.dp, end = 36.dp)
                .fillMaxWidth(),
            onSearch = onSearch
        )

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(searchQuery)
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StrainSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    updateQuery: (String) -> Unit,
    onSearch: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current

    val searchClicked = {
        onSearch()
        keyboardController?.hide()
        focusManager.clearFocus()
    }
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        elevation = 2.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SearchEditText(
                query = query,
                updateQuery = updateQuery,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 6.dp, bottom = 6.dp, start = 13.dp, end = 13.dp),
                onSearch = searchClicked
            )
            Icon(
                painter = painterResource(R.drawable.ic_search),
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp, end = 12.dp)
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource
                    ) { searchClicked() }
                    .size(width = 20.dp, height = 20.dp),
                contentDescription = "Search icon",
                tint = MaterialTheme.colors.secondary
            )
        }


    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchEditText(
    query: String,
    updateQuery: (String) -> Unit,
    modifier: Modifier,
    onSearch: () -> Unit
) {

    Box( modifier = modifier) {
        if(query.isEmpty()){
            Text(
                text = stringResource(R.string.enter_strain_name),
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 14.sp,
                    color = MaterialTheme.colors.onBackground.copy(alpha = Constants.ALPHA_GREY)
                ),
                textAlign = TextAlign.Start
            )
        }
        BasicTextField(
            value = query,
            textStyle = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
            onValueChange = updateQuery,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search,capitalization = KeyboardCapitalization.Sentences),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch()
            }),
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@Preview
@Composable
fun SearchScreenPreview() {
    SmokeTrackerTheme {
        Scaffold {
            StrainSearch(searchQuery = "", updateQuery = {}, onSearch = {})
        }
    }

}