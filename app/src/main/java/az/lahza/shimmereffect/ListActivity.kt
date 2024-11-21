package az.lahza.shimmereffect

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import az.lahza.shimmereffect.adapter.DataAdapter
import az.lahza.shimmereffect.adapter.ShimmerAdapter
import az.lahza.shimmereffect.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding

    private lateinit var dataAdapter: DataAdapter
    private lateinit var shimmerAdapter: ShimmerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.rvList.layoutManager = LinearLayoutManager(this)
        showShimmer()
        showActualData()
    }

    private fun showShimmer() {
        shimmerAdapter = ShimmerAdapter(10) // Show 10 shimmer items as a placeholder
        binding.rvList.adapter = shimmerAdapter
    }

    private fun showActualData() {
        // Simulate data loading with a delay
        val data = List(20) { "Item ${it + 1}" } // Generating dummy data
        Handler(Looper.getMainLooper()).postDelayed({
            dataAdapter = DataAdapter(data)
            binding.rvList.adapter = dataAdapter // Replace shimmer adapter with the data adapter
        }, DELAY_TIME)
    }

    companion object {
        const val DELAY_TIME = 3000L // 3 seconds
    }
}