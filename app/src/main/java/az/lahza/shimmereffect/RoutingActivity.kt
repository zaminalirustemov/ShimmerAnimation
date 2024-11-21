package az.lahza.shimmereffect

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import az.lahza.shimmereffect.databinding.ActivityRoutingBinding

class RoutingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoutingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityRoutingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnMain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnList.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }
    }
}