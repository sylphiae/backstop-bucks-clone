(ns backstop-bucks.endpoints.user-endpoint
  (:require [ring.util.response :refer [response status]]
            [backstop-bucks.util.database-methods :as database-methods]))

(def fake-db (atom {"0" "Link" "1" "Metaknight"}))

(defn get-user [{{id :id} :params session :session :as req}]
  (response {:user (get @fake-db id)}))

;(defn create-user [param]
(defn create-user [{{id :id name :name} :params session :session user :user :as req}]
  (swap! fake-db assoc id name)
  (status (response "Success") 200))

;
;(defn update-user [{{user :user id :id} :params session :session user :user :as req}]
;  (let [document (assoc scorecard :owner user)
;        result (search-util/update "scorecard" document id)]
;    (response result)))
;
;(defn delete-user [{user :user id :id}_]
;  (response result))
