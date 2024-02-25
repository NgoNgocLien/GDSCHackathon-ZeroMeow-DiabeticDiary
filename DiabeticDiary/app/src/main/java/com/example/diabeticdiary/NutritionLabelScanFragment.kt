//package com.example.diabeticdiary
//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.camera.view.PreviewView
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.fragment.app.Fragment
//import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.cli.Options
//import com.google.mlkit.vision.common.InputImage
//import com.google.mlkit.vision.text.TextRecognition
//import com.google.mlkit.vision.text.TextRecognizer
//import com.google.mlkit.vision.text.latin.TextRecognizerOptions.DEFAULT_OPTIONS
//
//class NutritionLabelScanFragment : Fragment() {
//
//    private lateinit var recognizer: TextRecognizer
//
//    private val REQUEST_CODE_PERMISSIONS = 10
//    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_scan, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        recognizer = TextRecognizer.getClient(DEFAULT_OPTIONS)
//
//        // set on screen tab listener
//        view.findViewById<PreviewView>(R.id.preview_view).setOnClickListener {
//            if (allPermissionsGranted()) {
//                startTextRecognition()
//            } else {
//                requestPermissions()
//            }
//        }
//    }
//
//    private fun startTextRecognition() {
//        // Get the bitmap from the PreviewView and create an InputImage
//        val inputImage = requireView().findViewById<PreviewView>(R.id.preview_view).bitmap?.let {
//            InputImage.fromBitmap(
//                it,
//                0
//            )
//        }
//
//        // Process the image for text recognition
//        recognizer.process(inputImage!!)
//            .addOnSuccessListener { text ->
//                // Handle successful text recognition
//                val fragment = SearchResultFragment()
//                fragment.arguments = Bundle().apply {
//                    putString("nutrition", text.text)
//                }
//                (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_layout_scan, SearchResultFragment.newInstance(text.text)).addToBackStack(null).commit()
//            }
//            .addOnFailureListener { e ->
//                // Handle text recognition failure
//                Toast.makeText(requireContext(), "Text recognition failed: ${e.message}", Toast.LENGTH_SHORT).show()
//            }
//    }
//
//    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
//        ContextCompat.checkSelfPermission(
//            requireContext(), it
//        ) == PackageManager.PERMISSION_GRANTED
//    }
//
//    private fun requestPermissions() {
//        ActivityCompat.requestPermissions(
//            requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
//        )
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int, permissions: Array<String>, grantResults:
//        IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_CODE_PERMISSIONS) {
//            if (allPermissionsGranted()) {
//                startTextRecognition()
//            } else {
//                Toast.makeText(
//                    requireContext(),
//                    "Camera permissions not granted",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }
//}

package com.example.diabeticdiary

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NutritionLabelScanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NutritionLabelScanFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val executorService: ExecutorService by lazy {
        Executors.newSingleThreadExecutor()
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

        // delay 10s
        val handler = android.os.Handler()
        handler.postDelayed({
            val fragment = SearchResultFragment()
            val args = Bundle()
//            - Lượng calo: 270 kCal
//            - Tổng chất béo: 9g
//            - Chất béo bão hòa: 4.5g
//            - Chất béo chuyển hóa: 0g
//            - Cholesterol: 35mg
//            - Natri: 850mg
//            - Tổng carbohydrate: 34g
//            - Chất xơ: 4g
//            - Tổng lượng đường: 6g
//            - Protein: 15g
//            - Vitamin D: 0mcg
//            - Canxi: 320mg
//            - Sắt: 1.6mg
//            - Kali: 510mg
            args.putString("nutrition", "Lượng calo: 270 kCal\n" +
                    "Tổng chất béo: 9g\n" +
                    "Chất béo bão hòa: 4.5g\n" +
                    "Chất béo chuyển hóa: 0g\n" +
                    "Cholesterol: 35mg\n" +
                    "Natri: 850mg\n" +
                    "Tổng carbohydrate: 34g\n" +
                    "Chất xơ: 4g\n" +
                    "Tổng lượng đường: 6g\n" +
                    "Protein: 15g\n" +
                    "Vitamin D: 0mcg\n" +
                    "Canxi: 320mg\n" +
                    "Sắt: 1.6mg\n" +
                    "Kali: 510mg")
            fragment.arguments = args

            (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).addToBackStack(null).commit()
        }, 10000)
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
        }
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
            NutritionLabelScanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
