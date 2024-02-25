package com.example.diabeticdiary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getDrawable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var foodScanningButton = view.findViewById<ImageButton>(R.id.food_scanning_button)
        var barcodeScanningButton = view.findViewById<ImageButton>(R.id.barcode_scanning_button)
        var nutritionLabelScanningButton = view.findViewById<ImageButton>(R.id.ingredient_label_scanning_button)
        var suggestedRecipesButton = view.findViewById<ImageButton>(R.id.suggested_recipes_button)

        changeButtonColor(barcodeScanningButton)
        (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_layout_scan, BarcodeScanFragment()).addToBackStack(null).commit()


        foodScanningButton.setOnClickListener {
            changeButtonColor(foodScanningButton)
            Toast.makeText(requireContext(), "Food Scanning", Toast.LENGTH_SHORT).show()
        }

        barcodeScanningButton.setOnClickListener {
            changeButtonColor(barcodeScanningButton)
            (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_layout_scan, BarcodeScanFragment()).addToBackStack(null).commit()
            Toast.makeText(requireContext(), "Barcode Scanning", Toast.LENGTH_SHORT).show()
        }

        nutritionLabelScanningButton.setOnClickListener {
            changeButtonColor(nutritionLabelScanningButton)
            (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.frame_layout_scan, NutritionLabelScanFragment()).addToBackStack(null).commit()
            Toast.makeText(requireContext(), "Nutrition Label Scanning", Toast.LENGTH_SHORT).show()
        }

        suggestedRecipesButton.setOnClickListener {
            changeButtonColor(suggestedRecipesButton)
            Toast.makeText(requireContext(), "Suggested Recipes", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeButtonColor(btn: ImageButton) {
        var foodScanningButton = requireView().findViewById<ImageButton>(R.id.food_scanning_button)
        var barcodeScanningButton = requireView().findViewById<ImageButton>(R.id.barcode_scanning_button)
        var nutritionLabelScanningButton = requireView().findViewById<ImageButton>(R.id.ingredient_label_scanning_button)
        var suggestedRecipesButton = requireView().findViewById<ImageButton>(R.id.suggested_recipes_button)

        foodScanningButton.setImageResource(R.drawable.ic_lemon)
        foodScanningButton.background = getDrawable(requireContext(), R.drawable.button_rounded)

        barcodeScanningButton.setImageResource(R.drawable.ic_barcode)
        barcodeScanningButton.background = getDrawable(requireContext(), R.drawable.button_rounded)

        nutritionLabelScanningButton.setImageResource(R.drawable.ic_list)
        nutritionLabelScanningButton.background = getDrawable(requireContext(), R.drawable.button_rounded)

        suggestedRecipesButton.setImageResource(R.drawable.ic_utensil)
        suggestedRecipesButton.background = getDrawable(requireContext(), R.drawable.button_rounded)

        when (btn) {
            foodScanningButton -> {
                btn.setImageResource(R.drawable.ic_lemon_active)
            }
            barcodeScanningButton -> {
                btn.setImageResource(R.drawable.ic_barcode_active)
            }
            nutritionLabelScanningButton -> {
                btn.setImageResource(R.drawable.ic_list_active)
            }
            suggestedRecipesButton -> {
                btn.setImageResource(R.drawable.ic_utensil_active)
            }
        }

        btn.background = getDrawable(requireContext(), R.drawable.button_rounded_active)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}