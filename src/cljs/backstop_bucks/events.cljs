(ns backstop-bucks.events
  (:require
    [re-frame.core :as re-frame]
    [backstop-bucks.db :as db]
    [backstop-bucks.views.user-trade :refer [user-trade]]
    [backstop-bucks.views.user-home-page :refer [user-home-page]]
    [backstop-bucks.views.admin-add-page :refer [admin-add-page]]
    [backstop-bucks.views.upcoming-rewards-page :refer [upcoming-rewards-page]]
    [backstop-bucks.util :as util]))

(re-frame/reg-event-db
  ::initialize-db
  (fn [_ _]
      db/default-db))

(re-frame/reg-event-db
  :trade-button-click
  (fn [db [_ redeemed-reward-id]]
    (let [reward (some #(when (= (:reward-id %) redeemed-reward-id) %) (:all-rewards db))
          all-rewards-index (first (util/positions #{reward} (:all-rewards db)))]
      (-> db
        (assoc-in [:all-rewards all-rewards-index :reward-state ] :outgoing-trade)
        (assoc :page user-trade)))))

(re-frame/reg-event-db
  :trade-nav-click
  (fn [db _]
    (assoc db :page user-trade)))

(re-frame/reg-event-db
  :add-admin-click
  (fn [db _]
    (assoc db :page admin-add-page)))

(re-frame/reg-event-db
  :home-nav-click
  (fn [db _]
    (assoc db :page user-home-page)))

(re-frame/reg-event-db
  :upcoming-rewards-click
  (fn [db _]
    (assoc db :page upcoming-rewards-page)))

(re-frame/reg-event-db
  :redeem-button-click
  (fn [db [_ redeemed-reward-id]]
    (let [reward (some #(when (= (:reward-id %) redeemed-reward-id) %) (:all-rewards db))
          all-rewards-index (first (util/positions #{reward} (:all-rewards db)))
          new-bucks (- (:bucks db) (:price reward redeemed-reward-id))]
      (-> db
          (assoc :bucks new-bucks)
          (assoc-in [:all-rewards all-rewards-index :reward-state ] :pending)))))

(re-frame/reg-event-db
  :reject-button-click
  (fn [db [_ rejected-reward-id]]
    (let [reward (some #(when (= (:reward-id %) rejected-reward-id) %) (:all-rewards db))
          all-rewards-index (first (util/positions #{reward} (:all-rewards db)))]
      (assoc-in db [:all-rewards all-rewards-index :reward-state] :rejected))))

(re-frame/reg-event-db
  :remove-button-click
  (fn [db [_ rejected-reward-id]]
    (update-in db [:all-rewards] util/remove-item rejected-reward-id)
    ;(remove #(= rejected-reward-id (:reward-id %)) (:all-rewards db))
    ))

;this event handler is going to do more when there is an actual database and other users to interact with
(re-frame/reg-event-db
  :accept-button-click
  (fn [db [_ accepted-reward-id]]
    (let [reward  (some #(when (= (:reward-id %) accepted-reward-id) %) (:all-rewards db))
          all-rewards-index (first (util/positions #{reward} (:all-rewards db)))]
      (assoc-in db [:all-rewards all-rewards-index :reward-state] :redeemed))))

(re-frame/reg-event-db
  :bucks-input-change
  (fn [db [_ new-bucks-value]]
      (assoc db :bucks-trade-amount new-bucks-value))
  )

(re-frame/reg-event-db
  :select-trade-target
  (fn [db [_ trade-target-value]]
    (assoc db :selected-trade-target {:name trade-target-value})))

(re-frame/reg-event-db
  :trade-request-click
  (fn [db [_ trade-value trade-target]]
    (if (<= trade-value (:bucks db))
      (-> db
        (assoc :is-bucks-alert-open false)
        (assoc-in [:trade-requests (count (:trade-requests db))] {:trade-value trade-value :trade-target trade-target})
        (assoc :bucks-trade-amount 0)
        (assoc :bucks (- (:bucks db) trade-value)))
      (assoc db :is-bucks-alert-open true))))

(re-frame/reg-event-db
  :select-tradee-click
  (fn [db [_ outgoing-trade-id]]
    (-> db
        (assoc :is-select-tradee-modal-open true)
        (assoc :select-tradee-modal-id outgoing-trade-id))))

(re-frame/reg-event-db
  :grant-button-click
  (fn [db [_ requested-reward-id]]
    (-> db
        (assoc :is-grant-request-modal-open true)
        (assoc :grant-request-modal-id requested-reward-id))))

(re-frame/reg-event-db
  :select-tradee-modal-cancel-button-click
  (fn [db _]
    (assoc db :is-select-tradee-modal-open false)))

(re-frame/reg-event-db
  :grant-request-modal-cancel-button-click
  (fn [db _]
    (assoc db :is-grant-request-modal-open false)))

(re-frame/reg-event-db
  :modal-trade-button-click
  (fn [db [_ outgoing-trade-id tradee]]
    (let [reward (some #(when (= (:reward-id %) outgoing-trade-id) %) (:all-rewards db))
          all-rewards-index (first (util/positions #{reward} (:all-rewards db)))]
    (-> db
        (assoc-in [:all-rewards all-rewards-index :tradee] (:name tradee))
        (assoc :is-select-tradee-modal-open false)))))

(re-frame/reg-event-db
  :grant-request-modal-button-click
  (fn [db [_ grant-request-id grantee]]
    (let [reward (some #(when (= (:reward-id %) grant-request-id) %) (:all-rewards db))
          all-rewards-index (first (util/positions #{reward} (:all-rewards db)))]
      (-> db
          (assoc-in [:all-rewards all-rewards-index :owner] (:name grantee))
          (assoc-in [:all-rewards all-rewards-index :reward-state] :redeemed)
          (assoc :is-grant-request-modal-open false)))))

