package com.example.bloodpressure

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.one_result.view.*

/**
 * RecyclerView.ViewHolderを継承するクラス
 * RecyclerViewに表示する個々のビューを管理する
 */
class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
    var dateText:TextView? = null
    var minMaxText:TextView? = null
    var pulseText:TextView? = null
    
    //初期化処理の実施
    init{
        //ビューホルダーのプロパティとレイアウトのViewの対応
        dateText = itemView.dateText
        minMaxText = itemView.minMaxText
        pulseText = itemView.pulseText
    }
}