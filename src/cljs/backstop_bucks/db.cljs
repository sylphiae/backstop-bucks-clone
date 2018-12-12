(ns backstop-bucks.db
  (:require [backstop-bucks.views.user-home-page :as home-page]))

(def default-db
  {:page #'home-page/main-panel
   :name "Jenny"
   :bucks 100
   :bucks-trade-amount 0
   :redeemed-rewards [{:reward-name "Coupon" :price 10} {:reward-name "Candy" :price 2}]
   :unredeemed-rewards [{:reward-name "Phone charger" :price 15}
                        {:reward-name "Beer" :price 8}
                        {:reward-name "Gift Card" :price 20}]
   :users [{:name "Jenny"} {:name "Wii Fit Trainer"} {:name "Link"} {:name "Kirby"} {:name "Zelda"}]
   :trade-requests []
   :selected-trade-target nil
   :pending-trades [{:reward-name "Pen" :price 40 :trader "Link"}]
   })