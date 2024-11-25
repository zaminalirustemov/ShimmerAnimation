package az.lahza.shimmereffect.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import az.lahza.shimmereffect.databinding.ItemDataBinding

class DataAdapter(private val dataList: List<String>) :
    RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    inner class DataViewHolder(private var binding: ItemDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataList: String) {
            binding.ivPhoto.setImageDrawable(getRandomDrawable())
            binding.tvFirst.text = "Name $dataList"
            binding.tvSecond.text = "Date $dataList"
            binding.tvThird.text = "Phone $dataList"
        }
    }

    private fun getRandomDrawable(): Drawable {
        return listOf(
            ColorDrawable(Color.parseColor("#740938")),
            ColorDrawable(Color.parseColor("#AF1740")),
            ColorDrawable(Color.parseColor("#CC2B52")),
            ColorDrawable(Color.parseColor("#DE7C7D")),
            ColorDrawable(Color.parseColor("#A64D79"))
        ).random()
    }
}