(ns backstop-bucks.components.select-tradee-modal
  (:require [baking-soda.core :as b]
            [backstop-bucks.components.trade-target :refer [trade-target]]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom with-let]]
            [backstop-bucks.subs :as subs]
            [backstop-bucks.components.action-button :refer [action-button]]))

(defn select-tradee-modal [props]
  (reagent/with-let [select-tradee-modal-index (re-frame/subscribe [::subs/select-tradee-modal-index])
                     outgoing-trades (re-frame/subscribe [::subs/outgoing-trades])
                     selected-trade-target (re-frame/subscribe [::subs/selected-trade-target])]
  [:<>
   [b/Modal props
    [b/ModalHeader "Choose your tradee"]
    [b/ModalBody
     [trade-target {:trade-category (:reward-name (nth @outgoing-trades @select-tradee-modal-index))}]
     ]
    [b/ModalFooter
     [action-button {:value [@select-tradee-modal-index @selected-trade-target] :event :modal-trade-button-click} "Request Trade"]
     [action-button {:event :cancel-button-click} "Cancel"]
     ]]]))