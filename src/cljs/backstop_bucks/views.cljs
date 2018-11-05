(ns backstop-bucks.views
  (:require
   [re-frame.core :as re-frame]
   [reagent.core :as reagent]
   [backstop-bucks.subs :as subs]
   [baking-soda.core :as b]
   [backstop_bucks.components.redeem_button :refer [redeem-button]]
   ))

;(defn color-input []
;  (let [color (re-frame/subscribe [::subs/color])]
;  [:div.color-input
;   "The color is: "
;   [:input {:type "text"
;            :value @color
;            :on-change #(re-frame/dispatch [:name-color-change (-> % .-target .-value)])}]
;   ]))

(defn redeemed-rewards []
  (reagent/with-let [redeemed-rewards (re-frame/subscribe [::subs/redeemed-rewards])
                    lis (mapv #(conj [:li] %) @redeemed-rewards)]
    [:div (into [:ol] lis)])
  )

(defn unredeemed-rewards []
  (reagent/with-let [unredeemed-rewards (re-frame/subscribe [::subs/unredeemed-rewards])
                     unredeemed-lis (mapv #(conj [:li] %) @unredeemed-rewards)]
    [:div (into [:ol] unredeemed-lis)])
  )


; flexbox or grid?
; react router - secretary
; baking soda, material-ui

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
     [redeemed-rewards]
     ])
  )