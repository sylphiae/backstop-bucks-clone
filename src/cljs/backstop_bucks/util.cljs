(ns backstop-bucks.util)

(defn clone-component [coll props]
      (if (map? (second coll))
        (update-in coll [1] #(merge % props))
        ; case button with children and no props
        (if (> (count coll) 1)
          [(first coll) props (rest coll)]
          ; case button with no children and no props
          [(first coll) props])))

(defn remove-index [coll pos]
      (vec (concat (subvec coll 0 pos) (subvec coll (inc pos)))))
;dissoc-in for vectors


