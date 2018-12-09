(ns backstop-bucks.views.user-trade
      (:require [backstop-bucks.subs :as subs]
                [re-frame.core :as re-frame]
                [backstop-bucks.components.bucks-input :refer [bucks-input]]
                [backstop-bucks.components.trade-target :refer [trade-target]]))

(defn user-trade []
      (let [first-name (re-frame/subscribe [::subs/name])]
      [:div
       [:h1 @first-name "'s Trade Page"]
       [:h2 "Trade Amount:"]
       [bucks-input]
       [trade-target]
       ]))

(comment "Input the amount user would like to trade
Select trade target
Click on request trade
Accept or reject pending trades
Select rewards to trade")