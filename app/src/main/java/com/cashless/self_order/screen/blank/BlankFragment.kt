package com.cashless.self_order.screen.blank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cashless.self_order.R

class BlankFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_blank, container, false)
  }

  companion object {
    fun newInstance() = BlankFragment()
    const val TAG = "BlankFragment"
  }


}