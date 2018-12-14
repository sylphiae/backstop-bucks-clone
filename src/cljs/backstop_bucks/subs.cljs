(ns backstop-bucks.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::bucks
 (fn [db]
   (:bucks db)))

(re-frame/reg-sub
  ::bucks-trade-amount
  (fn [db]
      (:bucks-trade-amount db)))

(re-frame/reg-sub
 ::redeemed-rewards
 (fn [db]
   (:redeemed-rewards db)))

(re-frame/reg-sub
 ::unredeemed-rewards
 (fn [db]
   (:unredeemed-rewards db)))

(re-frame/reg-sub
  ::trade-targets
  (fn [db]
    (:users db)))

(re-frame/reg-sub
  ::trade-requests
  (fn [db]
    (:trade-requests db)))

(re-frame/reg-sub
  ::pending-trades
  (fn [db]
    (:pending-trades db)))

(re-frame/reg-sub
  ::outgoing-trades
  (fn [db]
    (:outgoing-trades db)))

(re-frame/reg-sub
  ::page-view
  (fn [db]
      (:page db)))

(re-frame/reg-sub
  ::selected-trade-target-primary
  (fn [db]
    (:selected-trade-target db)))

(re-frame/reg-sub
  ::is-select-tradee-modal-open
  (fn [db]
    (:is-select-tradee-modal-open db)))

(re-frame/reg-sub
  ::is-bucks-alert-open
  (fn [db]
    (:is-bucks-alert-open db)))

(re-frame/reg-sub
  ::select-tradee-modal-index
  (fn [db]
    (:select-tradee-modal-index db)))
;; --------------------------------------------------------

(re-frame/reg-sub
 ::unredeemed-rewards-count
 :<- [::unredeemed-rewards]

 (fn [unredeemed-rewards]
   (count unredeemed-rewards)))

(re-frame/reg-sub
 ::redeemed-rewards-count
 :<- [::redeemed-rewards]

 (fn [redeemed-rewards]
   (count redeemed-rewards)))

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
  ::selected-trade-target
  :<- [::trade-targets]
  :<- [::name]
  :<- [::selected-trade-target-primary]
    (fn [[trade-targets user-name selected-trade-target-primary] _]
      (if (nil? selected-trade-target-primary)
        (first (remove #(= user-name (:name %)) trade-targets))
        selected-trade-target-primary)))



