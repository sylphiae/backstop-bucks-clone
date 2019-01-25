(ns backstop-bucks.components.select-new-user-target
  (:require [baking-soda.core :as b]
            [backstop-bucks.subs :as subs]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom with-let]]))

(defn select-new-user-target [props]
  (reagent/with-let [users (re-frame/subscribe [::subs/users])
                     selected-new-user (re-frame/subscribe [::subs/selected-new-user])]
    [:div
     [b/Col
      (str "Please pick a new user.")]
     [b/Col {:sm "3"}
      [b/Input {:type "select"
                :value (:name @selected-new-user)
                :on-change #(re-frame/dispatch [:selected-new-user (-> % .-target .-value)])}
      ;% is javascript event
       (map #(conj [:option] (:name %)) @users)]]]))
