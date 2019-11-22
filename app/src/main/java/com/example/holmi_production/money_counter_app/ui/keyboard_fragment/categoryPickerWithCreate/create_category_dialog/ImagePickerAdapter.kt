package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog

import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.model.SquareImageView

class ImagePickerAdapter(private val imageIds: TypedArray, val callback: IImagePicker?) : RecyclerView.Adapter<ImagePickerAdapter.ImageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {

        val inflater = LayoutInflater.from(parent.context)
        val item = inflater.inflate(R.layout.category_picker_item, parent, false)
        return ImageHolder(item)
    }

    override fun getItemCount(): Int = imageIds.length()

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val imageId = imageIds.getResourceId(position,R.drawable.ic_launcher_foreground)
        holder.bind(imageId)
    }

    inner class ImageHolder(
        v: View): RecyclerView.ViewHolder(v){
        private val image:SquareImageView = v.findViewById(R.id.image_category_dialog)
        private val tv: TextView = v.findViewById(R.id.text_category_dialog)


        fun bind(imageId:Int){
            image.setOnClickListener {
                callback!!.imagePicked(imageId)
            }
            image.visibility = View.VISIBLE
            image.setImageResource(imageId)
            tv.visibility = View.GONE
        }
    }
}