(ns backstop-bucks.components.rewards-table
  (:require [reagent.core :as reagent :refer [atom with-let]]
            [baking-soda.core :as b]
            [re-frame.core :as re-frame]))

(defn- reward->table-row [index reward]
  [:tr
   [:td (inc index)]
   [:td (:reward-name reward)]
   [:td (:price reward)]
   [:td]])

(defn rewards-table [props & children]
  (reagent/with-let [rewards (re-frame/subscribe (:rewards-subscription props))]
    [:div
     [b/Table {:hover true}
      [:thead
       [:tr
        [:th "#"]
        [:th "Reward Name"]
        [:th "Reward Value"]
        (when (< 0 (count children)) [:th "Actions"])]]
      (into [:tbody]
            (map-indexed reward->table-row @rewards))]]))
