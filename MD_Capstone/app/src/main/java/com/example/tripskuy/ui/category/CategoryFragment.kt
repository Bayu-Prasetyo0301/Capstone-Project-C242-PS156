package com.example.tripskuy.ui.category

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripskuy.adapter.RecommendationAdapter
import com.example.tripskuy.data.remote.response.PlaceRecommendation
import com.example.tripskuy.databinding.FragmentCategoryBinding
import com.example.tripskuy.ui.detail.DetailActivity

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private val args: CategoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = args.category
        val recommendations: Array<PlaceRecommendation> = args.recommendations

        binding.tvCategoryTitle.text = category

        val recommendationAdapter = RecommendationAdapter(recommendations.toList()) { item ->
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("PLACE_DATA", item)
            }
            startActivity(intent)
        }

        binding.rvCategory.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = recommendationAdapter
            addItemDecoration(SpaceItemDecoration(16))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class SpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            val spanCount = 2
            val column = position % spanCount

            outRect.top = space
            outRect.bottom = space
            outRect.left = space - column * space / spanCount
            outRect.right = (column + 1) * space / spanCount
        }
    }
}

