(ns backstop-bucks.db)

(def default-db
  {:name "Jenny"
   :bucks 100
   :redeemed-rewards [{:reward-name "Coupon" :price 10} {:reward-name "Candy" :price 2}]
   :unredeemed-rewards [{:reward-name "Phone charger" :price 15}
                        {:reward-name "Beer" :price 8}
                        {:reward-name "Gift Card" :price 20}]})
