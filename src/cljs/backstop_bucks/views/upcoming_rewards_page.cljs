(ns backstop-bucks.views.upcoming-rewards-page
  (:require
   [baking-soda.core :as b]
   [re-frame.core :as re-frame]
   [backstop-bucks.subs :as subs]
   [backstop-bucks.util :as util]
   [backstop-bucks.db :as db]
   [backstop-bucks.components.basic-table :refer [basic-table]]))

(defn upcoming-rewards-page []
  (let [tier (re-frame/subscribe [::subs/tier])
        tiers (re-frame/subscribe [::subs/tiers])
        next-tier (re-frame/subscribe [::subs/next-tier])
        upcoming-rewards-count (re-frame/subscribe [::subs/upcoming-rewards-count])]
    [:div
     [b/Container
      [:h1 {:class "text-primary"} "Your Upcoming Rewards Page"]
      [b/Row [:h3 "Your upcoming tier is: " (util/display-upcoming-tier @next-tier @tier)]]
      [:h4 "Upcoming Rewards: " @upcoming-rewards-count]
      [basic-table {:subscription-details [::subs/upcoming-rewards]
                    :column-names ["#" "Reward Name" "Reward Value"]
                    :column-keys [:reward-name :price]}]]]))
