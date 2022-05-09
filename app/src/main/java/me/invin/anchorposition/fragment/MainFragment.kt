package me.invin.anchorposition.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import me.invin.anchorposition.adapter.MainAdapter
import me.invin.anchorposition.databinding.FragmentMainBinding
import me.invin.anchorposition.viewmodel.MainViewModel


class MainFragment : Fragment() {
    companion object {
        private const val TAG = "MainFragment"
    }

    // view bind
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mainAdapter: MainAdapter
    private val viewModel by viewModels<MainViewModel> {
        MainViewModel.Factory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainAdapter = MainAdapter(requireActivity())
        viewModel.requestData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.dataList.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty().not()) {
                mainAdapter.run {
                    items = it
                }
            }
        }

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = mainAdapter
            setHasFixedSize(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStop() {
        super.onStop()

        val recyclerView = binding.recyclerView
        val adapter = recyclerView.adapter as MainAdapter
        (0 until adapter.itemCount).forEach {
            val holder = recyclerView.findViewHolderForAdapterPosition(it)
            if (holder is MainAdapter.ItemViewHolder) {
                val key = adapter.getSectionID(holder.layoutPosition)
                adapter.scrollStates[key] = holder.binding.recyclerHorizontal.layoutManager?.onSaveInstanceState()
            }
        }
    }
}