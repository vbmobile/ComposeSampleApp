package com.visionbox.mobileid.sample.injection

import com.visionbox.mobileid.sdk.enrolment.data.biometricFaceCapture.BiometricFaceCaptureParameters
import com.visionbox.mobileid.sdk.enrolment.data.boardingPass.parse.BoardingPassStringParserParameters
import com.visionbox.mobileid.sdk.enrolment.data.boardingPass.scan.BoardingPassScanParameters
import com.visionbox.mobileid.sdk.enrolment.data.documentReader.DocumentReaderParameters

data class Parameters (
    val documentReaderParameters: DocumentReaderParameters,
    val faceCaptureParameters: BiometricFaceCaptureParameters,
    val scanBoardingPassParameters: BoardingPassScanParameters,
    val parseBoardingPassParameters: BoardingPassStringParserParameters,
)