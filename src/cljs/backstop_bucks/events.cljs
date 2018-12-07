(ns backstop-bucks.events
  (:require
    [re-frame.core :as re-frame]
    [backstop-bucks.db :as db]
    [backstop-bucks.views.user-trade :refer [user-trade]]
    [backstop-bucks.util :as util]))

(re-frame/reg-event-db
  ::initialize-db
  (fn [_ _]
      db/default-db))

(re-frame/reg-event-db
  :trade-button-click
  (fn [db _]
      (assoc db :page user-trade)))

(re-frame/reg-event-db
  :redeem-button-click
  (fn [db [_ redeemed-reward-index]]
      (-> db
          (assoc-in [:redeemed-rewards (count (:redeemed-rewards db))] (nth (:unredeemed-rewards db) redeemed-reward-index))
          (update-in [:unredeemed-rewards] util/remove-index redeemed-reward-index))))

(re-frame/reg-event-db
  :reject-button-click
  (fn [db [_ rejected-reward-index]]
      (update-in db [:unredeemed-rewards] util/remove-index rejected-reward-index)))