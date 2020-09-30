# sakila-app

[Sakila データベース](https://github.com/jOOQ/jOOQ/tree/main/jOOQ-examples/Sakila/) のためのサンプルアプリケーション。

* sakila-app-web
    - REST API で CRUD を提供する
* sakila-app-batch
    - テーブルからテーブルへの ETL を行う
* sakila-app-domain
    - テーブルエンティティ

## sakila-app-web

* [ ] `Country` エンティティの CRUD 操作が成功する
* [ ] 他のエンティティの CRUD 操作が成功する
* [ ] actuator を構成する
* [ ] logging を構成する

## sakila-app-batch

* [ ] ジョブを定義する
* [ ] logging を構成する

## sakila-app-domain

* [x] `Country` エンティティクラスを構成する
* [ ] エンティティクラスを構成する
* [ ] flyway マイグレーションスクリプトを整備する
