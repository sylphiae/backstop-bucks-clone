(ns backstop-bucks.util.database-methods
  (:require [monger.collection :as mc]
            [monger.core :as mg])
  (:import (com.mongodb ServerAddress MongoOptions)))

(defn find-by-id [id]
  (let [^MongoOptions opts (mg/mongo-options {:threads-allowed-to-block-for-connection-multiplier 300})
        ^ServerAddress sa (mg/server-address "127.0.0.1" 27017)
        conn (mg/connect sa opts)
        db (mg/get-db conn "test")]
    (mc/find-maps db "users" {:_id id})))

(defn create-user [id name]
  (let [^MongoOptions opts (mg/mongo-options {:threads-allowed-to-block-for-connection-multiplier 300})
        ^ServerAddress sa (mg/server-address "127.0.0.1" 27017)
        conn (mg/connect sa opts)
        db (mg/get-db conn "test")]
    (mc/insert db "users" {:_id id :name name})))

(defn update-user [id name]
  (let [^MongoOptions opts (mg/mongo-options {:threads-allowed-to-block-for-connection-multiplier 300})
        ^ServerAddress sa (mg/server-address "127.0.0.1" 27017)
        conn (mg/connect sa opts)
        db (mg/get-db conn "test")]
    (mc/update-by-id db "users" id {:_id id :name name})))

(defn delete-user [id]
  (let [^MongoOptions opts (mg/mongo-options {:threads-allowed-to-block-for-connection-multiplier 300})
        ^ServerAddress sa (mg/server-address "127.0.0.1" 27017)
        conn (mg/connect sa opts)
        db (mg/get-db conn "test")]
    (mc/remove-by-id db "users" id)))

(defn find-rewards-by-id [id]
  (let [^MongoOptions opts (mg/mongo-options {:threads-allowed-to-block-for-connection-multiplier 300})
        ^ServerAddress sa (mg/server-address "127.0.0.1" 27017)
        conn (mg/connect sa opts)
        db (mg/get-db conn "test")]
    (mc/find-maps db "rewards" {:_id id})))

(defn create-rewards [id price reward-name reward-state]
  (let [^MongoOptions opts (mg/mongo-options {:threads-allowed-to-block-for-connection-multiplier 300})
        ^ServerAddress sa (mg/server-address "127.0.0.1" 27017)
        conn (mg/connect sa opts)
        db (mg/get-db conn "test")]
    (mc/insert db "rewards" {:_id id :price price :reward-name reward-name :reward-state reward-state})))

(defn update-rewards [id price reward-name reward-state]
  (let [^MongoOptions opts (mg/mongo-options {:threads-allowed-to-block-for-connection-multiplier 300})
        ^ServerAddress sa (mg/server-address "127.0.0.1" 27017)
        conn (mg/connect sa opts)
        db (mg/get-db conn "test")]
    (mc/update-by-id db "rewards" id {:_id id :price price :reward-name reward-name :reward-state reward-state})))

(defn delete-rewards [id]
  (let [^MongoOptions opts (mg/mongo-options {:threads-allowed-to-block-for-connection-multiplier 300})
        ^ServerAddress sa (mg/server-address "127.0.0.1" 27017)
        conn (mg/connect sa opts)
        db (mg/get-db conn "test")]
    (mc/remove-by-id db "rewards" id)))