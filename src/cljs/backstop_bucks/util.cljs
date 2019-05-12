(ns backstop-bucks.util)

(defn clone-component [coll props]
  (if (map? (second coll))
    (update-in coll [1] #(merge % props))
    ; case button with children and no props
    (if (> (count coll) 1)
      [(first coll) props (rest coll)]
      ; case button with no children and no props
      [(first coll) props])))

(defn remove-item [coll id]
  (remove #(= id (:_id %)) coll))
;dissoc-in for maps

(defn get-tier-end-value [bucks coll]
  (some #(when (> % bucks) %) (map second (vals coll))))

(defn display-upcoming-tier [next-tier coll]
  (if (nil? next-tier)
    (str 6 ": $" (first (second coll)) "-" (second (second coll)))
    (str (first next-tier) ", for which the range is $" (first (second next-tier)) "-" (second (second next-tier)))))

(defn positions
  [pred coll]
  (keep-indexed (fn [idx x]
                  (when (pred x)
                    idx))


                coll))


