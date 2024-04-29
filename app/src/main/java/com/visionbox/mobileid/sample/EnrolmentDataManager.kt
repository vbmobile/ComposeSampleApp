package com.visionbox.mobileid.sample

import android.graphics.Bitmap
import com.visionbox.mobileid.sdk.enrolment.data.documentReader.documentData.DocumentReaderReport
import com.visionbox.mobileid.sdk.enrolment.data.mobile.api.biometric.models.FaceCaptureReport
import com.visionbox.mobileid.sdk.enrolment.data.mobile.api.biometric.models.MatchReport
import com.visionbox.mobileid.sdk.enrolment.data.mobile.api.boarding_pass.models.BoardingPass

object EnrolmentDataManager {
    var documentReaderReport: DocumentReaderReport? = null
    var candidatePhoto: Bitmap? = null
    var referencePhoto: Bitmap? = null
    var boardingPass: BoardingPass? = null
    var faceCaptureReport: FaceCaptureReport? = null
    var matchReport: MatchReport? = null
    var candidateHash: String? = null
    var referenceHash: String? = null
}