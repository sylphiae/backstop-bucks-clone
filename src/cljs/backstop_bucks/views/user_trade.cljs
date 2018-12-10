(ns backstop-bucks.views.user-trade
      (:require [backstop-bucks.subs :as subs]
                [re-frame.core :as re-frame]
                [backstop-bucks.components.bucks-input :refer [bucks-input]]
                [backstop-bucks.components.trade-target :refer [trade-target]]
                [backstop-bucks.components.action-button :refer [action-button]]
                [reagent.core :as reagent :refer [atom with-let]]))

(defn user-trade []
      (reagent/with-let [first-name (re-frame/subscribe [::subs/name])
                         selected-trade-target (re-frame/subscribe [::subs/selected-trade-target])
                         bucks-trade-request (re-frame/subscribe [::subs/bucks-trade-amount])]
      [:div
       [:h1 @first-name "'s Trade Page"]
       [:h2 "Trade Amount:"]
       [bucks-input]
       [trade-target]
       [action-button {:value [@bucks-trade-request @selected-trade-target] :event :trade-request-click} "Request Backstop Bucks trade"]
       ]))

(comment "Input the amount user would like to trade
Select trade target
Click on request trade
Accept or reject pending trades
Select rewards to trade")