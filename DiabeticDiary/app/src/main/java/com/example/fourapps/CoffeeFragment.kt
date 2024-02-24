package com.example.fourapps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CoffeeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CoffeeFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_coffee, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val radioBtn1 = view.findViewById<RadioButton>(R.id.option1_question1)
        val radioBtn2 = view.findViewById<RadioButton>(R.id.option2_question1)
        val radioBtn3 = view.findViewById<RadioButton>(R.id.option3_question1)

        val checkBox1 = view.findViewById<CheckBox>(R.id.option1_question2)
        val checkBox2 = view.findViewById<CheckBox>(R.id.option2_question2)

        val payBtn = view.findViewById<Button>(R.id.pay_button)

        payBtn.setOnClickListener {
            if (!radioBtn1.isChecked && !radioBtn2.isChecked && !radioBtn3.isChecked) {
                Toast.makeText(requireContext(), "Please select a kind of coffee", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var totalPrice: Double = 0.0

            totalPrice += when {
                radioBtn1.isChecked -> 2.5
                radioBtn2.isChecked -> 2.0
                radioBtn3.isChecked -> 3.0
                else -> 0.0
            }

            if (checkBox1.isChecked && checkBox2.isChecked) {
                totalPrice += 0.75
            } else if (checkBox1.isChecked) {
                totalPrice += 0.5
            } else if (checkBox2.isChecked) {
                totalPrice += 0.25
            }

            Toast.makeText(
                requireContext(),
                "Total price for your order is $$totalPrice",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WidgetsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CoffeeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}