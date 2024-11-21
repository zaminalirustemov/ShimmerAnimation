package az.lahza.shimmereffect.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import az.lahza.shimmereffect.databinding.ShimmerItemDataBinding

class ShimmerAdapter(private val itemCount: Int) : RecyclerView.Adapter<ShimmerAdapter.ShimmerViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShimmerViewHolder {
    val view = ShimmerItemDataBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
    return ShimmerViewHolder(view.root)
  }

  override fun onBindViewHolder(holder: ShimmerViewHolder, position: Int) {
  }

  override fun getItemCount(): Int = itemCount

  inner class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}