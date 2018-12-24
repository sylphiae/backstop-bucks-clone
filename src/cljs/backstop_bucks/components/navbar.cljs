(ns backstop-bucks.components.navbar
  (:require [reagent.core :as reagent :refer [atom with-let]]
            [baking-soda.core :as b]
            [re-frame.core :as re-frame]
            [backstop-bucks.components.action-button :refer [action-button]]))

(defn navbar [props]
  [:<>
   [b/Navbar props
    [b/NavbarBrand {:href "#" :on-click #(re-frame/dispatch [:home-nav-click])} "Backstop Bucks"]
     [action-button {:event :trade-nav-click :color "primary"} "Trade Page"]
     ;[b/NavLink {:href "#" :on-click #(re-frame/dispatch [:trade-nav-click])} "Trade Page"]
    ]
   ]
  )
