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

(re-frame/reg-event-fx
 ::initialize-db
 (fn [_ _]
   {:db db/default-db
    :dispatch [:get-all-rewards-remote]}))

(re-frame/reg-event-fx
 :trade-button-modal-click
 (fn [{:keys [db]} [_ redeemed-reward-id selected-trade-target]]
   (let [reward (util/get-reward redeemed-reward-id (:all-rewards db))
         id (:current-user-id db)
         users (:users db)
         user (util/get-current-user id users)
         new-reward (assoc reward :reward-state "outgoing-trade")]
     {:db (-> db
              (assoc :is-user-page-select-tradee-modal-open false)
              (assoc :page user-trade))
      :dispatch-n [[:update-rewards-remote redeemed-reward-id new-reward]
                  [:update-user-remote id  (assoc-in user [:trades (count (:trades user))] {:trade-reward new-reward :trade-target selected-trade-target})]]})))

(re-frame/reg-event-db
  :trade-button-click
  (fn [db [_ redeemed-reward-id]]
    (-> db
        (assoc :is-user-page-select-tradee-modal-open true)
        (assoc :select-tradee-modal-id redeemed-reward-id))))

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
   (let [reward (util/get-reward redeemed-reward-id (:all-rewards db))
         id (:current-user-id db)
         users (:users db)
         current-user (util/get-current-user id users)
         new-bucks (- (util/get-current-user-bucks id users) (:price reward redeemed-reward-id))]
     {:dispatch-n [[:update-rewards-remote redeemed-reward-id (-> reward
                                                                  (assoc :reward-state "pending")
                                                                  (assoc-in [:requesters (count (:requesters reward))] current-user))]
                   [:update-user-remote id (assoc (util/get-current-user id users) :bucks new-bucks)]]})))
;need to add back end functionality so that synchronous calls can be made

(re-frame/reg-event-fx
 :reject-button-click
 (fn [{:keys [db]} [_ rejected-reward-id]]
   (let [reward (util/get-reward rejected-reward-id (:all-rewards db))]
     {:dispatch [:update-rewards-remote rejected-reward-id (assoc reward :reward-state "rejected")]})))

(re-frame/reg-event-fx
 :remove-button-click
 (fn [{:keys [db]}  [_ rejected-reward-id]]
   ;(update-in db [:all-rewards] util/remove-item rejected-reward-id)
   {:dispatch [:delete-rewards-remote rejected-reward-id]}))

;this event handler is going to do more when there is an actual database and other users to interact with
(re-frame/reg-event-fx
 :accept-button-click
 (fn [{:keys [db]}  [_ accepted-reward-id]]
   (let [reward  (util/get-reward accepted-reward-id (:all-rewards db))]
     {:dispatch [:update-rewards-remote accepted-reward-id (assoc reward :reward-state "redeemed")]})))

(re-frame/reg-event-db
 :bucks-input-change
 (fn [db [_ new-bucks-value]]
   (assoc db :bucks-trade-amount new-bucks-value)))

(re-frame/reg-event-db
 :select-trade-target
 (fn [db [_ trade-target-value]]
   (assoc db :selected-trade-target {:name trade-target-value})))

(re-frame/reg-event-db
  :select-grant-target
  (fn [db [_ grant-target-value]]
    (assoc db :selected-grant-target  {:name grant-target-value})))

(re-frame/reg-event-fx
 :trade-request-click
 (fn [{:keys [db]}  [_ trade-value trade-target]]
   (let [id (:current-user-id db)
         users (:users db)
         user (util/get-current-user id users)]
   (if (<= trade-value (:bucks user))
        {:db (assoc db :is-bucks-alert-open false)
        :dispatch [:update-user-remote id (-> user
                                               (assoc-in [:trades (count (:trades user))] {:trade-value trade-value :trade-target trade-target})
                                               (assoc :bucks (- (:bucks user) trade-value)))]}
        {:db (assoc db :is-bucks-alert-open true)}))))

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
       (assoc :selected-grant-target (first (:requesters (util/get-reward requested-reward-id (:all-rewards db)))))
       (assoc :grant-request-modal-id requested-reward-id))))

(re-frame/reg-event-db
 :select-tradee-modal-cancel-button-click
 (fn [db _]
   (assoc db :is-select-tradee-modal-open false)))

(re-frame/reg-event-db
  :is-user-page-select-tradee-modal-cancel-button-click
  (fn [db _]
    (assoc db :is-user-page-select-tradee-modal-open false)))

(re-frame/reg-event-db
 :grant-request-modal-cancel-button-click
 (fn [db _]
   (assoc db :is-grant-request-modal-open false)))

(re-frame/reg-event-fx
 :modal-trade-button-click
 (fn [{:keys [db]} [_ outgoing-trade-id tradee]]
   (let [reward (util/get-reward outgoing-trade-id (:all-rewards db))
         users (:users db)
         user (util/get-current-user (:current-user-id db) users)
         rewards (map :trade-reward (:trades user))
         trade-index (first (util/positions #{reward} rewards))
         trade (some #(when (= (:trade-reward %) reward) %) (:trades user))]
     {:db (assoc db :is-select-tradee-modal-open false)
      :dispatch [:update-user-remote (:current-user-id db) (assoc-in user [:trades trade-index] {:trade-reward reward :trade-target tradee})]})))

(re-frame/reg-event-fx
 :grant-request-modal-button-click
 (fn [{:keys [db]} [_ grant-request-id grantee]]
   (let [reward (util/get-reward grant-request-id (:all-rewards db))]
     (prn (str "Grantee" grantee))
     {:db (assoc db :is-grant-request-modal-open false)
      :dispatch [:update-rewards-remote grant-request-id (-> reward
                                                              (assoc :reward-state "redeemed")
                                                              (assoc :owner (:name grantee)))]})))

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



