(ns backstop-bucks.util)

(defn clone-component [coll props]
      (if (map? (second coll))
        (update-in [1] #(merge % props))
        ; case button with children and no props
        (if (> (count coll) 1)
          [(first coll) props (rest coll)]
          ; case button with no children and no props
          [(first coll) props])))
