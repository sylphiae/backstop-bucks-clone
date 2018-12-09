(ns backstop-bucks.components.trade-target
  (:require [baking-soda.core :as b]
            [backstop-bucks.subs :as subs]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom with-let]]))

(defn trade-target []
  (reagent/with-let [trade-targets (re-frame/subscribe [::subs/trade-targets])]
                    (print @trade-targets)
    [:div
     [b/Input {:type "select"}
      (map #(conj [:option] (:name %)) @trade-targets)
      ]
     ]))
