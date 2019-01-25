(ns backstop-bucks.views.view
  (:require [backstop-bucks.subs :as subs]
            [re-frame.core :as re-frame]
            [backstop-bucks.components.navbar :refer [navbar]]))

;header

;subscription to the current page
(defn view-panel []
  (let [page-view (re-frame/subscribe [::subs/page-view])]
    [:div
     [navbar {:color "primary" :dark true}]
     (@page-view)]))

(comment "Add nav bar
Add page for viewing historical trades?")

;container for react components to add margin buffers?
