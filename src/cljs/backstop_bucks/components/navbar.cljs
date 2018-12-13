(ns backstop-bucks.components.navbar
  (:require [reagent.core :as reagent :refer [atom with-let]]
            [baking-soda.core :as b]
            [re-frame.core :as re-frame]))

(defn navbar [props]
  [:<>
   [b/Navbar props
    [b/NavbarBrand {:href "#" :on-click #(re-frame/dispatch [:home-nav-click])} "Backstop Bucks"]
    [b/NavItem
     [b/NavLink {:href "#" :on-click #(re-frame/dispatch [:trade-nav-click])} "Trade Page"]
     ]
    ]
   ]
  )
