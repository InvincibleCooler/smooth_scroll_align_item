package me.invin.anchorposition.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.invin.anchorposition.adapter.Extra
import me.invin.anchorposition.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {
    companion object {
        private const val TAG = "MainFragment"
    }

    // view bind
    private var _binding: FragmentDetailBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inState = savedInstanceState ?: arguments

        name = inState?.getString(Extra.NAME) ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvName.text = name
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}