package com.example.bloodpressure

import android.content.Intent
import android.graphics.Color
import android.icu.text.MessageFormat.format
import android.text.format.DateFormat
import android.text.format.DateFormat.format
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults

/**
 * recyclerView.Adapterクラスを継承する
 * Realmのクエリの実行結果であるRealmResultsを受け取る
 * RecyclerVIewに表示するデータソースを管理する
 */
class CustomRecyclerViewAdapter(realmResults: RealmResults<BloodPress>):RecyclerView.Adapter<ViewHolder>() {
    private val rResults:RealmResults<BloodPress> = realmResults //データセット

    /**
     * レイアウトマネージャー(RecyclerViewのレイアウトを決める)によって起動され、新しいViewを作成する
     * @param parent
     * @param position
     * @return ViewHolder RecyclerViewに表示する個々のviewを管理する
     */
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        //parentにRecyclerViewを指定して、one_resultを作成する。
        val view = LayoutInflater.from(parent.context).inflate(R.layout.one_result,parent,false)
        return ViewHolder(view) //ViewHolderオブジェクトを生成して返す
    }

    /**
     * レイアウトマネージャーによって起動される。個々のviewに値をセットする
     * @param holder
     * @param position RecyclerView上のポジション　＝　RealmResultsの並び
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bloodPress = rResults[position] //bloodPressオブジェクトを取得する。
        //holderの保持するone_result上の各textプロパティを設定する
        holder.dateText?.text = DateFormat.format("yyyy/MM/dd kk:mm",bloodPress?.dateTime) //dateTimeをformatに設定する
        holder.minMaxText?.text = "${bloodPress?.max.toString()}/${bloodPress?.min.toString()}"
        holder.pulseText?.text = bloodPress?.pulse.toString()

        holder.itemView.setBackgroundColor(if(position % 2 == 0) Color.LTGRAY else Color.WHITE)

        //RecyclerViewの1レコードを表示しているViewがタップされたら、Intentを発行して、EditActivityに画面を遷移するコードを追加する
        //10th commit
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context,EditActivity::class.java)
            intent.putExtra("id",bloodPress?.id)
            it.context.startActivity(intent)
        }
    }

    /**
     * @return Int データセット(rResults)のサイズを返す
     */
    override fun getItemCount(): Int {
        return rResults.size
    }

}