package com.example.holmi_production.money_counter_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment

class CostsFragment : AndroidXMvpAppCompatFragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.costst_fragment,container,false)
    }
}