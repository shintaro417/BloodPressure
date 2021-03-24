package com.example.bloodpressure

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Realmを初期化するタイミングはアプリ起動時に実行する必要あるので、Applicationクラスを継承する
 * onCreateメソッドがアプリ起動時に実行されるようにするためにマニフェストファイルのapplicationタグに追記が必要
 */
class CustomApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this) //Realmライブラリの初期化
        val config = RealmConfiguration.Builder().build() //コンフィグレーションを作成する
        Realm.setDefaultConfiguration(config) //デフォルト設定するs
    }
}