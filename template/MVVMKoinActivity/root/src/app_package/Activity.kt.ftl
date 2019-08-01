package ${featureOut};

import com.cashless.self_order.R
import com.cashless.self_order.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * --------------------
 * Screen name:
 * --------------------
 */

class ${capFeatureName}Activity : BaseActivity<${capFeatureName}ViewModel>() {

    override val viewModelx: ${capFeatureName}ViewModel by viewModel()
	
	override val layoutID: Int
        get() = R.layout.activity_${featureName?lower_case}

    override fun initialize() {
    }
	
	override fun onSubscribeObserver() {
    }
}
