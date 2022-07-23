package com.worldline.bootstrap.presentation.ui.goodbyeworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.worldline.bootstrap.presentation.R
import com.worldline.bootstrap.presentation.databinding.FragmentGoodbyeWorldBinding
import com.worldline.bootstrap.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GoodbyeWorldFragment : BaseFragment() {

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!! as FragmentGoodbyeWorldBinding
    private val args: GoodbyeWorldFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoodbyeWorldBinding.inflate(inflater, container, false)

        val username = args.userName
        binding.helloWorldTextView.text = getString(R.string.goodbye_world) + " " + username

        return binding.root
    }
}
