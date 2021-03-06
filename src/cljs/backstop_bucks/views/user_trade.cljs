(ns backstop-bucks.views.user-trade
  (:require [backstop-bucks.subs :as subs]
            [re-frame.core :as re-frame]
            [backstop-bucks.components.bucks-input :refer [bucks-input]]
            [backstop-bucks.components.trade-target :refer [trade-target]]
            [backstop-bucks.components.action-button :refer [action-button]]
            [backstop-bucks.components.select-tradee-modal :refer [select-tradee-modal]]
            [reagent.core :as reagent :refer [atom with-let]]
            [backstop-bucks.components.basic-table :refer [basic-table]]
            [baking-soda.core :as b]))

(defn user-trade []
  (reagent/with-let [first-name (re-frame/subscribe [::subs/name])
                     selected-trade-target (re-frame/subscribe [::subs/selected-trade-target])
                     bucks-trade-request (re-frame/subscribe [::subs/bucks-trade-amount])
                     pending-trades-count (re-frame/subscribe [::subs/pending-trades-count])
                     outgoing-trades-count (re-frame/subscribe [::subs/outgoing-trades-count])
                     is-modal-open (re-frame/subscribe [::subs/is-select-tradee-modal-open])
                     is-alert-open (re-frame/subscribe [::subs/is-bucks-alert-open])]
    [:div
     [b/Alert {:color "danger" :isOpen @is-alert-open} "You do not have enough Backstop Bucks for that trade."]
     [b/Container
      [:h1 {:class "text-primary"} @first-name "'s Trade Page"]
      [b/Row
       [b/Col {:lg "12"}
        [:h2 "Trade Amount:"]
        [b/Col [bucks-input]]
        [trade-target {:trade-category "Backstop Bucks"}]
        [b/Col
         [action-button {:value [@bucks-trade-request @selected-trade-target]
                         :event :trade-request-click
                         :color "primary"} "Request Backstop Bucks trade"]]]]
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
      [select-tradee-modal {:is-open @is-modal-open :trade-event-handler :modal-trade-button-click :trade-event-cancel :select-tradee-modal-cancel-button-click}]]]))

(comment "Input the amount user would like to trade
Select trade target
Click on request trade
Accept or reject pending trades
Select who to trade with for outgoing trades")