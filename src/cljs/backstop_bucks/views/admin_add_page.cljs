(ns backstop-bucks.views.admin-add-page
  (:require [baking-soda.core :as b]
            [backstop-bucks.subs :as subs]
            [backstop-bucks.components.user-bucks-input :refer [user-bucks-input]]
            [backstop-bucks.components.select-new-user-target :refer [select-new-user-target]]
            [backstop-bucks.components.action-button :refer [action-button]]
            [backstop-bucks.components.grant-request-modal :refer [grant-request-modal]]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom with-let]]))

(defn admin-add-page []
  (reagent/with-let [selected-new-user (re-frame/subscribe [::subs/selected-new-user])
                     new-bucks (re-frame/subscribe [::subs/new-bucks])
                     is-alert-open (re-frame/subscribe [::subs/is-add-admin-alert-open])]
  [:div
   [b/Alert {:color "success" :isOpen @is-alert-open} "You have successfully input Backstop Bucks."]
   [b/Container
    [:h1 {:class "text-primary"} "Admin Add Page"]
    [b/Row
     [b/Col {:lg "12"}
      [:h2 "Input Backstop Bucks:"]
      [b/Col [user-bucks-input]]
      [select-new-user-target]
      [b/Col
       [action-button {
                       :value [@new-bucks @selected-new-user]
                       :event :set-bucks-click
                       :color "primary"} "Set Backstop Bucks"]]]]]]))

(comment "Click on add reward button
Input reward value
Input Backstop Bucks amount for user
Stretch: add picture for reward
Add alert for success for button")