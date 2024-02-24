package com.example.fourapps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.RelativeLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FlashlightFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FlashlightFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var isOn = true
    private var currentColor = R.color.color0

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
        return inflater.inflate(R.layout.fragment_flashlight, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val flashlightBtn = view.findViewById<ImageButton>(R.id.flashlight_button)

        val colors:List<Int> = listOf(
            R.color.color0,
            R.color.color1,
            R.color.color2,
            R.color.color3,
            R.color.color4,
            R.color.color5,
            R.color.color6,
            R.color.color7,
            R.color.color8,
            R.color.color9,
            R.color.color10
        )
        val colorBtns:List<Button> = listOf(
            view.findViewById(R.id.color0_button),
            view.findViewById(R.id.color1_button),
            view.findViewById(R.id.color2_button),
            view.findViewById(R.id.color3_button),
            view.findViewById(R.id.color4_button),
            view.findViewById(R.id.color5_button),
            view.findViewById(R.id.color6_button),
            view.findViewById(R.id.color7_button),
            view.findViewById(R.id.color8_button),
            view.findViewById(R.id.color9_button),
            view.findViewById(R.id.color10_button)
        )

        val rootLayout = view.findViewById<RelativeLayout>(R.id.relative_layout)

        for (i in 0..10) {
            colorBtns[i].setOnClickListener {
                currentColor = colors[i]
                when (isOn) {
                    true -> rootLayout.setBackgroundColor(requireContext().getColor(currentColor))
                    false -> flashlightBtn.backgroundTintList = requireContext().getColorStateList(currentColor)
                }
            }
        }

        flashlightBtn.setOnClickListener {
            if (isOn) {
                flashlightBtn.backgroundTintList = requireContext().getColorStateList(currentColor)
                flashlightBtn.setImageResource(R.drawable.power_button_black)
                rootLayout.setBackgroundColor(requireContext().getColor(R.color.black))
                isOn = false
            } else {
                flashlightBtn.backgroundTintList = requireContext().getColorStateList(R.color.black)
                flashlightBtn.setImageResource(R.drawable.power_button_white)
                rootLayout.setBackgroundColor(requireContext().getColor(currentColor))
                isOn = true
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FlashlightFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FlashlightFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}