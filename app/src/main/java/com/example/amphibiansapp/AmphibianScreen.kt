package com.example.amphibiansapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmphibianApp(
    viewModel: AmphibianViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Amphibians")
                }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            when (val uiState = viewModel.amphibianUiState) {
                is AmphibianUiState.Loading -> LoadingScreen()
                is AmphibianUiState.Success -> AmphibianList(uiState.amphibians)
                is AmphibianUiState.Error -> ErrorScreen(
                    onRetryClick = { viewModel.getAmphibians() }
                )
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(onRetryClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Failed to load amphibians")
        Button(
            onClick = onRetryClick,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Retry")
        }
    }
}

@Composable
fun AmphibianList(amphibians: List<Amphibian>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(amphibians) { amphibian ->
            AmphibianCard(amphibian)
        }
    }
}

@Composable
fun AmphibianCard(amphibian: Amphibian) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Name and type
            Text(
                text = "${amphibian.name} (${amphibian.type})",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(amphibian.img_src)
                    .crossfade(true)
                    .build(),
                contentDescription = amphibian.name,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                text = amphibian.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}