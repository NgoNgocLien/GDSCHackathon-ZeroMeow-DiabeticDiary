package com.example.fourapps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TipCalculatorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TipCalculatorFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_tip_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val billAmountET = view.findViewById<EditText>(R.id.billAmountEditText)
        val tipPercentageET = view.findViewById<EditText>(R.id.tipPercentEditText)
        val numberOfPeopleET = view.findViewById<EditText>(R.id.numberOfPeopleEditText)

        val calculateTipButton = view.findViewById<Button>(R.id.calculateTipButton)

        val billAmountResultValueTV = view.findViewById<TextView>(R.id.billAmountResultValueTextView)
        val totalAmountValueTV = view.findViewById<TextView>(R.id.totalAmountValueTextView)
        val totalPerPersonValueTV = view.findViewById<TextView>(R.id.totalPerPersonValueTextView)

        calculateTipButton.setOnClickListener {
            if (billAmountET.text.toString().isEmpty() || tipPercentageET.text.toString().isEmpty() || numberOfPeopleET.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Please enter all values", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val billAmount = billAmountET.text.toString().toDouble()
            val tipPercentage = tipPercentageET.text.toString().toDouble()
            val numberOfPeople = numberOfPeopleET.text.toString().toDouble()

            val tipAmount = billAmount * tipPercentage / 100
            val totalAmount = billAmount + tipAmount
            val totalPerPerson = totalAmount / numberOfPeople

            billAmountResultValueTV.text = String.format("$%.2f", billAmount)
            totalAmountValueTV.text = String.format("$%.2f", totalAmount)
            totalPerPersonValueTV.text = String.format("$%.2f", totalPerPerson)
        }
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
            TipCalculatorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}