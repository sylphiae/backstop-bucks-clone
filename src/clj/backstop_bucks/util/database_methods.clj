(ns backstop-bucks.util.database-methods
  (:require [monger.collection :as mc]
            [monger.core :as mg])
  (:import (com.mongodb ServerAddress MongoOptions)))

(defn get-mongo-connection []
  (let [^MongoOptions opts (mg/mongo-options {:threads-allowed-to-block-for-connection-multiplier 300})
        ^ServerAddress sa (mg/server-address "127.0.0.1" 27017)
        conn (mg/connect sa opts)]
    (mg/get-db conn "test")))

(defn find-by-id [id]
  (let [db (get-mongo-connection)]
    (mc/find-maps db "users" {:_id id})))

(defn create-user [id name bucks]
  (let [db (get-mongo-connection)]
    (mc/insert db "users" {:_id id :name name :bucks bucks})))

(defn update-user [id name bucks]
  (let [db (get-mongo-connection)]
    (mc/update-by-id db "users" id {:_id id :name name :bucks bucks})))

(defn delete-user [id]
  (let [db (get-mongo-connection)]
    (mc/remove-by-id db "users" id)))

(defn find-rewards-by-id [id]
  (let [db (get-mongo-connection)]
    (mc/find-maps db "rewards" {:_id id})))

(defn get-all-rewards []
  (let [db (get-mongo-connection)]
    (mc/find-maps db "rewards")))

(defn create-rewards [id price reward-name reward-state]
  (let [db (get-mongo-connection)]
    (mc/insert db "rewards" {:_id id :price price :reward-name reward-name :reward-state reward-state})))

(defn update-rewards [id price reward-name reward-state]
  (let [db (get-mongo-connection)]
    (mc/update-by-id db "rewards" id {:_id id :price price :reward-name reward-name :reward-state reward-state})))

(defn delete-rewards [id]
  (let [db (get-mongo-connection)]
    (mc/remove-by-id db "rewards" id)))