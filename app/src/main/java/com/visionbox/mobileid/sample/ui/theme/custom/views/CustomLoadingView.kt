package com.visionbox.mobileid.sample.ui.theme.custom.views

import android.content.Context
import android.view.LayoutInflater
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import com.visionbox.mobileid.sample.databinding.ComposeWrapperBinding
import com.visionbox.mobileid.sdk.enrolment.presentation.readDocument.customViews.ICustomDocumentReader

class CustomLoadingView(context: Context) :
    ConstraintLayout(context), ICustomDocumentReader.LoadingView {
    private val binding: ComposeWrapperBinding
    private var isLoading by mutableStateOf(false)
    private var title by mutableStateOf("")
    private var message by mutableStateOf("")

    init {
        binding = ComposeWrapperBinding.inflate(
            LayoutInflater.from(context),
            this
        )
        binding.composeView.setContent {
            LoadingViewCompose()
        }
    }

    override fun onPreFeatureLoading(message: String) {
        isLoading = true
        title = "Loading..."
        this.message = message
    }

    override fun onPostFeatureLoading() {
        isLoading = false
        title = ""
        this.message = ""
    }

    override fun onDownloadProgressChanged(progress: Int) {
        isLoading = true
        title = "Downloading Resources"
        this.message = "Progress: $progress%"
    }

    override fun hideLoading() {
        isLoading = false
        title = ""
        this.message = ""
    }

    @Composable
    fun LoadingViewCompose() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                // Show loading progress indicator
                CircularProgressIndicator()
            }
            // Show title and message
            Text(
                text = title,
                color = Color.Black,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = message,
                color = Color.Gray,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}