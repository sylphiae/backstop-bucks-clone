(ns backstop-bucks.views.admin-home-page
  (:require [baking-soda.core :as b]
            [backstop-bucks.subs :as subs]
            [backstop-bucks.components.basic-table :refer [basic-table]]
            [backstop-bucks.components.action-button :refer [action-button]]
            [backstop-bucks.components.grant-request-modal :refer [grant-request-modal]]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom with-let]]))

(defn admin-home-page []
  (reagent/with-let [is-modal-open (re-frame/subscribe [::subs/is-grant-request-modal-open])]
    [:div
     [b/Container
      [:h1 {:class "text-primary"} "Admin Center"]
      [:h4 "Requested Rewards:"]
      [basic-table {:subscription-details [::subs/pending-rewards]
                    :column-names ["#" "Reward Name" "Reward Value"]
                    :column-keys [:reward-name :price]}
       [action-button {:event :remove-button-click :color "danger"} "Remove"]
       [action-button {:event :grant-button-click :color "primary"} "Grant request"]]
      [grant-request-modal {:is-open @is-modal-open}]
      [:h4 "Unrequested Rewards:"]
      [basic-table {:subscription-details [::subs/unrequested-rewards]
                    :column-names ["#" "Reward Name" "Reward Value"]
                    :column-keys [:reward-name :price]}
       [action-button {:event :remove-button-click :color "danger"} "Remove"]]
      [:h4 "Previously Confirmed Rewards:"]
      [basic-table {:subscription-details [::subs/redeemed-rewards]
                    :column-names ["#" "Reward Name" "Reward Value"]
                    :column-keys [:reward-name :price]}]]]))

(comment "Click on remove reward button
Confirm user reward request")