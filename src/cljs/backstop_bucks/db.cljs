(ns backstop-bucks.db)

(def default-db
  {:name "Jenny"
   :color "#00f"
   :bucks 100
   :redeemed-rewards [{:reward-name "Coupon"} {:reward-name "Candy"}]
   :unredeemed-rewards '("Phone charger" "Beer" "Gift Card")})
