(ns backstop-bucks.handlers
  (:require [re-frame.core :as rf]
            [ajax.core :as ajax]
            [day8.re-frame.http-fx]))



;(rf/reg-event-fx
;  :get-user
;  (fn [_ [_ id]]
;    {:http-xhrio {:method           :get
;                  :uri              (str "/user/" id)
;                  :timeout          5000
;                  :response-format  (ajax/json-response-format)
;                  :on-success       [:]
;                  :on-failure       [:]}}))

(rf/reg-event-fx
  :get-all-rewards-remote
  (fn [_ _]
    {:http-xhrio {:method           :get
                  :uri              "/rewards"
                  :timeout          5000
                  :response-format  (ajax/json-response-format {:keywords? true})
                  :on-success       [:update-rewards]}}))

(rf/reg-event-fx
  :update-rewards-remote
  (fn [_ [_ id body]]
    {:http-xhrio {:method           :put
                  :uri              (str "/rewards/" id)
                  :timeout          5000
                  :response-format  (ajax/text-response-format)
                  :format           (ajax/json-request-format)
                  :on-success       [:get-all-rewards-remote]
                  :params           body}}))

(rf/reg-event-fx
  :get-user-remote
  (fn [_ [_ id]]
    {:http-xhrio {:method           :get
                  :uri              (str "/user/" id)
                  :timeout          5000
                  :response-format  (ajax/json-response-format {:keywords? true})
                  :on-success       [:update-user id]}}))

(rf/reg-event-fx
  :update-user-remote
  (fn [_ [_ id body]]
    {:http-xhrio {:method           :put
                  :uri              (str "/user/" id)
                  :timeout          5000
                  :response-format  (ajax/text-response-format)
                  :format           (ajax/json-request-format)
                  :on-success       [:get-all-users-remote]
                  :params           body}}))

(rf/reg-event-db
  :update-rewards
  (fn [db [_ state]]
    (let [result (mapv #(assoc % :reward-state (keyword (:reward-state %))) state)]
      (assoc db :all-rewards result))))

(rf/reg-event-db
  :update-user
  (fn [db [_ id state]]
      (assoc-in db [:users id] state)))




