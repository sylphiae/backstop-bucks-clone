(ns backstop-bucks.components.trade-target
  (:require [baking-soda.core :as b]
            [backstop-bucks.subs :as subs]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom with-let]]))

(defn trade-target []
  (reagent/with-let [trade-targets (re-frame/subscribe [::subs/trade-targets])
                     user-name (re-frame/subscribe [::subs/name])]
                    (print @trade-targets)
    [:div
     "Who would you like to trade with?"
     [b/Input {:type "select"}
      (map #(conj [:option] (:name %)) (remove #(= {:name @user-name} %) @trade-targets))
      ]
     ]))
