(ns backstop-bucks.views.user-home-page
  (:require
    [re-frame.core :as re-frame]
    [reagent.core :as reagent]
    [backstop-bucks.subs :as subs]
    [baking-soda.core :as b]
    [backstop-bucks.components.action-button :refer [action-button]]
    [backstop-bucks.components.basic-table :refer [basic-table]]
   ))

; react router - secretary

(defn user-home-page []
  (let [first-name (re-frame/subscribe [::subs/name])
        bucks (re-frame/subscribe [::subs/bucks])
        redeemed-rewards-count (re-frame/subscribe [::subs/redeemed-rewards-count])
        unredeemed-rewards-count (re-frame/subscribe [::subs/unredeemed-rewards-count])]
    [:div
     [:h1
      @first-name "'s Backstop Bucks Home Page"]
     [:h3
      "Backstop Bucks: $" @bucks]
     [:h4 "Unredeemed Rewards: " @unredeemed-rewards-count]
     [basic-table {:subscription-details [::subs/unredeemed-rewards]
                   :column-names ["#" "Reward Name" "Reward Value"]
                   :column-keys [:reward-name :price]}
      [action-button {:event :redeem-button-click :color "primary"} "Redeem"]
      [action-button {:value [:unredeemed-rewards] :event :reject-button-click :color "danger"} "Reject"]
      [action-button {:event :trade-button-click :color "primary"} "Trade"]]
     [:h4 "Redeemed Rewards: " @redeemed-rewards-count]
     [basic-table {:subscription-details [::subs/redeemed-rewards]
                   :column-names ["#" "Reward Name" "Reward Value"]
                   :column-keys [:reward-name :price]}]]))

(comment "Actions user can take while on this page:
Click on Redeem rewards
Click on Reject rewards
Click on trade rewards button to go to the trade page where you can click on trade Backstop Bucks button
" )