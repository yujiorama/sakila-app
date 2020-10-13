# sakila-app

[Sakila データベース](https://github.com/jOOQ/jOOQ/tree/main/jOOQ-examples/Sakila/) のためのサンプルアプリケーション。

* sakila-app-web
    - REST API で CRUD を提供する
* sakila-app-batch
    - テーブルからテーブルへの ETL を行う
* sakila-app-domain
    - テーブルエンティティ

## sakila-app-web

* [x] テスト用の flyway マイグレーションスクリプトを整備する
* [x] `Country` エンティティの CRUD 操作が成功する
* [x] 他のエンティティの CRUD 操作が成功する
* [x] `enum class` のためのマッパーを構成する
* [x] `ByteArray` のためのマッパーを構成する
* [x] actuator を構成する
* [x] logging を構成する

## sakila-app-batch

* [ ] ジョブを定義する
* [ ] logging を構成する

## sakila-app-domain

* [x] `Country` エンティティクラスを構成する
* [x] エンティティクラスを構成する
* [x] `enum class` のためのマッパーを構成する
* [x] `ByteArray` のためのマッパーを構成する

## ライセンス

[MIT](./LICENSE)

