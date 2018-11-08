(ns backstop-bucks.components.redeem-button
  (:require [reagent.core :as reagent :refer [atom with-let]]
            [baking-soda.core :as b]))

(defn redeem-button []
  [:div
   [b/Button {:style {:color "primary"}} "Redeem"]])
