(ns training-planner.core-test
  (:require [clojure.test :refer :all]
            [training-planner.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 0))))

(deftest get-workout-tss-test
  (let [workout (struct workout :RI 3)]
    (is (= (get-workout-tss workout) 7))))

;(deftest requires-too-much-tss-test
;  (let [workout (struct workout :RI 3)
;        allowed-tss 6]
;    (is (= (have-tss-for-workout? workout allowed-tss)) true)))

;(deftest have-tss-for-workout-test2
;  (let [workout (struct workout :RI 3)
;        allowed-tss 8]
;    (is (= (have-tss-for-workout? workout allowed-tss)) false)))

(deftest filter-by-tss-test
  (is (= (tss-more-than-pair? 10 (list "workout" 20))) false)
         )

(deftest session-pair-compare-test
  (is (=
     (session-pair-compare (list "workout" 10) (list "workout" 20)) false
       )))
