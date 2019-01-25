(ns backstop-bucks.components.select-tradee-modal
  (:require [baking-soda.core :as b]
            [backstop-bucks.components.trade-target :refer [trade-target]]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom with-let]]
            [backstop-bucks.subs :as subs]
            [backstop-bucks.components.action-button :refer [action-button]]
            [backstop-bucks.db :as db]))

(defn select-tradee-modal [props]
  (reagent/with-let [select-tradee-modal-id (re-frame/subscribe [::subs/select-tradee-modal-id])
                     selected-trade-target (re-frame/subscribe [::subs/selected-trade-target])]
    [:<>
     [b/Modal props
      [b/ModalHeader "Choose your tradee"]
      [b/ModalBody
       [trade-target {:trade-category (:reward-name (some #(when (= (:reward-id %) @select-tradee-modal-id) %) (:all-rewards db/default-db)))}]]
      [b/ModalFooter
       [action-button {:value [@select-tradee-modal-id @selected-trade-target]
                       :event :modal-trade-button-click
                       :color "primary"} "Request Trade"]
       [action-button {:event :select-tradee-modal-cancel-button-click :color "secondary"} "Cancel"]]]]))