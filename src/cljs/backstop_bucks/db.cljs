(ns backstop-bucks.db
  (:require [backstop-bucks.views.user-home-page :refer [user-home-page]]
            [backstop-bucks.views.admin-home-page :refer [admin-home-page]]))

(def default-db
  {:page                       admin-home-page
   :name                        "Jenny"
   :bucks                       100
   :bucks-trade-amount          0
   :all-rewards                 [{:reward-name "Phone charger" :price 15 :reward-id 0 :reward-state :unredeemed}
                                 {:reward-name "Beer" :price 8 :reward-id 1 :reward-state :unredeemed}
                                 {:reward-name "Gift Card" :price 20 :reward-id 2 :reward-state :unredeemed}
                                 {:reward-name "Switch" :price 300 :reward-id 3 :reward-state :unredeemed}
                                 {:reward-name "Coupon" :price 10 :reward-id 4 :reward-state :redeemed}
                                 {:reward-name "Candy" :price 2 :reward-id 5 :reward-state :redeemed}]
   :users                       [{:name "Jenny"} {:name "Wii Fit Trainer"} {:name "Link"} {:name "Kirby"} {:name "Zelda"}]
   :tiers                       {:1 [0 101] :2 [101 501] :3 [501 1001] :4 [1001 5001] :5 [5001 10001] :6 [10001 100001]}
   ;bucks
   :trade-requests              []
   :selected-trade-target       nil
   ;non-bucks
   :pending-trades              [{:reward-name "Pen" :price 40 :trader "Link" :reward-id 6}]
   :outgoing-trades             [{:reward-name "Book" :price 28 :reward-id 7}]
   :is-select-tradee-modal-open false
   :select-tradee-modal-index   0
   :is-bucks-alert-open         false
   })