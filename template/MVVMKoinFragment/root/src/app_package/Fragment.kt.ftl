package ${featureOut};

import android.os.Bundle
import android.view.View
import com.cashless.self_order.R
import com.cashless.self_order.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * --------------------
 * Screen name:
 * --------------------
 */
 
class ${capFeatureName}Fragment : BaseFragment<${capFeatureName}ViewModel>() {

	override val viewModelx: ${capFeatureName}ViewModel by viewModel()
	
	override val layoutID: Int
        get() = R.layout.fragment_${featureName?lower_case}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initialize() {
    }

    override fun onSubscribeObserver() {
    }

    companion object {
        private const val TAG = "${capFeatureName}Fragment"

    }
	
}
