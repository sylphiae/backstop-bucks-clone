(ns backstop-bucks.endpoints.user-endpoint
  (:require [ring.util.response :refer [response]]
            [backstop-bucks.util.database-methods :as database-methods]))


(defn get-user [{user :user id :id}_]
  (response
    (database-methods/find-by-id id)))

(defn create-user [{{user :user} :params session :session user :user :as req}]
  (let [document (assoc scorecard :owner user)
        result (search-util/create "scorecard" document)]
    (response result)))

(defn update-user [{{user :user id :id} :params session :session user :user :as req}]
  (let [document (assoc scorecard :owner user)
        result (search-util/update "scorecard" document id)]
    (response result)))

(defn delete-user [{user :user id :id}_]
  (response result))
