(ns backstop-bucks.core
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import (com.mongodb MongoOptions ServerAddress)))

;; using MongoOptions allows fine-tuning connection parameters,
;; like automatic reconnection (highly recommended for production environment)
(defn -main [& args]
  (let [^MongoOptions opts (mg/mongo-options {:threads-allowed-to-block-for-connection-multiplier 300})
        ^ServerAddress sa (mg/server-address "127.0.0.1" 27017)
        conn (mg/connect sa opts)
        db (mg/get-db conn "monger-test")]

    ;(mc/insert-batch db "documents" [{:_id 1 :first_name "John" :last_name "Lennon"}
    ;                                 {:_id 2 :first_name "Paul" :last_name "McCartney"}])
    (print (mc/find-maps db "documents" {:first_name "John"}))))

(comment "List of endpoints to have:
POST request for adding users
PUT request for updating users
DELETE request for deleting users
GET request for retrieving users
POST request for adding rewards
PUT request for updating rewards
DELETE request for deleting rewards
GET request for retrieving rewards
POST request for adding Backstop Bucks
PUT request for updating Backstop Bucks
DELETE request for deleting Backstop Bucks
GET request for retrieving Backstop Bucks")