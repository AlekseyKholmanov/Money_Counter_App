package com.example.holmi_production.money_counter_app.ui.keyboard_fragment.categoryPickerWithCreate.create_category_dialog

import android.content.Context
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.holmi_production.money_counter_app.R

class ImagePickerAdapter(private val imageIds: TypedArray, val context:Context) : RecyclerView.Adapter<ImagePickerAdapter.ImageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {

        val inflater = LayoutInflater.from(parent.context)
        val item = inflater.inflate(R.layout.category_picker_item, parent, false)
        return ImageHolder(item)
    }

    override fun getItemCount(): Int = imageIds.length()

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind(imageIds.getResourceId(position,R.drawable.ic_launcher_foreground))
    }

    inner class ImageHolder(
        v: View): RecyclerView.ViewHolder(v){
        private val image:ImageView = v.findViewById(R.id.image_category_dialog)
        private val tv: TextView = v.findViewById(R.id.text_category_dialog)


        fun bind(imageId:Int){
            image.setImageDrawable(context.getDrawable(imageId))
            tv.visibility = View.GONE
        }
    }
}