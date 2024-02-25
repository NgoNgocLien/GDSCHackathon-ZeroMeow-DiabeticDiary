package com.example.diabeticdiary

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.diabeticdiary.util.BarcodeScanner
import com.example.diabeticdiary.util.BarcodeScannerListener
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BarcodeScanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BarcodeScanFragment : Fragment(), BarcodeScannerListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val executorService: ExecutorService by lazy {
        Executors.newSingleThreadExecutor()
    }

    private val barcodeScanner by lazy {
        BarcodeScanner(this)
    }

    private val CAMERA_REQUEST_CODE = 101
    private val TAG = "SCAN_FRAGMENT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_layout_scan_container, ScanCameraFragment()).addToBackStack(null).commit()
        setupCamera()
    }


    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE && isCameraPermissionGranted()) {
            startCamera()
        }
    }

    private fun setupCamera() {
        if (isCameraPermissionGranted()) {
            startCamera()
        } else {
            requestPermission()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().apply {
                setSurfaceProvider(requireView().findViewById<PreviewView>(R.id.preview_view).surfaceProvider)
            }
            val imageAnalyzer = ImageAnalysis.Builder().build().apply {
                setAnalyzer(executorService, getImageAnalyzerListener())
            }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageAnalyzer
                )
            } catch (throwable: Throwable) {
                Log.e(TAG, "Use case binding failed", throwable)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun getImageAnalyzerListener(): ImageAnalysis.Analyzer {
        return ImageAnalysis.Analyzer { imageProxy ->
            val image = imageProxy.image ?: return@Analyzer
            val inputImage = InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
            barcodeScanner.scanImage(inputImage) {
                imageProxy.close()
            }
        }
    }

    override fun onSuccessScan(result: List<Barcode>) {
        var barcode = ""
        for (bc in result) {
            Log.d(TAG, "Barcode value: ${bc.rawValue}")
            barcode = bc.rawValue ?: ""
            if (barcode.isNotEmpty())
                break
        }
//        result.forEachIndexed { index, barcode ->
////            Toast.makeText(requireContext(), "Barcode value: ${barcode.rawValue}", Toast.LENGTH_SHORT).show()
//            Log.d(TAG, "Barcode value: ${barcode.rawValue}")
//        }

        // TODO: Connect to the database and retrieve the food item with the barcode
        if (barcode == "512345000107") {
            (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_layout, SearchResultFragment()).addToBackStack(null).commit()
        }
    }

    override fun onScanFailed() {
        Toast.makeText(requireContext(), "Fail", Toast.LENGTH_SHORT).show()
    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(),
            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE)
    }

    override fun onDestroy() {
        super.onDestroy()
        barcodeScanner.closeScanner()
        executorService.shutdown()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TipCalculatorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BarcodeScanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}