package com.worldline.bootstrap.presentation.ui.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.worldline.bootstrap.common.extensions.shortToast
import com.worldline.bootstrap.presentation.databinding.FragmentHelloWorldBinding
import com.worldline.bootstrap.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HelloWorldFragment : BaseFragment() {

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!! as FragmentHelloWorldBinding

    private val helloWorldViewModel: HelloWorldViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHelloWorldBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        helloWorldViewModel.helloWorldResult.observe(viewLifecycleOwner::getLifecycle) {
            if (it.helloWorldResultStatus == HelloWorldResultStatus.OK) {
                showText(it.value)
            } else {
                showError(it.value)
            }
        }

        helloWorldViewModel.navigationResult.observe(viewLifecycleOwner::getLifecycle) { event ->
            event.getContentIfNotHandled()?.let { navigationEvent ->
                when (navigationEvent) {
                    is HelloWorldNavigation.NavigateToGoodbye -> {
                        val action = HelloWorldFragmentDirections
                            .actionHelloWorldFragmentToGoodbyeWorldFragment()
                            .setUserName(navigationEvent.name)
                        view.findNavController().navigate(action)
                    }
                }
            }
        }

        binding.helloWorldButton.setOnClickListener {
            helloWorldViewModel.goodbyeButtonClicked(binding.helloWorldEditText.text)
        }
    }

    private fun showError(message: String) {
        context?.shortToast(message)
    }

    private fun showText(valueToShow: String) {
        binding.helloWorldTextView.text = valueToShow
        binding.helloWorldEditText.hint = valueToShow
    }
}
