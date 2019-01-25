(ns backstop-bucks.components.grant-request-modal
  (:require [baking-soda.core :as b]
            [backstop-bucks.components.grant-request-target :refer [grant-request-target]]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom with-let]]
            [backstop-bucks.subs :as subs]
            [backstop-bucks.components.action-button :refer [action-button]]))

(defn grant-request-modal [props]
  (reagent/with-let [grant-request-modal-id (re-frame/subscribe [::subs/grant-request-modal-id])
                     grant-request-item (re-frame/subscribe [::subs/grant-request-item])
                     selected-grant-target (re-frame/subscribe [::subs/selected-grant-target])]
    [:<>
     [b/Modal props
      [b/ModalHeader "Choose the recipient"]
      [b/ModalBody
       [grant-request-target {:grant-request-item @grant-request-item}]]
      [b/ModalFooter
       (print @selected-grant-target)
       [action-button {:value [@grant-request-modal-id @selected-grant-target]
                       :event :grant-request-modal-button-click
                       :color "primary"} "Grant Request"]
       [action-button {:event :grant-request-modal-cancel-button-click :color "secondary"} "Cancel"]]]]))