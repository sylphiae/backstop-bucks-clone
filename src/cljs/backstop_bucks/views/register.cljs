(ns backstop-bucks.views.register
  (:require [baking-soda.core :as b]
            [re-frame.core :as re-frame]
            [backstop-bucks.subs :as subs]
            [reagent.core :as reagent :refer [atom with-let]]
            [backstop-bucks.components.action-button :refer [action-button]]))

(defn register []
  (reagent/with-let [new-username (re-frame/subscribe [::subs/new-username])
                     new-display-name (re-frame/subscribe [::subs/new-display-name])
                     new-password (re-frame/subscribe [::subs/new-password])
                     new-password-confirm (re-frame/subscribe [::subs/new-password-confirm])
                     is-registry-alert-open (re-frame/subscribe [::subs/is-registry-alert-open])
                     registry-alert-text (re-frame/subscribe [::subs/registry-alert-text])]
    [:div
     [b/Alert {:color "danger" :isOpen @is-registry-alert-open} @registry-alert-text]
     ;[b/Alert {:color "danger" :isOpen @is-password-mismatch-alert-open} "Your passwords do not match!"]
     ;[b/Alert {:color "danger" :isOpen @is-no-username-alert-open} "You need to input a username."]
     [b/Container
      [:h2 "Register Your Account:"]
      [b/Form
       [b/FormGroup
        [b/Label "Username"]
        [b/Input {:type      "text"
                  :placeholder "Enter a username."
                  :value     @new-username
                  :on-change #(re-frame/dispatch [:new-username-input-change (-> % .-target .-value)])}]]
       [b/FormGroup
        [b/Label "Display name"]
        [b/Input {:type      "text"
                  :placeholder "Enter a publicly displayed name."
                  :value     @new-display-name
                  :on-change #(re-frame/dispatch [:new-display-name-input-change (-> % .-target .-value)])}]]
       [b/FormGroup
        [b/Label "Password"]
        [b/Input {:type      "password"
                  :placeholder "Enter your password."
                  :value     @new-password
                  :on-change #(re-frame/dispatch [:new-password-input-change (-> % .-target .-value)])}]]
       [b/FormGroup
        [b/Label "Confirm Password"]
        [b/Input {:type      "password"
                  :placeholder "Enter your password again."
                  :value     @new-password-confirm
                  :on-change #(re-frame/dispatch [:new-password-confirm-input-change (-> % .-target .-value)])}]]
       [action-button {:value [@new-password @new-password-confirm @new-display-name @new-username]
                       :event :register-click
                       :color "primary"} "Create account"]]]]))

(comment "Enter username
Enter display name
Enter password twice
Click create account")