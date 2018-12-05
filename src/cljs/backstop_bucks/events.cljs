(ns backstop-bucks.events
  (:require
   [re-frame.core :as re-frame]
   [backstop-bucks.db :as db]
   [backstop-bucks.views.user-trade :refer [user-trade]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 :trade-button-click
 (fn [db _]
   (assoc db :page user-trade)))

(re-frame/reg-event-db
 :name-change
 (fn [db [_ new-name-value]]
   (assoc db :name new-name-value)))