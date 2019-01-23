(ns backstop-bucks.components.new-reward-name-input
  (:require [backstop-bucks.subs :as subs]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom with-let]]))

;This component is for admins to input the value of a new reward
(defn new-reward-name-input []
        (reagent/with-let [new-reward-name (re-frame/subscribe [::subs/new-reward-name])
                           ]
           [:div.name-input
            "What is the name of the new reward? "
            [:input {:type "text"
                     :value @new-reward-name
                     :on-change #(re-frame/dispatch [:new-reward-name-input-change (-> % .-target .-value)])}]]))

