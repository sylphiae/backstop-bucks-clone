(ns backstop-bucks.components.bucks-input
  (:require [backstop-bucks.subs :as subs]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom with-let]]))

(defn bucks-input []
        (reagent/with-let [bucks-to-trade (re-frame/subscribe [::subs/bucks-trade-amount])]
           [:div.name-input
            "How much would you like to trade? "
            [:input {:type "text"
                     :value @bucks-to-trade
                     :on-change #(re-frame/dispatch [:bucks-input-change (-> % .-target .-value)])}]
            ]
           ))

