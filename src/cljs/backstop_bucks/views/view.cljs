(ns backstop-bucks.views.view
  (:require [backstop-bucks.subs :as subs]
            [re-frame.core :as re-frame]))

;header

;subscription to the current page
(defn view-panel []
      (let [page-view (re-frame/subscribe [::subs/page-view])]
           [:div (@page-view)]))
