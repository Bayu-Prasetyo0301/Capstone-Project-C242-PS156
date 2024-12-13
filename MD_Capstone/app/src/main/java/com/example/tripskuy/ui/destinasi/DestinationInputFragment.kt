package com.example.tripskuy.ui.destinasi

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
import com.example.tripskuy.data.remote.response.PlaceResponse
import com.example.tripskuy.data.remote.retrofit.ApiConfig
import com.example.tripskuy.data.request.DestinationRequest
import com.example.tripskuy.databinding.FragmentDestinationInputBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationInputFragment : Fragment() {
    private var _binding: FragmentDestinationInputBinding? = null
    private val binding get() = _binding!!
    private val apiService = ApiConfig.getApiServicePlace()
    private lateinit var destinationViewModel: DestinationViewModel

    private val cities = listOf(
        "Bandung", "Jakarta Utara", "Surabaya", "Jakarta Timur",
        "Jakarta Selatan", "Jakarta Barat", "Jakarta Pusat",
        "Yogyakarta", "Kepulauan Seribu", "Semarang"
    )

    private val categories = listOf(
        "Tempat Ibadah", "Budaya", "Pusat Perbelanjaan", "Bahari", "Taman Hiburan", "Cagar Alam"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDestinationInputBinding.inflate(inflater, container, false)
        destinationViewModel = ViewModelProvider(this)[DestinationViewModel::class.java]
        Log.d("DestinationInputFragment", "ViewModel berhasil diakses")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, cities)
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, categories)

        binding.etCity.setAdapter(cityAdapter)
        binding.etCategory.setAdapter(categoryAdapter)

        binding.etCity.setOnClickListener {
            toggleCityChoices()
        }

        binding.etCategory.setOnClickListener {
            toggleCategoryChoices()
        }

        binding.etCity.setOnItemClickListener { parent, _, position, _ ->
            val selectedCity = parent?.getItemAtPosition(position).toString()
            binding.etCity.setText(selectedCity)
            binding.etCityChoices.visibility = View.GONE
            adjustCityPosition(false)
            binding.etCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
            binding.etCategory.requestFocus()
        }

        binding.etCategory.setOnItemClickListener { parent, _, position, _ ->
            val selectedCategory = parent?.getItemAtPosition(position).toString()
            binding.etCategory.setText(selectedCategory)
            binding.etCategoryChoices.visibility = View.GONE
            adjustCategoryPosition(false)
            binding.etBudget.requestFocus()
        }

        binding.btnSearch.setOnClickListener {
            val location = binding.etCity.text.toString()
            val category = binding.etCategory.text.toString()
            val budget = binding.etBudget.text.toString().toIntOrNull()

            if (location.isNotEmpty() && category.isNotEmpty() && budget != null) {
                sendDataToApi(location, category, budget)
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        addCityButtons()
        addCategoryButtons()
    }

    private fun toggleCityChoices() {
        if (binding.etCityChoices.visibility == View.VISIBLE) {
            binding.etCityChoices.visibility = View.GONE
            adjustCityPosition(false)
            binding.etCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
        } else {
            binding.etCityChoices.visibility = View.VISIBLE
            adjustCityPosition(true)
            binding.etCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0)
        }
    }

    private fun toggleCategoryChoices() {
        if (binding.etCategoryChoices.visibility == View.VISIBLE) {
            binding.etCategoryChoices.visibility = View.GONE
            adjustCategoryPosition(false)
        } else {
            binding.etCategoryChoices.visibility = View.VISIBLE
            adjustCategoryPosition(true)
        }
    }

    private fun adjustCityPosition(show: Boolean) {
        if (show) {
            binding.etCategory.layoutParams = (binding.etCategory.layoutParams as ConstraintLayout.LayoutParams).apply {
                topToBottom = binding.etCityChoices.id
            }
            binding.etBudget.layoutParams = (binding.etBudget.layoutParams as ConstraintLayout.LayoutParams).apply {
                topToBottom = binding.etCategory.id
            }
        } else {
            binding.etCategory.layoutParams = (binding.etCategory.layoutParams as ConstraintLayout.LayoutParams).apply {
                topToBottom = binding.etCity.id
            }
            binding.etBudget.layoutParams = (binding.etBudget.layoutParams as ConstraintLayout.LayoutParams).apply {
                topToBottom = binding.etCategory.id
            }
        }
    }

    private fun adjustCategoryPosition(show: Boolean) {
        if (show) {
            binding.etBudget.layoutParams = (binding.etBudget.layoutParams as ConstraintLayout.LayoutParams).apply {
                topToBottom = binding.etCategoryChoices.id
            }
        } else {
            binding.etBudget.layoutParams = (binding.etBudget.layoutParams as ConstraintLayout.LayoutParams).apply {
                topToBottom = binding.etCategory.id
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
                    adjustCityPosition(false)
                    binding.etCategory.requestFocus()
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

    private fun addCategoryButtons() {
        categories.forEach { category ->
            val button = Button(requireContext()).apply {
                text = category
                setBackgroundResource(R.drawable.rounded_button)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                setOnClickListener {
                    binding.etCategory.setText(category)
                    binding.etCategoryChoices.visibility = View.GONE
                    adjustCategoryPosition(false)
                    binding.etBudget.requestFocus()
                }
            }

            val params = GridLayout.LayoutParams()
            params.setMargins(8, 8, 8, 8)
            params.width = 0
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED)
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            button.layoutParams = params
            binding.etCategoryChoices.addView(button)
        }
    }

    private fun sendDataToApi(location: String, category: String, budget: Int) {
        // Gunakan data class DestinationRequest
        val destinasiData = DestinationRequest(
            category = category,
            city = location,
            ticket_price = budget
        )

        Log.d("DestinationInputFragment", "Sending data to API: $destinasiData")

        apiService.postDestination(destinasiData).enqueue(object : Callback<PlaceResponse> {
            override fun onResponse(call: Call<PlaceResponse>, response: Response<PlaceResponse>) {
                if (response.isSuccessful) {
                    Log.d("DestinationInputFragment", "Data sent successfully: ${response.body()}")
                    val recommendations = response.body()?.recommendations?.toTypedArray()
                    Log.d("DestinationInputFragment", "Recommendations: $recommendations")

                    // Menyiapkan Bundle untuk dikirim ke PlaceRecommendationFragment
                    val bundle = Bundle().apply {
                        putParcelableArray("placeRecommendations", recommendations)
                    }
                    findNavController().navigate(R.id.action_destinationInputFragment_to_placeRecommendationFragment2, bundle)
                } else {
                    Log.e("DestinationInputFragment", "Failed to send data: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "Failed: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
                Log.e("DestinationInputFragment", "Error: ${t.message}")
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
