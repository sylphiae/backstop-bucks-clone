(ns backstop-bucks.endpoints.user-rewards-endpoint
  (:require [ring.util.response :refer [response status]]
            [backstop-bucks.util.database-methods :as database-methods]))

(defn get-rewards [{{id :id} :params session :session :as req}]
  (response
    (database-methods/find-rewards-by-id id)))

(defn get-all-rewards [_]
  (response
    (database-methods/get-all-rewards)))

(defn create-rewards [{{id :id price :price reward-name :reward-name reward-state :reward-state} :params session :session :as req}]
  (database-methods/create-rewards id price reward-name reward-state)
  (status (response "Success") 200))

(defn update-rewards [{{id :id price :price reward-name :reward-name reward-state :reward-state} :params session :session :as req}]
  (if (empty? (database-methods/find-rewards-by-id id))
    (status (response "Not Found") 404)
    (do (database-methods/update-rewards id price reward-name reward-state)
        (status (response "Success") 200))))

(defn delete-rewards [{{id :id} :params session :session :as req}]
  (database-methods/delete-rewards id)
  (status (response "Success") 200))
