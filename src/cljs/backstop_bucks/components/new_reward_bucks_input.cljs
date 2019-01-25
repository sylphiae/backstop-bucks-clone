(ns backstop-bucks.components.new-reward-bucks-input
  (:require [backstop-bucks.subs :as subs]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom with-let]]))

;This component is for admins to input the value of a new reward
(defn new-reward-bucks-input []
  (reagent/with-let [new-reward-bucks (re-frame/subscribe [::subs/new-reward-bucks])]
    [:div.name-input
     "How much is this reward worth in Backstop Bucks? "
     [:input {:type "text"
              :value @new-reward-bucks
              :on-change #(re-frame/dispatch [:new-reward-bucks-input-change (-> % .-target .-value)])}]]))

