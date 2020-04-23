package co.cdmunoz.vp2onboarding

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import co.cdmunoz.vp2onboarding.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {

    companion object {
        private val TAG = OnBoardingActivity::class.java
    }

    private var onBoardingPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            updateCircleMarker(binding, position)
        }
    }
    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val numberOfScreens = resources.getStringArray(R.array.on_boarding_titles).size
        binding.onBoardingMainContainer.makeStatusBarTransparent()
        val onBoardingAdapter = OnBoardingAdapter(this, numberOfScreens)
        binding.onBoardingViewPager.adapter = onBoardingAdapter
        binding.onBoardingViewPager.registerOnPageChangeCallback(onBoardingPageChangeCallback)
    }

    private fun updateCircleMarker(binding: ActivityOnBoardingBinding, position: Int) {
        when (position) {
            0 -> {
                binding.onBoardingInitialCircle.background = getDrawable(R.drawable.bg_green_circle)
                binding.onBoardingMiddleCircle.background = getDrawable(R.drawable.bg_gray_circle)
                binding.onBoardingLastCircle.background = getDrawable(R.drawable.bg_gray_circle)
            }
            1 -> {
                binding.onBoardingInitialCircle.background = getDrawable(R.drawable.bg_gray_circle)
                binding.onBoardingMiddleCircle.background = getDrawable(R.drawable.bg_green_circle)
                binding.onBoardingLastCircle.background = getDrawable(R.drawable.bg_gray_circle)
            }
            2 -> {
                binding.onBoardingInitialCircle.background = getDrawable(R.drawable.bg_gray_circle)
                binding.onBoardingMiddleCircle.background = getDrawable(R.drawable.bg_gray_circle)
                binding.onBoardingLastCircle.background = getDrawable(R.drawable.bg_green_circle)
            }
        }
    }

    override fun onDestroy() {
        binding.onBoardingViewPager.unregisterOnPageChangeCallback(onBoardingPageChangeCallback)
        super.onDestroy()
    }

    private fun View.makeStatusBarTransparent() {
        this.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }
}
