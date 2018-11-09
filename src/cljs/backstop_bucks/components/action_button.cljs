(ns backstop-bucks.components.action-button
  (:require [baking-soda.core :as b]))

(defn action-button [props & children]
  [:div
   [b/Button props ""]])
