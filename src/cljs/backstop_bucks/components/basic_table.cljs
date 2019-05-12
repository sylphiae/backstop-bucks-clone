(ns backstop-bucks.components.basic-table
  (:require [reagent.core :as reagent :refer [atom with-let]]
            [baking-soda.core :as b]
            [re-frame.core :as re-frame]
            [backstop-bucks.util :as util]))

(defn- item->table-row [actions item-keys index item]
  [:tr
   [:td (inc index)]
   (map #(conj [:td] (% item)) item-keys)
   [:td (map #(util/clone-component % {:value (conj [(:_id item)] (:value (second %)))}) actions)]])

(defn basic-table [props & children]
  (reagent/with-let [table-items (re-frame/subscribe (:subscription-details props))]
    [:div
     [b/Table {:hover true}
      [:thead
       [:tr
        (map #(conj [:th] %) (:column-names props))
        (when (< 0 (count children)) [:th "Actions"])]]
      (into [:tbody]
            (map-indexed (partial item->table-row children (:column-keys props)) @table-items))]]))
