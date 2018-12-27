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

(defn get-tier-end-value [bucks coll]
  (some #(when (> % bucks) %) (map second (vals coll))))

(defn display-upcoming-tier [tier coll tiers]
  (let [next-tier (inc (js/parseInt (name tier)))
        next-tier-val (get tiers (keyword (str next-tier)))]
    (if (= 6 (int tier))
      (str 6 ": $" (first (second coll)) "-" (second (second coll)))
      (str next-tier", for which the range is $" (first next-tier-val) "-" (second next-tier-val)))))


