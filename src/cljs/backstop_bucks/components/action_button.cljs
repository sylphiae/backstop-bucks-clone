(ns backstop-bucks.components.action-button
  (:require [baking-soda.core :as b]
            [re-frame.core :as re-frame]))

(defn action-button [props & children]
      (let [new-props (merge props {:on-click #(re-frame/dispatch [(:event props)(:index props)])})])
  [:<>
   [b/Button new-props children]])

;need event and index