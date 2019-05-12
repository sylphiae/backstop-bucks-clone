(ns backstop-bucks.events
  (:require
    [re-frame.core :as re-frame]
    [backstop-bucks.db :as db]
    [backstop-bucks.views.user-trade :refer [user-trade]]
    [backstop-bucks.views.user-home-page :refer [user-home-page]]
    [backstop-bucks.views.admin-add-page :refer [admin-add-page]]
    [backstop-bucks.views.upcoming-rewards-page :refer [upcoming-rewards-page]]
    [backstop-bucks.util :as util]
    [backstop-bucks.handlers]
    [re-frame.core :as rf]
    [ajax.core :as ajax]))

(defn- get-reward [id db]
  (prn db)
  (some #(when (= (:_id %) id) %) db))

(re-frame/reg-event-fx
 ::initialize-db
 (fn [_ _]
   {:db db/default-db
    :dispatch [:get-all-rewards-remote]}))

(re-frame/reg-event-fx
 :trade-button-click
 (fn [{:keys [db]} [_ redeemed-reward-id]]
   (let [reward (get-reward redeemed-reward-id (:all-rewards db))]
     {:db (assoc db :page user-trade),
      :dispatch [:update-rewards-remote redeemed-reward-id (assoc reward :reward-state :outgoing-trade)]})))

(re-frame/reg-event-db
 :trade-nav-click
 (fn [db _]
   (assoc db :page user-trade)))

(re-frame/reg-event-db
 :add-admin-click
 (fn [db _]
   (-> db
       (assoc :page admin-add-page)
       (assoc :is-add-admin-alert-open false)
       (assoc :is-add-new-reward-alert-open false))))

(re-frame/reg-event-db
 :home-nav-click
 (fn [db _]
   (assoc db :page user-home-page)))

(re-frame/reg-event-db
 :upcoming-rewards-click
 (fn [db _]
   (assoc db :page upcoming-rewards-page)))

(re-frame/reg-event-fx
 :redeem-button-click
 (fn [{:keys [db]} [_ redeemed-reward-id]]
   (let [reward (get-reward redeemed-reward-id (:all-rewards db))
         new-bucks (- (:bucks db) (:price reward redeemed-reward-id))]
     {:dispatch-n [[:update-rewards-remote redeemed-reward-id (assoc reward :reward-state :pending)],
                   [:update-user-remote 0 {:name "Link" :bucks new-bucks}]]})))
;need to add back end functionality so that synchronous calls can be made
;handler needed

(re-frame/reg-event-db
 :reject-button-click
 (fn [db [_ rejected-reward-id]]
   (let [reward (some #(when (= (:_id %) rejected-reward-id) %) (:all-rewards db))
         all-rewards-index (first (util/positions #{reward} (:all-rewards db)))]
     (assoc-in db [:all-rewards all-rewards-index :reward-state] :rejected))))
;handler needed

(re-frame/reg-event-db
 :remove-button-click
 (fn [db [_ rejected-reward-id]]
   (update-in db [:all-rewards] util/remove-item rejected-reward-id)
    ;(remove #(= rejected-reward-id (:_id %)) (:all-rewards db))
))
;handler needed

;this event handler is going to do more when there is an actual database and other users to interact with
(re-frame/reg-event-db
 :accept-button-click
 (fn [db [_ accepted-reward-id]]
   (let [reward  (some #(when (= (:_id %) accepted-reward-id) %) (:all-rewards db))
         all-rewards-index (first (util/positions #{reward} (:all-rewards db)))]
     (assoc-in db [:all-rewards all-rewards-index :reward-state] :redeemed))))
;handler needed

(re-frame/reg-event-db
 :bucks-input-change
 (fn [db [_ new-bucks-value]]
   (assoc db :bucks-trade-amount new-bucks-value)))

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
;handler needed

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
;handler needed

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
   (let [reward (some #(when (= (:_id %) outgoing-trade-id) %) (:all-rewards db))
         all-rewards-index (first (util/positions #{reward} (:all-rewards db)))]
     (-> db
         (assoc-in [:all-rewards all-rewards-index :tradee] (:name tradee))
         (assoc :is-select-tradee-modal-open false)))))
;handler needed

(re-frame/reg-event-db
 :grant-request-modal-button-click
 (fn [db [_ grant-request-id grantee]]
   (let [reward (some #(when (= (:_id %) grant-request-id) %) (:all-rewards db))
         all-rewards-index (first (util/positions #{reward} (:all-rewards db)))]
     (-> db
         (assoc-in [:all-rewards all-rewards-index :owner] (:name grantee))
         (assoc-in [:all-rewards all-rewards-index :reward-state] :redeemed)
         (assoc :is-grant-request-modal-open false)))))
;handler needed

;this needs to do more when there is more than one user
(re-frame/reg-event-db
 :set-bucks-click
 (fn [db [_ new-bucks selected-new-user]]
   (-> db
       (assoc :bucks new-bucks)
       (assoc :is-add-new-reward-alert-open false)
       (assoc :is-add-admin-alert-open true))))
;handler needed

(re-frame/reg-event-db
 :user-bucks-input-change
 (fn [db [_ new-user-bucks-value]]
   (assoc db :new-bucks new-user-bucks-value)))

(re-frame/reg-event-db
 :selected-new-user
 (fn [db [_ selected-new-user]]
   (assoc db :selected-new-user selected-new-user)))

(re-frame/reg-event-db
 :new-reward-bucks-input-change
 (fn [db [_ new-reward-bucks-value]]
   (assoc db :new-reward-bucks new-reward-bucks-value)))
;handler needed

(re-frame/reg-event-db
 :new-reward-name-input-change
 (fn [db [_ new-reward-name-value]]
   (assoc db :new-reward-name new-reward-name-value)))

(re-frame/reg-event-db
 :new-username-input-change
 (fn [db [_ new-username-value]]
   (assoc db :new-username new-username-value)))

(re-frame/reg-event-db
 :new-display-name-input-change
 (fn [db [_ new-display-name-value]]
   (assoc db :new-display-name new-display-name-value)))

(re-frame/reg-event-db
 :new-password-input-change
 (fn [db [_ new-password-value]]
   (assoc db :new-password new-password-value)))

(re-frame/reg-event-db
 :new-password-confirm-input-change
 (fn [db [_ new-password-confirm-value]]
   (assoc db :new-password-confirm new-password-confirm-value)))

(re-frame/reg-event-db
 :add-new-reward-click
 (fn [db [_ new-reward-bucks new-reward-name]]
   (-> db
       (assoc-in [:all-rewards (count (:all-rewards db))] {:reward-name new-reward-name
                                                           :price new-reward-bucks
                                                           :_id (count (:all-rewards db))
                                                           :reward-state :unredeemed})
       (assoc :is-add-new-reward-alert-open true)
       (assoc :is-add-admin-alert-open false))))
;handler needed

(re-frame/reg-event-db
 :register-click
 (fn [db [_ new-password new-password-confirm new-display-name new-username]]
   (-> db
       (cond->
        (= "" new-username) (assoc :registry-alert-text "You did not enter a username.")
        (not (= "" new-username)) (assoc  :is-registry-alert-text ""))
       (cond-> (= "" new-display-name)
         (assoc :new-display-name new-username))
       (cond-> (= "" new-password) (assoc :is-registry-alert-text "You did not enter a password.")
               (not (= new-password new-password-confirm)) (assoc :is-registry-alert-text "Your passwords do not match."))
       (cond-> (= new-password new-password-confirm)  (assoc :page

                                                             user-home-page)))))

;handler needed



