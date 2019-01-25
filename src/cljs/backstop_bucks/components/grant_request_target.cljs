(ns backstop-bucks.components.grant-request-target
  (:require [baking-soda.core :as b]
            [backstop-bucks.subs :as subs]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom with-let]]))

(defn grant-request-target [props]
  (reagent/with-let [grant-targets (re-frame/subscribe [::subs/grant-targets])
                     selected-grant-target (re-frame/subscribe [::subs/selected-grant-target])]
    [:div
     [b/Col
      (str "Who would you like to grant \"" (:grant-request-item props) "\" to?")]
     [b/Col {:sm "3"}
      [b/Input {:type "select"
                :value (:name @selected-grant-target)
                :on-change #(re-frame/dispatch [:select-grant-target (-> % .-target .-value)])}
      ;% is javascript event
       (map #(conj [:option] (:name %)) @grant-targets)]]]))
