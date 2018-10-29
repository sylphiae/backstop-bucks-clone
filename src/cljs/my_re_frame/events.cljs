(ns my-re-frame.events
  (:require
   [re-frame.core :as re-frame]
   [my-re-frame.db :as db]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
:name-color-change
(fn [db [_ new-color-value]]
  (assoc db :color new-color-value)))

(re-frame/reg-event-db
  :name-change
  (fn [db [_ new-name-value]]
    (assoc db :name new-name-value))
  )