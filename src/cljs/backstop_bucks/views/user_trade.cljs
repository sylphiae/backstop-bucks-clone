(ns backstop-bucks.views.user-trade
      (:require [backstop-bucks.subs :as subs]
                [re-frame.core :as re-frame]
                [backstop-bucks.components.bucks-input :refer [bucks-input]]
                [backstop-bucks.components.trade-target :refer [trade-target]]
                [backstop-bucks.components.action-button :refer [action-button]]
                [backstop-bucks.components.select-tradee-modal :refer [select-tradee-modal]]
                [reagent.core :as reagent :refer [atom with-let]]
                [backstop-bucks.components.basic-table :refer [basic-table]]))

(defn user-trade []
      (reagent/with-let [first-name (re-frame/subscribe [::subs/name])
                         selected-trade-target (re-frame/subscribe [::subs/selected-trade-target])
                         bucks-trade-request (re-frame/subscribe [::subs/bucks-trade-amount])
                         pending-trades-count (re-frame/subscribe [::subs/pending-trades-count])
                         outgoing-trades-count (re-frame/subscribe [::subs/outgoing-trades-count])
                         is-modal-open (re-frame/subscribe [::subs/is-select-tradee-modal-open])]
      [:div
       [:h1 @first-name "'s Trade Page"]
       [:h2 "Trade Amount:"]
       [bucks-input]
       [trade-target {:trade-category "Backstop Bucks"}]
       [action-button {:value [@bucks-trade-request @selected-trade-target]
                       :event :trade-request-click
                       :color "primary"} "Request Backstop Bucks trade"]
       [:h4 "Pending Trades Tables:"]
       [:h5 "Incoming Trades: " @pending-trades-count]
       [basic-table {:subscription-details [::subs/pending-trades]
                     :column-names ["#" "Reward Name" "Reward Value" "Trader"]
                     :column-keys [:reward-name :price :trader]}
        [action-button {:event :accept-button-click :color "primary"} "Accept"]
        [action-button {:value [:pending-trades] :event :reject-button-click :color "danger"} "Reject"]]
       [:h5 "Outgoing Trades: " @outgoing-trades-count]
       [basic-table {:subscription-details [::subs/outgoing-trades]
                     :column-names ["#" "Reward Name" "Reward Value" "Tradee"]
                     :column-keys [:reward-name :price :tradee]}
        [action-button {:event :select-tradee-click :color "primary"} "Select Tradee"]]
       [select-tradee-modal {:is-open @is-modal-open}]
       ]))

(comment "Input the amount user would like to trade
Select trade target
Click on request trade
Accept or reject pending trades
Select who to trade with for outgoing trades")