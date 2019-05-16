(ns backstop-bucks.subs
  (:require
   [re-frame.core :as re-frame]
   [backstop-bucks.util :as util]))

(re-frame/reg-sub
  ::current-user-id
  (fn [db]
    (:current-user-id db)))

(re-frame/reg-sub
 ::new-bucks
 (fn [db]
   (:new-bucks db)))

(re-frame/reg-sub
 ::new-reward-name
 (fn [db]
   (:new-reward-name db)))

(re-frame/reg-sub
 ::new-username
 (fn [db]
   (:new-username db)))

(re-frame/reg-sub
 ::new-password
 (fn [db]
   (:new-password db)))

(re-frame/reg-sub
 ::new-password-confirm
 (fn [db]
   (:new-password-confirm db)))

(re-frame/reg-sub
 ::new-display-name
 (fn [db]
   (:new-display-name db)))

(re-frame/reg-sub
 ::new-reward-bucks
 (fn [db]
   (:new-reward-bucks db)))

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
 ::registry-alert-text
 (fn [db]
   (:registry-alert-text db)))

(re-frame/reg-sub
 ::is-select-tradee-modal-open
 (fn [db]
   (:is-select-tradee-modal-open db)))

(re-frame/reg-sub
  ::is-user-page-select-tradee-modal-open
  (fn [db]
    (:is-user-page-select-tradee-modal-open db)))

(re-frame/reg-sub
 ::is-grant-request-modal-open
 (fn [db]
   (:is-grant-request-modal-open db)))

(re-frame/reg-sub
 ::is-bucks-alert-open
 (fn [db]
   (:is-bucks-alert-open db)))

(re-frame/reg-sub
 ::is-add-new-reward-alert-open
 (fn [db]
   (:is-add-new-reward-alert-open db)))

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
  ::selected-trade-reward
  :<- [::select-tradee-modal-id]
  :<- [::all-rewards]
  (fn [[select-tradee-modal-id rewards] _]
    (some #(when (= (:_id %) (str select-tradee-modal-id)) %) rewards)))

(re-frame/reg-sub
  ::name
  :<- [::users]
  :<- [::current-user-id]
  (fn [[users id] _]
    (util/get-current-user-name id users)))

(re-frame/reg-sub
  ::bucks
  :<- [::users]
  :<- [::current-user-id]
  (fn [[users id] _]
    (util/get-current-user-bucks id users)))

(re-frame/reg-sub
 ::admin-rewards
 :<- [::all-rewards]
 (fn [all-rewards]
   (remove (some-fn #(= :redeemed (:reward-state %)) #(= :pending-trade (:reward-state %)) #(= :outgoing-trade (:reward-state %))) all-rewards)))

(re-frame/reg-sub
 ::unrequested-rewards
 :<- [::admin-rewards]
 (fn [admin-rewards]
   (remove #(= "pending" (:reward-state %)) admin-rewards)))

(re-frame/reg-sub
 ::redeemed-rewards
 :<- [::all-rewards]
 (fn [all-rewards]
   (filter #(= "redeemed" (:reward-state %)) all-rewards)))

(re-frame/reg-sub
 ::pending-rewards
 :<- [::all-rewards]
 (fn [all-rewards]
   (filter #(= "pending" (:reward-state %)) all-rewards)))

(re-frame/reg-sub
 ::unredeemed-rewards
 :<- [::all-rewards]
 (fn [all-rewards]
   (filter #(= "unredeemed" (:reward-state %)) all-rewards)))

(re-frame/reg-sub
 ::redeemed-rewards-count
 :<- [::redeemed-rewards]

 (fn [redeemed-rewards]
   (count redeemed-rewards)))

(re-frame/reg-sub
 ::pending-trades
 :<- [::all-rewards]
 (fn [all-rewards]
   (filter #(= "pending-trade" (:reward-state %)) all-rewards)))

(re-frame/reg-sub
 ::outgoing-trades
 :<- [::users]
 :<- [::current-user-id]
 (fn [[users current-user-id] _]
   (->> (:trades (util/get-current-user current-user-id users))
        (filter #(not= nil (:trade-reward %)))
        (map #(-> {}
                 (assoc :tradee (:name (:trade-target %)))
                 (assoc :price (:price (:trade-reward %)))
                 (assoc :reward-name (:reward-name (:trade-reward %)))
                 (assoc :_id (:_id (:trade-reward %))))))))

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
   (some #(when (= (second (second %)) (util/get-tier-end-value bucks tiers)) %) tiers)))

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
   (:reward-name (util/get-reward grant-request-modal-id all-rewards))))

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
   (:requesters (some #(when (= (str grant-request-modal-id) (:_id %)) %) all-rewards))))

(re-frame/reg-sub
 ::is-registry-alert-open
 :<- [::registry-alert-text]
 (fn [registry-alert-text]
   (not (= ""

           registry-alert-text))))



