(ns backstop-bucks.util.database-methods
  (:require [monger.collection :as mc]
            [monger.core :as mg])
  (:import (com.mongodb ServerAddress MongoOptions)))

(defn find-by-id [id]
  (let [^MongoOptions opts (mg/mongo-options {:threads-allowed-to-block-for-connection-multiplier 300})
        ^ServerAddress sa (mg/server-address "127.0.0.1" 27017)
        conn (mg/connect sa opts)
        db (mg/get-db conn "monger-test")]
  (mc/find-maps db "users" {:id id})))