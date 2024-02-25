package com.example.diabeticdiary.util

import com.google.mlkit.vision.barcode.common.Barcode

interface BarcodeScannerListener {

    fun onSuccessScan(result: List<Barcode>)

    fun onScanFailed()
}