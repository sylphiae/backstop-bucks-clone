(ns backstop-bucks.subs
  (:require
   [re-frame.core :as re-frame]
   [backstop-bucks.util :as util]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::bucks
 (fn [db]
   (:bucks db)))

(re-frame/reg-sub
  ::new-bucks
  (fn [db]
    (:new-bucks db)))

(re-frame/reg-sub
  ::users
  (fn [db]
    (:users db)))

(re-frame/reg-sub
  ::tiers
  (fn [db]
    (:tiers db)))

(re-frame/reg-sub
  ::bucks-trade-amount
  (fn [db]
      (:bucks-trade-amount db)))

(re-frame/reg-sub
  ::all-rewards
  (fn [db]
   (:all-rewards db)))

(re-frame/reg-sub
  ::trade-targets
  (fn [db]
    (:users db)))

(re-frame/reg-sub
  ::trade-requests
  (fn [db]
    (:trade-requests db)))

(re-frame/reg-sub
  ::page-view
  (fn [db]
      (:page db)))

(re-frame/reg-sub
  ::selected-trade-target-primary
  (fn [db]
    (:selected-trade-target db)))

(re-frame/reg-sub
  ::selected-grant-target
  (fn [db]
    (:selected-grant-target db)))

(re-frame/reg-sub
  ::selected-new-user
  (fn [db]
    (:selected-new-user db)))

(re-frame/reg-sub
  ::is-select-tradee-modal-open
  (fn [db]
    (:is-select-tradee-modal-open db)))

(re-frame/reg-sub
  ::is-grant-request-modal-open
  (fn [db]
    (:is-grant-request-modal-open db)))

(re-frame/reg-sub
  ::is-bucks-alert-open
  (fn [db]
    (:is-bucks-alert-open db)))

(re-frame/reg-sub
  ::is-add-admin-alert-open
  (fn [db]
    (:is-add-admin-alert-open db)))

(re-frame/reg-sub
  ::select-tradee-modal-id
  (fn [db]
    (:select-tradee-modal-id db)))

(re-frame/reg-sub
  ::grant-request-modal-id
  (fn [db]
    (:grant-request-modal-id db)))
;; --------------------------------------------------------

(re-frame/reg-sub
  ::admin-rewards
  :<- [::all-rewards]
  (fn [all-rewards]
    (remove (some-fn #(= :redeemed (:reward-state %)) #(= :pending-trade (:reward-state %)) #(= :outgoing-trade (:reward-state %))) all-rewards)))

(re-frame/reg-sub
  ::unrequested-rewards
  :<- [::admin-rewards]
  (fn [admin-rewards]
    (remove #(= :pending (:reward-state %)) admin-rewards)))

(re-frame/reg-sub
  ::redeemed-rewards
  :<- [::all-rewards]
  (fn [all-rewards]
    (filter #(= :redeemed (:reward-state %)) all-rewards)))

(re-frame/reg-sub
  ::pending-rewards
  :<- [::all-rewards]
  (fn [all-rewards]
    (filter #(= :pending (:reward-state %)) all-rewards)))

(re-frame/reg-sub
  ::unredeemed-rewards
  :<- [::all-rewards]
  (fn [all-rewards]
    (filter #(= :unredeemed (:reward-state %)) all-rewards)))

(re-frame/reg-sub
 ::redeemed-rewards-count
 :<- [::redeemed-rewards]

 (fn [redeemed-rewards]
   (count redeemed-rewards)))

(re-frame/reg-sub
  ::pending-trades
  :<- [::all-rewards]
  (fn [all-rewards]
    (filter #(= :pending-trade (:reward-state %)) all-rewards)))

(re-frame/reg-sub
  ::outgoing-trades
  :<- [::all-rewards]
  (fn [all-rewards]
    (filter #(= :outgoing-trade (:reward-state %)) all-rewards)))

(re-frame/reg-sub
  ::pending-trades-count
  :<- [::pending-trades]

  (fn [pending-trades]
    (count pending-trades)))

(re-frame/reg-sub
  ::outgoing-trades-count
  :<- [::outgoing-trades]

  (fn [outgoing-trades]
    (count outgoing-trades)))

(re-frame/reg-sub
  ::tier
  :<- [::bucks]
  :<- [::tiers]
  (fn [[bucks tiers] _]
    (some #(when (= (second (second % )) (util/get-tier-end-value bucks tiers)) %) tiers)))

(re-frame/reg-sub
  ::next-tier
  :<- [::tier]
  :<- [::tiers]
  (fn [[tier tiers] _]
    (let [next-tier (inc (js/parseInt (name (first tier))))]
      (first (hash-map next-tier (get tiers (keyword (str next-tier))))))))

(re-frame/reg-sub
  ::upcoming-rewards
  :<- [::all-rewards]
  :<- [::next-tier]
  (fn [[unredeemed-rewards next-tier] _]
    (filter #(< (first (second next-tier)) (:price %) (second (second next-tier))) unredeemed-rewards)))

(re-frame/reg-sub
  ::upcoming-rewards-count
  :<- [::upcoming-rewards]
  (fn [upcoming-rewards]
    (count upcoming-rewards)))

(re-frame/reg-sub
  ::pending-rewards-count
  :<- [::pending-rewards]
  (fn [pending-rewards]
    (count pending-rewards)))

(re-frame/reg-sub
  ::unredeemed-rewards-affordable
  :<- [::unredeemed-rewards]
  :<- [::bucks]
  (fn [[unredeemed-rewards bucks] _]
    (filter #(< (:price %) bucks) unredeemed-rewards)))

(re-frame/reg-sub
  ::grant-request-item
  :<- [::all-rewards]
  :<- [::grant-request-modal-id]
  (fn [[all-rewards grant-request-modal-id] _]
    (:reward-name (some #(when (= (:reward-id %) grant-request-modal-id) %) all-rewards))))


(re-frame/reg-sub
  ::unredeemed-rewards-count
  :<- [::unredeemed-rewards-affordable]

  (fn [unredeemed-rewards-affordable]
    (count unredeemed-rewards-affordable)))

(re-frame/reg-sub
  ::selected-trade-target
  :<- [::trade-targets]
  :<- [::name]
  :<- [::selected-trade-target-primary]
    (fn [[trade-targets user-name selected-trade-target-primary] _]
      (if (nil? selected-trade-target-primary)
        (first (remove #(= user-name (:name %)) trade-targets))
        selected-trade-target-primary)))

(re-frame/reg-sub
  ::grant-targets
  :<- [::all-rewards]
  :<- [::grant-request-modal-id]
  (fn [[all-rewards grant-request-modal-id] _]
    ;(print grant-request-modal-id)
    (:requesters (some #(when (= grant-request-modal-id (:reward-id %)) %) all-rewards))))



