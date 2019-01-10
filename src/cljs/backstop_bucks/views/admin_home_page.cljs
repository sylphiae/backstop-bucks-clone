(ns backstop-bucks.views.admin-home-page
  (:require [baking-soda.core :as b]
            [backstop-bucks.subs :as subs]
            [backstop-bucks.components.basic-table :refer [basic-table]]
            [backstop-bucks.components.action-button :refer [action-button]]
            [re-frame.core :as re-frame]))

(defn admin-home-page []
  (let [pending-rewards (re-frame/subscribe [::subs/pending-rewards])]
  [:div
   [b/Container
    [:h1 {:class "text-primary"} "Admin Page"]
    [basic-table {:subscription-details [::subs/all-rewards]
                  :column-names ["#" "Reward Name" "Reward Value"]
                  :column-keys [:reward-name :price]}
     [action-button {:event :remove-button-click :color "danger"} "Remove"]
     [action-button {:value [:all-rewards] :event :confirm-button-click :color "primary"} "Confirm"]]]]))

(comment "Click on add reward button
Click on remove reward button
Input reward value
Confirm user reward request
Input Backstop Bucks amount for user
Stretch: add picture for reward")