(ns backstop-bucks.components.rewards-table
  (:require [reagent.core :as reagent :refer [atom with-let]]
            [baking-soda.core :as b]
            [re-frame.core :as re-frame]
            [backstop-bucks.util :as util]))

(defn- reward->table-row [actions index reward]
  [:tr
   [:td (inc index)]
   [:td (:reward-name reward)]
   [:td (:price reward)]
   [:td (map #(util/clone-component % {placeholder}) actions)]])

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
            (map-indexed (partial reward->table-row children) @rewards))]]))
