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
 ::redeemed-rewards
 (fn [db]
   (:redeemed-rewards db)))

(re-frame/reg-sub
 ::unredeemed-rewards
 (fn [db]
   (:unredeemed-rewards db)))

(re-frame/reg-sub
  ::page-view
  (fn [db]
      (:page db)))
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
   (count



    redeemed-rewards)))


