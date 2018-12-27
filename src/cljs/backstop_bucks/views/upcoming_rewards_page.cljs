(ns backstop-bucks.views.upcoming-rewards-page
  (:require
    [baking-soda.core :as b]
    [re-frame.core :as re-frame]
    [backstop-bucks.subs :as subs]
    [backstop-bucks.util :as util]
    [backstop-bucks.db :as db]))

(defn upcoming-rewards-page []
  (let [tier (re-frame/subscribe [::subs/tier])
        tiers (re-frame/subscribe [::subs/tiers])]
  [:div
   [b/Container
    [:h1 {:class "text-primary"} "Your Upcoming Rewards Page"]
    [:h3 "Your upcoming tier is: " (util/display-upcoming-tier (first @tier) @tier @tiers)]
    ]]))
