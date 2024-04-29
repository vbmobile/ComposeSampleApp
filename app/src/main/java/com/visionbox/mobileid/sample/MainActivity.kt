package com.visionbox.mobileid.sample

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.visionbox.mobileid.sample.injection.Parameters
import com.visionbox.mobileid.sample.ui.theme.SampleComposeAppTheme
import com.visionbox.mobileid.sdk.enrolment.IEnrolment
import com.visionbox.mobileid.sdk.enrolment.data.biometricFaceMatch.BiometricMatchParameters
import com.visionbox.mobileid.sdk.enrolment.data.mobile.api.biometric.models.TemplateOption
import com.visionbox.mobileid.sdk.enrolment.data.mobile.api.readDocument.model.DocumentReaderActivityResult
import com.visionbox.mobileid.sdk.enrolment.data.mobile.api.readDocument.result.launcher.DocumentReaderResultLauncher
import com.visionbox.mobileid.sdk.enrolment.data.mobile.api.subject.models.Subject
import com.visionbox.mobileid.sdk.enrolment.data.subject.AddSubjectParameters
import com.visionbox.mobileid.sdk.enrolment.data.subject.subjectBuilder.BuildSubjectParameters
import org.koin.java.KoinJavaComponent.get

lateinit var subject: Subject

class MainActivity : ComponentActivity() {

    private val documentReaderResultLauncher = registerForActivityResult(
        DocumentReaderResultLauncher()
    ){ result: DocumentReaderActivityResult ->
        if (result.success) {
            val data = result.documentReaderReport
            Toast.makeText(this, "Document Number: ${data?.documentData?.documentNumber}", Toast.LENGTH_SHORT).show()
        } else {
            val error = result.documentReaderError
            Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyScreen(this, documentReaderResultLauncher)
                }
            }
        }
    }
}

@Composable
fun MyScreen(context: Context, launcher: ActivityResultLauncher<Intent>) {
    val enrolment: IEnrolment = get(IEnrolment::class.java)
    val parameters: Parameters = get(Parameters::class.java)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { enrolment.readDocument(context, parameters.documentReaderParameters, launcher) },
            modifier = Modifier.padding(16.dp)) {
            Text(text = "Read Document")
        }
        Button(
            onClick = { enrolment.biometricFaceCapture(context, parameters.faceCaptureParameters, launcher) },
            modifier = Modifier.padding(16.dp)) {
            Text(text = "Face Capture")
        }
        Button(
            onClick = { enrolment.matchBiometrics(context, BiometricMatchParameters(
                candidate = EnrolmentDataManager.candidatePhoto,
                reference = EnrolmentDataManager.referencePhoto,
                templateOption = TemplateOption.ALL, // Default is TemplateOption.None
                showErrors = true,
                candidateHash = EnrolmentDataManager.candidateHash,
                referenceHash = EnrolmentDataManager.referenceHash
            ), launcher) },
            modifier = Modifier.padding(16.dp)) {
            Text(text = "Biometric Match")
        }
        Button(
            onClick = { enrolment.scanBoardingPass(context, parameters.scanBoardingPassParameters, launcher) },
            modifier = Modifier.padding(16.dp)) {
            Text(text = "Scan Boarding Pass")
        }
        Button(
            onClick = { enrolment.parseBoardingPass(context, parameters.parseBoardingPassParameters, launcher) },
            modifier = Modifier.padding(16.dp)) {
            Text(text = "Parse Boarding Pass")
        }
        Button(
            onClick = { enrolment.buildSubject(context as Activity,
                BuildSubjectParameters(
                    documentData = EnrolmentDataManager.documentReaderReport!!.documentData,
                    documentPhoto = EnrolmentDataManager.referencePhoto!!,
                    enrolmentPhoto = EnrolmentDataManager.candidatePhoto!!,
                    boardingPass = EnrolmentDataManager.boardingPass,
                    processReport = EnrolmentDataManager.faceCaptureReport,
                    matchReport = EnrolmentDataManager.matchReport,
                    documentReaderReport = EnrolmentDataManager.documentReaderReport
                )
            ) },
            modifier = Modifier.padding(16.dp)) {
            Text(text = "Build Subject")
        }
        Button(
            onClick = { enrolment.addSubject(
                context,
                AddSubjectParameters(
                    showErrors = true,
                    subject = subject
                ),
                launcher) },
            modifier = Modifier.padding(16.dp)) {
            Text(text = "Add subject")
        }
    }
}
