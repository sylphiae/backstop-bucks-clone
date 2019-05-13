(ns backstop-bucks.db
  (:require [backstop-bucks.views.user-home-page :refer [user-home-page]]
            [backstop-bucks.views.admin-home-page :refer [admin-home-page]]
            [backstop-bucks.views.register :refer [register]]))

(def default-db
  {:page                        admin-home-page
   :current-user-id             "0"
   :new-bucks                   0
   :new-reward-bucks            0
   :new-reward-name             ""
   :new-username                ""
   :new-display-name            ""
   :new-password                ""
   :new-password-confirm        ""
   :bucks-trade-amount          0
   :registry-alert-text         ""
   :all-rewards                 []
   ;:all-rewards                 [{:reward-name "Phone charger" :price 15 :_id 0 :reward-state :pending
   ;                               :requesters [{:name "Jenny"} {:name "Marth"} {:name "Ike"}]}
   ;                              {:reward-name "Beer" :price 8 :_id 1 :reward-state :unredeemed}
   ;                              {:reward-name "Gift Card" :price 20 :_id 2 :reward-state :unredeemed}
   ;                              {:reward-name "Switch" :price 300 :_id 3 :reward-state :unredeemed}
   ;                              {:reward-name "Coupon" :price 10 :_id 4 :reward-state :redeemed}
   ;                              {:reward-name "Candy" :price 2 :_id 5 :reward-state :redeemed}
   ;                              {:reward-name "Pen" :price 40 :trader "Link" :_id 6 :reward-state :pending-trade}
   ;                              {:reward-name "Book" :price 28 :_id 7 :reward-state :outgoing-trade}]

   :users                       [{:_id "0" :name "Jenny" :bucks 100} {:name "Wii Fit Trainer"} {:name "Link"} {:name "Kirby"} {:name "Zelda"}]
   :tiers                       {:1 [0 101] :2 [101 501] :3 [501 1001] :4 [1001 5001] :5 [5001 10001] :6 [10001 100001]}
   ;bucks
   :trade-requests              []
   :selected-trade-target       nil
   :selected-grant-target       nil
   :selected-new-user           {:name "None"}
   ;non-bucks
   :is-select-tradee-modal-open false
   :is-grant-request-modal-open false
   :select-tradee-modal-id      0
   :grant-request-modal-id      0
   :is-bucks-alert-open         false
   :is-add-admin-alert-open     false
   :is-add-new-reward-alert-open false})