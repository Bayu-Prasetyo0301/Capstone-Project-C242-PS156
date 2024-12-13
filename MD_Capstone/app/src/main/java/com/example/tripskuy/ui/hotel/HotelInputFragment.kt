package com.example.tripskuy.ui.hotel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tripskuy.R
import com.example.tripskuy.data.remote.response.HotelResponse
import com.example.tripskuy.data.remote.retrofit.ApiConfig
import com.example.tripskuy.databinding.FragmentHotelInputBinding

class HotelInputFragment : Fragment() {

    private var _binding: FragmentHotelInputBinding? = null
    private val binding get() = _binding!!
    private val apiService = ApiConfig.getApiServiceHotel()
    private lateinit var hotelViewModel: HotelViewModel

    private val cities = listOf(
        "Bandung", "Jakarta Utara", "Surabaya", "Jakarta Timur",
        "Jakarta Selatan", "Jakarta Barat", "Jakarta Pusat",
        "Yogyakarta", "Kepulauan Seribu", "Semarang"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelInputBinding.inflate(inflater, container, false)
        hotelViewModel = ViewModelProvider(this)[HotelViewModel::class.java]

        Log.d("HotelInputFragment", "ViewModel berhasil diakses")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, cities)
        binding.etCity.setAdapter(cityAdapter)

        binding.etCity.setOnClickListener {
            toggleCityChoices()
        }

        binding.etCity.setOnItemClickListener { parent, _, position, _ ->
            val selectedCity = parent?.getItemAtPosition(position).toString()
            binding.etCity.setText(selectedCity)
            binding.etCityChoices.visibility = View.GONE
            binding.etBudget.requestFocus()
            binding.etCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
        }

        binding.btnSearch.setOnClickListener {
            val location = binding.etCity.text.toString()
            val budget = binding.etBudget.text.toString().toIntOrNull()

            if (location.isNotEmpty() && budget != null) {
                val hotelData = mapOf("location" to location, "budget" to budget.toString())
                Log.d("HotelInputFragment", "Sending data to API: $hotelData")
                sendDataToApi(hotelData)
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        addCityButtons()
    }

    private fun toggleCityChoices() {
        if (binding.etCityChoices.visibility == View.VISIBLE) {
            binding.etCityChoices.visibility = View.GONE
            adjustBudgetPosition(false)
            binding.etCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
        } else {
            binding.etCityChoices.visibility = View.VISIBLE
            adjustBudgetPosition(true)
            binding.etCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0)
        }
    }

    private fun adjustBudgetPosition(show: Boolean) {
        if (show) {
            binding.etBudget.layoutParams = (binding.etBudget.layoutParams as ConstraintLayout.LayoutParams).apply {
                topToBottom = binding.etCityChoices.id
            }
        } else {
            binding.etBudget.layoutParams = (binding.etBudget.layoutParams as ConstraintLayout.LayoutParams).apply {
                topToBottom = binding.etCityChoices.id
            }
        }
    }

    private fun addCityButtons() {
        cities.forEach { city ->
            val button = Button(requireContext()).apply {
                text = city
                setBackgroundResource(R.drawable.rounded_button)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                setOnClickListener {
                    binding.etCity.setText(city)
                    binding.etCityChoices.visibility = View.GONE
                    binding.etBudget.requestFocus()
                    binding.etCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
                }
            }

            val params = GridLayout.LayoutParams()
            params.setMargins(8, 8, 8, 8)
            params.width = 0
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED)
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            button.layoutParams = params
            binding.etCityChoices.addView(button)
        }
    }

    private fun sendDataToApi(hotelData: Map<String, String>) {
        Log.d("HotelInputFragment", "Sending data to server with data: $hotelData")

        apiService.postHotel(hotelData.mapValues { it.value })
            .enqueue(object : retrofit2.Callback<HotelResponse> {
                override fun onResponse(call: retrofit2.Call<HotelResponse>, response: retrofit2.Response<HotelResponse>) {
                    Log.d("HotelInputFragment", "Response received: ${response.code()}")
                    Log.d("HotelInputFragment", "Response body: ${response.body()}")

                    if (response.isSuccessful) {
                        val hotelResponse = response.body()
                        hotelViewModel.setHotelResponse(hotelResponse ?: return)
                        Log.d("HotelInputFragment", "API call successful, preparing to send recommendations")

                        val hotelRecommendations = hotelResponse.recommendations.toTypedArray()
                        Log.d("HotelInputFragment", "Hotel Recommendations: $hotelRecommendations")

                        // Kirim data menggunakan Bundle
                        val bundle = Bundle()
                        bundle.putParcelableArray("hotelRecommendations", hotelRecommendations)

                        // Navigasi ke HotelRecommendationFragment
                        findNavController().navigate(R.id.action_hotelInputFragment_to_hotelRecommendationFragment, bundle)

                    } else {
                        Log.e("HotelInputFragment", "Failed response with code: ${response.code()}")
                        Toast.makeText(requireContext(), "Failed to send data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: retrofit2.Call<HotelResponse>, t: Throwable) {
                    Log.e("HotelInputFragment", "API call failed", t)
                    Toast.makeText(requireContext(), "Failed to send data", Toast.LENGTH_SHORT).show()
                }
            })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
