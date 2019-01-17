(ns backstop-bucks.components.trade-target
  (:require [baking-soda.core :as b]
            [backstop-bucks.subs :as subs]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom with-let]]))

(defn trade-target [props]
  (reagent/with-let [trade-targets (re-frame/subscribe [::subs/trade-targets])
                     user-name (re-frame/subscribe [::subs/name])
                     selected-trade-target (re-frame/subscribe [::subs/selected-trade-target])]
    [:div
     [b/Col
     (str "Who would you like to trade \"" (:trade-category props) "\" with?")]
     [b/Col {:sm "3"}
     [b/Input {:type "select"
               :value (:name @selected-trade-target)
               :on-change #(re-frame/dispatch [:select-trade-target (-> % .-target .-value)])}
      ;% is javascript event
      (map #(conj [:option] (:name %)) (remove #(= {:name @user-name} %) @trade-targets))]]]))
