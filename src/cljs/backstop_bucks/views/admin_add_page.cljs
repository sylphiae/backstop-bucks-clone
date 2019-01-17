(ns backstop-bucks.views.admin-add-page
  (:require [baking-soda.core :as b]
            [backstop-bucks.subs :as subs]
            [backstop-bucks.components.basic-table :refer [basic-table]]
            [backstop-bucks.components.action-button :refer [action-button]]
            [backstop-bucks.components.grant-request-modal :refer [grant-request-modal]]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom with-let]]))

(defn admin-add-page []
  (reagent/with-let [is-modal-open (re-frame/subscribe [::subs/is-grant-request-modal-open])]
  [:div
   [b/Container
    [:h1 {:class "text-primary"} "Admin Add Page"]

    ]]))

(comment "Click on add reward button
Input reward value
Input Backstop Bucks amount for user
Stretch: add picture for reward")