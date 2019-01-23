(ns backstop-bucks.components.user-bucks-input
  (:require [backstop-bucks.subs :as subs]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom with-let]]))

;This component is for admins to input backstop bucks to users
(defn user-bucks-input []
        (reagent/with-let [new-bucks (re-frame/subscribe [::subs/new-bucks])
                           ]
           [:div.name-input
            "How many Backstop Bucks would you like this user to start with? "
            [:input {:type "text"
                     :value @new-bucks
                     :on-change #(re-frame/dispatch [:user-bucks-input-change (-> % .-target .-value)])}]
            ]
           ))

