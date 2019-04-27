;(ns backstop-bucks.endpoints.user-endpoint-test
;  (:require [clojure.test :refer :all]
;            [bond.james :as bond]
;            [clojure.test.check.properties :as prop]
;            [clojure.spec.gen.alpha :as gen]
;            [clojure.test.check.clojure-test :refer :all]
;            [backstop-bucks.endpoints.user-endpoint :refer get-user]
;            [clj-spec.core :refer :all]))
;
;(defspec get-user-test
;         10
;         (prop/for-all [v (gen/vector (gen/choose 0 1))]
;                       (bond/with-stub [get-user]
;                                       (let [result (even-or-odd v)
;                                             zeroes-and-ones (group-by zero? v)
;                                             zeroes (get zeroes-and-ones true)
;                                             ones (get zeroes-and-ones false)
;                                             evens-and-odds (group-by #(= :even %) result)
;                                             evens (get zeroes-and-ones true)
;                                             odds (get zeroes-and-ones false)]
;                                         (and (= (count zeroes) (count evens))
;                                              (= (count ones) (count odds))
;                                              (= (count zeroes) (-> save bond/calls count)))))))
