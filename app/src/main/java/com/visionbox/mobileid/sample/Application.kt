package com.visionbox.mobileid.sample

import android.app.Application
import androidx.camera.core.CameraSelector
import com.visionbox.mobileid.sample.injection.Parameters
import com.visionbox.mobileid.sample.ui.theme.custom.views.CustomLoadingView
import com.visionbox.mobileid.sdk.enrolment.EnrolmentBuilder
import com.visionbox.mobileid.sdk.enrolment.IEnrolment
import com.visionbox.mobileid.sdk.enrolment.data.EnrolmentConfig
import com.visionbox.mobileid.sdk.enrolment.data.biometricFaceCapture.BiometricFaceCaptureParameters
import com.visionbox.mobileid.sdk.enrolment.data.boardingPass.models.BarcodeFormat
import com.visionbox.mobileid.sdk.enrolment.data.boardingPass.models.BoardingPassData
import com.visionbox.mobileid.sdk.enrolment.data.boardingPass.parse.BoardingPassStringParserParameters
import com.visionbox.mobileid.sdk.enrolment.data.boardingPass.scan.BoardingPassScanParameters
import com.visionbox.mobileid.sdk.enrolment.data.documentReader.DocumentReaderConfig
import com.visionbox.mobileid.sdk.enrolment.data.documentReader.DocumentReaderParameters
import com.visionbox.mobileid.sdk.enrolment.data.mobile.api.APIConfig
import com.visionbox.mobileid.sdk.enrolment.data.mobile.api.MobileAPILogLevel
import com.visionbox.mobileid.sdk.enrolment.presentation.biometricFaceCapture.model.CameraConfig
import com.visionbox.mobileid.sdk.enrolment.presentation.readDocument.customViews.DocumentReaderCustomViews
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.net.URL
import java.util.concurrent.TimeUnit

class ComposeApp: Application() {

    override fun onCreate() {
        super.onCreate()

        val apiConfig = APIConfig(
            baseUrl = URL(getString(R.string.api_url)),
            timeout = 30, // timeout in seconds
            logLevel = MobileAPILogLevel.BASIC,
            apiKey = getString(R.string.api_key) // Get API Key from Vision-Box
        )

        val enrolmentConfig = EnrolmentConfig(
            apiConfig = apiConfig
        )

        val documentReaderConfig = DocumentReaderConfig(
            multipageProcessing = true,
            databaseId = "Full"
        )

        val enrolmentModule = module {
            single<IEnrolment> {
                EnrolmentBuilder.of(
                    context = this@ComposeApp,
                    config = enrolmentConfig
                ).withDocumentReaderConfig(documentReaderConfig)
                    .withDocumentReaderCustomViews(
                        DocumentReaderCustomViews(
                            loadingView = CustomLoadingView::class.java
                        )
                    ).build()
            }
        }

        val parameters = module {
            single<Parameters> {
                Parameters(
                    documentReaderParameters = DocumentReaderParameters(
                        rfidRead = true,
                        showRFIDStatus = true,
                        showPreview = true,
                        showSecurityCheck = false,
                        showErrors = true,
                        mrzReadTimeout = TimeUnit.SECONDS.toMillis(30),
                        rfidReadTimeout = TimeUnit.SECONDS.toMillis(30)
                    ),
                    faceCaptureParameters = BiometricFaceCaptureParameters(
                        showPreview = true,
                        showErrors = true,
                        cameraConfig = CameraConfig(
                            enableCameraToggle = true,
                            defaultCamera = CameraSelector.DEFAULT_FRONT_CAMERA
                        )
                    ),
                     scanBoardingPassParameters = BoardingPassScanParameters(
                         showPreview = true,
                         showErrors = true,
                         validate = true
                     ),
                     parseBoardingPassParameters = BoardingPassStringParserParameters(
                         boardingPassData = BoardingPassData("M1ALZATE MORA/LINA MAREEVSMZZDLISFNCEZY7603 275 8A  0592 10A1858877497", BarcodeFormat.AZTEC),
                         showPreview = true,
                         showErrors = true,
                         validate = true
                     )
                )
            }
        }

        startKoin {
            androidContext(this@ComposeApp)
            modules(enrolmentModule, parameters)
        }
    }
}