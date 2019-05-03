(ns backstop-bucks.endpoints.user-endpoint
  (:require [ring.util.response :refer [response status]]
            [backstop-bucks.util.database-methods :as database-methods]))

(defn get-user [{{id :id} :params session :session :as req}]
  (response
    (database-methods/find-by-id id)))

;(defn create-user [{{id :id name :name} :params session :session user :user :as req}]
;  (swap! fake-db assoc id name)
;  (response @fake-db))
  ;(status (response "Success") 200))

(defn create-user [{{id :id name :name} :params session :session user :user :as req}]
  (database-methods/create-user id name)
  (status (response "Success") 200))

(defn update-user [{{id :id name :name} :params session :session user :user :as req}]
  (if (empty? (database-methods/find-by-id id))
    (status (response "Not Found") 404)
    (do (database-methods/update-user id name)
        (status (response "Success") 200))))

;(defn update-user [{{id :id name :name} :params session :session user :user :as req}]
;  (if (nil? (:user (get @fake-db id)))
;    (status (response "Not Found") 404)
;    (do (swap! fake-db assoc id name)
;        (response @fake-db))))

;(defn update-user [{{user :user id :id} :params session :session user :user :as req}]
;  (let [document (assoc scorecard :owner user)
;        result (search-util/update "scorecard" document id)]
;    (response result)))

(defn delete-user [{{id :id} :params session :session :as req}]
  (database-methods/delete-user id)
  (status (response "Success") 200))
