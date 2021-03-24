package com.example.bloodpressure

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Realmのモデルクラス
 * RealmObjectを継承する
 * open修飾子を付与する。→デフォルトでは継承禁止。継承を可能にするアクセス修飾子
 */
open class BloodPress:RealmObject() {
    //Realm内部では整数型はすべてLong型として扱われる
    @PrimaryKey
    var id:Long = 0
    var dateTime:Date = Date() //登録時の日時
    var max:Long = 0 //最高血圧
    var min:Long = 0 //最低血圧
    var pulse:Long = 0 //脈拍
}