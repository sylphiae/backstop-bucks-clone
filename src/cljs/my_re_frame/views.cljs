(ns my-re-frame.views
  (:require
   [re-frame.core :as re-frame]
   [my-re-frame.subs :as subs]
   ))

(defn color-input []
  (let [color (re-frame/subscribe [::subs/color])]
  [:div.color-input
   "The color is: "
   [:input {:type "text"
            :value @color
            :on-change #(re-frame/dispatch [:name-color-change (-> % .-target .-value)])}]
   ]))

(defn name-input []
  (let [first-name (re-frame/subscribe [::subs/name])]
        [:div.name-input
         "Your desired name: "
         [:input {:type "text"
                  :value @first-name
                  :on-change #(re-frame/dispatch [:name-change (-> % .-target .-value)])}]
         ]
    ))

(defn main-panel []
  (let [first-name (re-frame/subscribe [::subs/name])
        color (re-frame/subscribe [::subs/color])]
    [:div
     [name-input]
     [:h1 {:style {:color @color}}
      "Hello from " @first-name]
     [color-input]
     ])
  )