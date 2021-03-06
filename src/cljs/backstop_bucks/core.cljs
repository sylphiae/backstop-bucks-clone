(ns backstop-bucks.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [backstop-bucks.events :as events]
   [backstop-bucks.views.view :as views]
   [backstop-bucks.config :as config]))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/view-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
