(ns backstop-bucks.views
  (:require
   [re-frame.core :as re-frame]
   [reagent.core :as reagent]
   [backstop-bucks.subs :as subs]
   [baking-soda.core :as b]
   [backstop-bucks.components.redeem-button :refer [redeem-button]]
   [backstop-bucks.components.rewards-table :refer [rewards-table]]))

(defn unredeemed-rewards []
  (reagent/with-let [unredeemed-rewards (re-frame/subscribe [::subs/unredeemed-rewards])
                     unredeemed-lis (mapv #(conj [:li] %) @unredeemed-rewards)]
    [:div (into [:ol] unredeemed-lis)]))

; react router - secretary

(defn main-panel []
  (let [first-name (re-frame/subscribe [::subs/name])
        color (re-frame/subscribe [::subs/color])
        bucks (re-frame/subscribe [::subs/bucks])
        redeemed-rewards-count (re-frame/subscribe [::subs/redeemed-rewards-count])
        unredeemed-rewards-count (re-frame/subscribe [::subs/unredeemed-rewards-count])]
    [:div
     [:h1 {:style {:color @color}}
      @first-name "'s Backstop Bucks Home Page"]
     [:h3 "Backstop Bucks: $" @bucks]
     [:h3 "Unredeemed Rewards: " @unredeemed-rewards-count]
     [redeem-button]
     [unredeemed-rewards]
     [:h3 "Redeemed Rewards: " @redeemed-rewards-count]
     [rewards-table {:rewards-subscription [::subs/redeemed-rewards]}]]))