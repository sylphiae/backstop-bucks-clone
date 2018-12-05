(ns backstop-bucks.views.user-home-page
  (:require
   [re-frame.core :as re-frame]
   [reagent.core :as reagent]
   [backstop-bucks.subs :as subs]
   [baking-soda.core :as b]
   [backstop-bucks.components.action-button :refer [action-button]]
   [backstop-bucks.components.rewards-table :refer [rewards-table]]))

(defn unredeemed-rewards []
  (reagent/with-let [unredeemed-rewards (re-frame/subscribe [::subs/unredeemed-rewards])
                     unredeemed-lis (mapv #(conj [:li] %) @unredeemed-rewards)]
    [:div (into [:ol] unredeemed-lis)]))

; react router - secretary

(defn main-panel []
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
     [rewards-table {:rewards-subscription [::subs/unredeemed-rewards]}
      [action-button "Redeem"]
      [action-button "Reject"]
      [action-button "Trade"]]
     [:h4 "Redeemed Rewards: " @redeemed-rewards-count]
     [rewards-table {:rewards-subscription [::subs/redeemed-rewards]}]]))

(comment "Actions user can take while on this page:
Click on Redeem rewards
Click on Reject rewards
Click on trade rewards button to go to the trade page where you can click on trade Backstop Bucks button
" )